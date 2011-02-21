package edu.metu.lngamesml.alt;

import edu.metu.lngamesml.agents.LearnerTypes;
import edu.metu.lngamesml.agents.WithDrawnAgent;
import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.core.InstancesList;
import edu.metu.lngamesml.core.IntegerVector;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.data.sample.WeightedSampling;
import edu.metu.lngamesml.eval.classification.BasicClassificationEvaluator;
import edu.metu.lngamesml.utils.log.Logging;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Oct 27, 2010
 * Time: 6:59:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class MajorityVoting {

    protected int NoOfAgents;
    protected int SamplingRatio;
    protected ArrayList<WithDrawnAgent> Agents = new ArrayList<WithDrawnAgent>();
    protected LearnerTypes LType;
    protected double Accuracy = 0.0;

    public MajorityVoting() {
        NoOfAgents = 10;
        SamplingRatio = 40;
    }

    public MajorityVoting(int noOfAgents, int samplingRatio) {
        NoOfAgents = noOfAgents;
        SamplingRatio = samplingRatio;
    }

    public void setSamplingRatio(int samplingRatio) {
        SamplingRatio = samplingRatio;
    }

    public void setNoOfAgents(int noOfAgents) {
        NoOfAgents = noOfAgents;
    }

    public void createAgentsWSampled(String trainingDataset) {
        WeightedSampling wSampling = new WeightedSampling(trainingDataset, NoOfAgents);
        try {
            wSampling.setSamplingRatio(SamplingRatio);
        } catch (Exception e) {
            Logging.log(Level.WARNING, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        InstancesList[] dataList = wSampling.partitionDataset();
        if (LType != null) {
            for (int i = 0; i < NoOfAgents; i++) {
                WithDrawnAgent agent = new WithDrawnAgent();
                agent.setLearningType(LType, null);
                Instances learningData = dataList[i].getInstances();
                agent.setAgentId(i);
                agent.learnFromData(learningData);
                Agents.add(i, agent);
            }
        } else {
            throw new NullPointerException("Learner Type can't be null!");
        }
    }

    public double getAccuracy() {
        return Accuracy;
    }

    public void setLearningType(LearnerTypes lType) {
        LType = lType;
    }

    public void startSimulation(String testDataset) {
        Instances testInstances = FileLoaderFactory.loadFile(testDataset);
        int noOfTestInstances = testInstances.numInstances();
        BasicClassificationEvaluator bcEval = new BasicClassificationEvaluator();
        Instance currentContext;
        int votes[];
        IntegerVector intVec;
        CategoricalComm electedClass = new CategoricalComm();
        for (int i = 0; i < noOfTestInstances; i++) {
            votes = new int[testInstances.numClasses()];
            currentContext = testInstances.get(i);
            for (int j = 0; j < NoOfAgents; j++) {
                try {
                    int vote = Agents.get(j).classifyInstance(currentContext).getStatementNo();
                    votes[vote]++;
                } catch (Exception e) {
                    Logging.warning(e.getMessage());
                    e.printStackTrace();
                }
            }
            intVec = new IntegerVector(votes);
            electedClass.setStatementNo(intVec.maxIndex());
            electedClass.setComStatement(currentContext.stringValue(currentContext.classIndex()));
            bcEval.addPerformanceObservation(electedClass, currentContext);
        }
        //System.out.println("Accuracy is " + bcEval.getAccuracyPercent());
        //System.out.println("No of Failed Observations " + bcEval.getNoOfFailedObservations());
        Accuracy = bcEval.getAccuracyPercent();
    }
}

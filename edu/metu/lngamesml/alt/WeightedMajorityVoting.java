package edu.metu.lngamesml.alt;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.WithDrawnAgent;
import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.core.DoubleVector;
import edu.metu.lngamesml.core.InstancesList;
import edu.metu.lngamesml.core.IntegerVector;
import edu.metu.lngamesml.data.ConfidenceEstimator;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.data.sample.WeightedSampling;
import edu.metu.lngamesml.eval.classification.BasicClassificationEvaluator;
import edu.metu.lngamesml.utils.log.Logging;
import weka.core.Debug;
import weka.core.Instance;
import weka.core.Instances;

import java.util.Random;
import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 2/22/11
 * Time: 1:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class WeightedMajorityVoting extends MajorityVoting {
    protected ConfidenceEstimator CfEstimator;
    protected double Confidences[];

    public WeightedMajorityVoting(int noOfAgents, int samplingRatio){
        super(noOfAgents, samplingRatio);
    }

    protected double agentConfCV(WithDrawnAgent agent, int id, int k, InstancesList[] dataList) {
        double confidence = 0.0;
        Random rnd = new Random();
        for (int i = 0; i < k; i++) {
            int randomIdx = rnd.nextInt(NoOfAgents);
            while (randomIdx == id) {
                randomIdx = rnd.nextInt(NoOfAgents);
            }
            Instances validationData = dataList[randomIdx].getInstances();
            confidence += CfEstimator.estimateConfidence(validationData, agent);
        }
        confidence /= (double)k;
        return confidence;
    }

    public void createAgentsWSampled(String trainingDataset) {
        Confidences = new double[NoOfAgents];
        CfEstimator = new ConfidenceEstimator(NoOfAgents);
        Random rnd = new Random();
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
                /*int randomIdx1 = rnd.nextInt(NoOfAgents);
                int randomIdx2 = rnd.nextInt(NoOfAgents);
                while (randomIdx1 == i || randomIdx2 == i){
                    if (randomIdx1 == i) {
                        randomIdx1 = rnd.nextInt(NoOfAgents);
                    }
                    if (randomIdx2 == i) {
                        randomIdx2 = rnd.nextInt(NoOfAgents);
                    }
                }
                Instances validationData1 = dataList[randomIdx1].getInstances();
                Instances validationData2 = dataList[randomIdx2].getInstances();
                Confidences[i] = (CfEstimator.estimateConfidence(validationData1, agent) + CfEstimator.estimateConfidence(validationData2, agent))/2;*/
                Confidences[i] = agentConfCV(agent, i, 4, dataList);
                Agents.add(i, agent);
            }
        } else {
            throw new NullPointerException("Learner Type can't be null!");
        }
    }

    public void startSimulation(String testDataset) {
        Instances testInstances = FileLoaderFactory.loadFile(testDataset);
        int noOfTestInstances = testInstances.numInstances();
        BasicClassificationEvaluator bcEval = new BasicClassificationEvaluator();
        Instance currentContext;
        double votes[];
        DoubleVector dblVec;
        CategoricalComm electedClass = new CategoricalComm();
        for (int i = 0; i < noOfTestInstances; i++) {
            votes = new double[testInstances.numClasses()];
            currentContext = testInstances.get(i);
            for (int j = 0; j < NoOfAgents; j++) {
                try {
                    int vote = Agents.get(j).classifyInstance(currentContext).getStatementNo();
                    double[] belief = Agents.get(j).getLearner().distributionForInstance(currentContext);
                    votes[vote] += Confidences[j] * belief[vote];
                } catch (Exception e) {
                    Logging.warning(e.getMessage());
                    e.printStackTrace();
                }
            }
            dblVec = new DoubleVector(votes);
            electedClass.setStatementNo(dblVec.maxIndex());
            electedClass.setComStatement(currentContext.stringValue(currentContext.classIndex()));
            bcEval.addPerformanceObservation(electedClass, currentContext);
        }
        Accuracy = bcEval.getAccuracyPercent();
    }
}

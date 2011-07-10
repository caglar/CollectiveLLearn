package edu.metu.lngamesml.collective;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.BasicCognitiveAgent;
import edu.metu.lngamesml.agents.LearnerTypes;
import edu.metu.lngamesml.core.ComparableInstance;
import edu.metu.lngamesml.core.InstanceComparator;
import edu.metu.lngamesml.core.InstancesList;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.data.sample.WeightedSampling;
import edu.metu.lngamesml.utils.random.MersenneTwister;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 7/12/10
 * Time: 11:06 PM
 * TODO: Implement Simon, Kirby's Iterated Learning Model for Machine Learning in this class.
 * Classifiers are trained sequentially using ILM.
 * Train first classifier supervisedly from a dataset.
 */

public class IteratedLearningModel {

    protected int NoOfAgents;
    protected double SamplingRatio;
    protected ArrayList<Agent> Agents = new ArrayList<Agent>();
    protected MersenneTwister MRnd = new MersenneTwister(System.nanoTime());
    protected int NoOfTrainingInstancesPerAgent;
    protected LearnerTypes LType;
    protected double Accuracy;
    protected double[] Confidences;
    protected Evaluation Evaluation;

    public IteratedLearningModel() {
        NoOfAgents = 10;
        SamplingRatio = 20;
        LType = LearnerTypes.C45;
        Accuracy = 0.0;
        Confidences = new double[NoOfAgents];
        Evaluation = null;
    }

    public IteratedLearningModel(int noOfAgents, double samplingRatio, LearnerTypes lType) {
        NoOfAgents = noOfAgents;
        SamplingRatio = samplingRatio;
        LType = lType;
        Confidences = new double[NoOfAgents];
        Accuracy = 0.0;
    }

    private void createLearningData(Instances trainInstances, Instances learningData, BasicCognitiveAgent agent, int agentNo) {
        learningData.clear();
        Comparator<Instance> comparator = new InstanceComparator();
        PriorityBlockingQueue<Instance> correctInstList = new PriorityBlockingQueue<Instance>(NoOfTrainingInstancesPerAgent, comparator);
        try {
            Evaluation = new Evaluation(trainInstances);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < trainInstances.numInstances(); i++) {
            Instance currentInstance = trainInstances.get(i);
            double predictedClassVal = 0.0;
            try {
                predictedClassVal = Evaluation.evaluateModelOnce(agent.getLearner(), currentInstance);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            if (learningData.size() <= NoOfTrainingInstancesPerAgent) {
                if (currentInstance.classValue() != predictedClassVal) {
                    learningData.add(currentInstance);
                } else {
                    try {
                        currentInstance.setWeight(agent.getLearner().distributionForInstance(currentInstance)[(int)predictedClassVal]);
                    } catch (Exception e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    correctInstList.put(currentInstance);
                }
            }
        }
        if (learningData.size() < NoOfTrainingInstancesPerAgent) {
            int remainingInstances = NoOfTrainingInstancesPerAgent - learningData.size();
            for (int i = 0; i < remainingInstances; i++) {
                try {
                    learningData.add(correctInstList.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Confidences[agentNo] = Evaluation.errorRate();
    }

    public void initModel(Instances trainInstances) {

        WeightedSampling wSampling = new WeightedSampling();
        InstancesList dataList = wSampling.getARandomPartition(trainInstances, SamplingRatio);
        Instances sampledTrainData = dataList.getInstances();
        Instances learningData = null;

        for (int i = 0; i < NoOfAgents; i++) {
            BasicCognitiveAgent agent = new BasicCognitiveAgent(LType, null);
            if (i == 0) {
                learningData = sampledTrainData;
            }
            agent.learnFromData(learningData);
            if (i != NoOfAgents -1) {
                createLearningData(trainInstances, learningData, agent, i);
            }
            Agents.add(agent);
        }
    }

    public void startModel(String trainingDataset, String testDataset) {
        Instances trainInstances = FileLoaderFactory.loadFile(trainingDataset);
        Instances testInstances = FileLoaderFactory.loadFile(testDataset);
        NoOfTrainingInstancesPerAgent = (int) (SamplingRatio * trainInstances.numInstances());
        initModel(trainInstances);
    }

    public void startModel(String trainingDataset, String testDataset, LearnerTypes lType, int noOfAgents, double samplingRatio) {
        NoOfAgents = noOfAgents;
        SamplingRatio = samplingRatio;
        LType = lType;
        startModel(trainingDataset, testDataset);
    }

    public double[] getConfidences(){
        return this.Confidences;
    }

    public ArrayList<Agent> getAgents() {
        return this.Agents;
    }

    public double getAccuracy() {
        return Accuracy;
    }
}

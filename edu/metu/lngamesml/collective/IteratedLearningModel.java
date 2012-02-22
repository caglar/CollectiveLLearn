package edu.metu.lngamesml.collective;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.BasicCognitiveAgent;
import edu.metu.lngamesml.agents.LearnerTypes;
import edu.metu.lngamesml.core.DoubleVector;
import edu.metu.lngamesml.core.InstanceComparator;
import edu.metu.lngamesml.core.InstancesList;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.data.sample.WeightedSampling;
import edu.metu.lngamesml.utils.MiscUtils;
import edu.metu.lngamesml.utils.random.MersenneTwister;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public void removeInstances(Instances container, Instances tobeRemoved) {
        for (int i = 0; i < tobeRemoved.size(); i++) {
            for (int j = 0; j < container.size(); j++) {
                boolean flag = false;
                for (int m = 0; m < container.get(j).numAttributes(); m++) {
                    if (container.get(j).value(m) != tobeRemoved.get(i).value(m))
                    {
                        break;
                    } else if (m == (container.get(j).numAttributes() - 1)) {
                        flag = true;
                    }
                }
                if (flag) {
                    container.remove(j);
                }
            }
        }
    }

    private double getIncorrectWeight(double prediction, double error)
    {
        double alpha = Math.log((1-error)/error)/2;
        double w = Math.exp(prediction * alpha);
        return w;
    }

    private void createLearningData(Instances trainInstances, Instances learningData, BasicCognitiveAgent agent, int agentNo) {
        learningData.clear();
        Comparator<Instance> comparator = new InstanceComparator();
        PriorityBlockingQueue<Instance> correctInstList = new PriorityBlockingQueue<Instance>(NoOfTrainingInstancesPerAgent, comparator);
        System.out.println("Creating local training data.");
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
            if (learningData.size() < NoOfTrainingInstancesPerAgent) {
                if (currentInstance.classValue() != predictedClassVal) {

                    double predictedDist[];
                    try {
                         predictedDist = agent.getLearner().distributionForInstance(currentInstance);
                         double  prob = MiscUtils.dmax(predictedDist);
                         double err = Evaluation.errorRate();
                         double w = getIncorrectWeight(prob, err);
                         currentInstance.setWeight(w);
                         learningData.add(currentInstance);
                         //System.out.println("Weight: " + learningData.lastInstance().weight());
                    } catch (Exception e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                } else {
                    try {

                        double dist[]= agent.getLearner().distributionForInstance(currentInstance);
                        dist = MiscUtils.revNormalize(dist);
                        currentInstance.setWeight(dist[(int)predictedClassVal]);
                        //System.out.println("Weight " + dist[(int)predictedClassVal]);
                    } catch (Exception e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    correctInstList.put(currentInstance);
                }
            }
        }
        System.out.println("Adding additional data");
        if (learningData.size() < NoOfTrainingInstancesPerAgent) {
            int remainingInstances = NoOfTrainingInstancesPerAgent - learningData.size();
            //System.out.println("Number of training instances per agent " + remainingInstances);
            for (int i = 0; i < remainingInstances; i++) {
                try {
                    learningData.add(correctInstList.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println();
        Confidences[agentNo] = 1 - Evaluation.errorRate();
    }

    public void initModel(Instances trainInstances) throws Exception {

        WeightedSampling wSampling = new WeightedSampling();
        InstancesList dataList = wSampling.getARandomPartition(trainInstances, SamplingRatio);
        Instances sampledTrainData = dataList.getInstances();
        Instances learningData = null;

        for (int i = 0; i < NoOfAgents; i++) {
            //System.out.println("Agent " + i + " is being trained");

            BasicCognitiveAgent agent = new BasicCognitiveAgent(LType, null);
            Instances localTrainingInstances = (Instances) MiscUtils.deepCopy(trainInstances);
            //System.out.println("Instances are copied.");
            if (i == 0) {
                learningData = sampledTrainData;
            }
            //System.out.println("Learning data size: " + learningData.size());
            agent.learnFromData(learningData);
            removeInstances(localTrainingInstances, learningData);
            createLearningData(localTrainingInstances, learningData, agent, i);
            //}
            Agents.add(agent);
        }
    }

    public void testModel(Instances testInstances) throws Exception {
        System.out.println("Testing model with weighted majority voting");
        int noOfCorrectlyClassified = 0;
        int noOfIncorrectlyClassified = 0;
        int noOfClasses = testInstances.numClasses();
        Evaluation = new Evaluation(testInstances);
        for (Instance inst : testInstances) {
            DoubleVector votes = new DoubleVector();
            int i = 0;
            for (Agent ag : Agents) {
                ag.forgetFocusedCat();
                int classVal = ag.speak(inst).getStatementNo();
                //System.out.println("Selected class " + classVal);
                votes.addToValue(classVal, Confidences[i]);
                i++;
            }
            //System.out.println("Selected class:" + votes.maxIndex());
            if (((int) inst.classValue()) == votes.maxIndex()) {
                Evaluation.evaluateModelOnce(votes.maxIndex(), inst);
                noOfCorrectlyClassified += inst.weight();
                //System.out.println("Correct " + noOfCorrectlyClassified);
            } else {
                noOfIncorrectlyClassified += inst.weight();
                //System.out.println("Wrong ");
            }
        }
        Accuracy = ((double)noOfCorrectlyClassified) * 100 / ((double)noOfCorrectlyClassified + (double)noOfIncorrectlyClassified);
        //System.out.println("Errors: " + Evaluation.errorRate());
    }

    public void startModel(String trainingDataset, String testDataset) {
        Instances trainInstances = FileLoaderFactory.loadFile(trainingDataset);
        Instances testInstances = FileLoaderFactory.loadFile(testDataset);
        NoOfTrainingInstancesPerAgent = (int) (SamplingRatio / 100 * trainInstances.numInstances());
        try {
            initModel(trainInstances);
            testModel(testInstances);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void startModel(String trainingDataset) {
        Instances trainInstances = FileLoaderFactory.loadFile(trainingDataset);
        NoOfTrainingInstancesPerAgent = (int) (SamplingRatio / 100 * trainInstances.numInstances());
        try {
            initModel(trainInstances);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void startModel(String trainingDataset, String testDataset, LearnerTypes lType, int noOfAgents, double samplingRatio) {
        NoOfAgents = noOfAgents;
        SamplingRatio = samplingRatio;
        LType = lType;
        startModel(trainingDataset, testDataset);
    }


    public double[] getConfidences() {
        return this.Confidences;
    }

    public ArrayList<Agent> getAgents() {
        return this.Agents;
    }

    public double getAccuracy() {
        return Accuracy;
    }
}

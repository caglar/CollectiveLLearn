package edu.metu.lngamesml.alt;

import edu.metu.lngamesml.agents.WithDrawnAgent;
import edu.metu.lngamesml.core.ClassBins;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.data.sample.cluster.ClusterTrainingDataHarvester;
import edu.metu.lngamesml.utils.log.Logging;
import weka.core.Instances;

import java.util.Collections;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Nov 6, 2010
 * Time: 10:41:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClassClusterMajorityVoting extends MajorityVoting {
    private int NoOfClustersPerClassifier = 2;

    public ClassClusterMajorityVoting(int noOfAgents, int noOfClustersPerClassifier) {
        this.NoOfAgents = noOfAgents;
        this.NoOfClustersPerClassifier = noOfClustersPerClassifier;
    }

    public void createAgents(String trainingDataset) {
        ClusterTrainingDataHarvester cTSampling = new ClusterTrainingDataHarvester(NoOfAgents);
        Instances trainingData = FileLoaderFactory.loadFile(trainingDataset);
        int noOfClasses = trainingData.numClasses();
        ClassBins classBins = new ClassBins(noOfClasses);
        classBins.createBins(trainingData);
        Logging.info("No of agents: " + NoOfAgents);
        Random rnd = new Random(1);
        Instances agentInstances[] = new Instances[NoOfAgents];
        if (LType != null) {
            for (int k = 0; k < noOfClasses; k++) {
                Instances classInstances = classBins.getInstancesForCluster(k);
                if (classInstances.size() > 0) {
                    Instances tData[] = cTSampling.getEnsembleTrainingData(classInstances);
                    for (int i = 0; i < NoOfAgents; i++) {
                        if (i <= (NoOfAgents - NoOfClustersPerClassifier)) {
                            for (int j = 0; j < NoOfClustersPerClassifier; j++) {
                                Instances learningData = tData[i + j];
                                if (agentInstances[i] == null) {
                                    agentInstances[i] = learningData;
                                } else {
                                    agentInstances[i].addAll(learningData);
                                }
                            }
                        } else {
                            int j = 0;
                            for (; (i + j) < NoOfAgents; j++) {
                                Instances learningData = tData[j + i];
                                if (agentInstances[i] == null) {
                                    agentInstances[i] = learningData;
                                } else {
                                    agentInstances[i].addAll(learningData);
                                }
                            }
                            int noOfRemaining = NoOfClustersPerClassifier - j;
                            for (int n = 0; n < noOfRemaining; n++) {
                                int idx = (rnd.nextInt() % (NoOfAgents - 1));
                                if (idx >= 0 && tData[idx] != null) {
                                    Instances learningData = tData[idx];
                                    agentInstances[i].addAll(learningData);
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < NoOfAgents; i++) {
                if (agentInstances[i] != null) {
                    Collections.shuffle(agentInstances[i]);
                    WithDrawnAgent agent = new WithDrawnAgent();
                    agent.setLearningType(LType, null);
                    agent.setAgentId(i);
                    Instances learningData = agentInstances[i];
                    agent.learnFromData(learningData);
                    Agents.add(i, agent);
                }
            }
        } else {
            throw new NullPointerException("Learner Type can't be null!");
        }
    }
}

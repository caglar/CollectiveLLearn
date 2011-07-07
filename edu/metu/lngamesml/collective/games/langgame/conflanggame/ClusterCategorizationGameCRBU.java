package edu.metu.lngamesml.collective.games.langgame.conflanggame;

import edu.metu.lngamesml.agents.BasicCognitiveAgent;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.data.sample.cluster.ClusterTrainingDataHarvester;
import edu.metu.lngamesml.exception.FaultyInputException;
import edu.metu.lngamesml.utils.log.Logging;
import weka.core.Instances;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Oct 31, 2010
 * Time: 3:36:30 PM
 * To change this template use File | Settings | File Templates.
 */

public class ClusterCategorizationGameCRBU extends CategorizationGameCRBU {

    private int NoOfClustersPerClassifier = 2;

    public ClusterCategorizationGameCRBU(int noOfClustersPerClassifier) {
        super();
        try {
            setNoOfClustersPerClassifier(noOfClustersPerClassifier);
        } catch (FaultyInputException e) {
            Logging.warning(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void createAgents(String trainingDataset) {
        setUseConfidences(true);
        ClusterTrainingDataHarvester cTSampling = new ClusterTrainingDataHarvester(NoOfAgents);
        Instances trainingData = FileLoaderFactory.loadFile(trainingDataset);
        Instances tData[] = cTSampling.getEnsembleTrainingData(trainingData);
        Random rnd = new Random(1);
        if (LType != null) {
            for (int i = 0; i < NoOfAgents; i++) {
                BasicCognitiveAgent agent = new BasicCognitiveAgent(LType, null);
                agent.setId(i);
                if (i < (NoOfAgents - NoOfClustersPerClassifier)) {
                    for (int j = 0; j < NoOfClustersPerClassifier; j++) {
                        Instances learningData = tData[i + j];
                        agent.learnFromData(learningData);
                    }
                } else {
                    for (int j = i; j < NoOfAgents; j++) {
                        Instances learningData = tData[j];
                        agent.learnFromData(learningData);
                    }
                    int noOfRemaining = NoOfAgents - i;
                    for (int j = 0; j < noOfRemaining; j++) {
                        int idx = rnd.nextInt(NoOfAgents - 1) ;
                        Instances learningData = tData[idx];
                        agent.learnFromData(learningData);
                    }
                }
                Agents.add(i, agent);
            }
        } else {
            throw new NullPointerException("Learner Type can't be null!");
        }
        this.calcConfidences(trainingDataset);       
    }

    public int getNoOfClustersPerClassifier() {
        return NoOfClustersPerClassifier;
    }

    public void setNoOfClustersPerClassifier(int noOfClustersPerClassifier) throws FaultyInputException {
        if (noOfClustersPerClassifier < (NoOfAgents / 2)) {
            NoOfClustersPerClassifier = noOfClustersPerClassifier;
        } else {
            throw new FaultyInputException("Number of clusters per agent can't be larger than the half of the number of agents");
        }
    }
}

package edu.metu.lngamesml.games.langgame.conflanggame;

import edu.metu.lngamesml.agents.BasicCognitiveAgent;
import edu.metu.lngamesml.agents.beliefs.BeliefUpdaterFactory;
import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.eval.classification.BasicClassificationEvaluator;
import edu.metu.lngamesml.eval.game.AgentsIndividualPerf;
import edu.metu.lngamesml.games.Convergence;
import edu.metu.lngamesml.games.langgame.BasicLanguageGame;
import weka.core.Instance;
import weka.core.Instances;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 15, 2010
 * Time: 2:56:37 PM
 * To change this template use File | Settings | File Templates.
 */

public class ConfidenceLGame extends BasicLanguageGame {

    private Convergence Converge = null;
    private int noOfSuccess = 0;
    private int noOfFails = 0;

    public ConfidenceLGame() {
        super();
    }

    public ConfidenceLGame(int noOfAgents, int samplingRatio) {
        super(noOfAgents, samplingRatio);
    }

    protected void adjustBeliefs (CategoricalComm sCat, CategoricalComm hCat, boolean isSuccess) {
        if (UseBeliefUpdates) {
            BeliefUpdaterFactory.init();
            BeliefUpdaterFactory.updateBeliefs(sCat, hCat, isSuccess);
        }
    }

    protected void updateCommunication (BasicCognitiveAgent sAgent, BasicCognitiveAgent hAgent, CategoricalComm sCat, CategoricalComm hCat) {
        CategoricalComm hearCat = null;
        try {
            hearCat = (CategoricalComm) hCat.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (sCat.equals(hCat)) {
            adjustBeliefs(sCat, hCat, true);
            noOfSuccess++;
        } else {
            noOfFails++;
            Converge.balanceTable(sCat, hCat);
            adjustBeliefs(sCat, hCat, false);
            hearCat.setConfidence(hCat.getConfidence());
            hearCat.setComStatement(sCat.getComStatement());
            hearCat.setStatementNo(sCat.getStatementNo());
        }

        Agents.get(hAgent.getId()).hear(hearCat);
        Agents.get(sAgent.getId()).hear(sCat);
    }

    @Override
    public void playGames(String testDataset) throws Exception {
        if (!this.UseConfidences) {
            throw new Exception("In confidence based Game useConfidences must be true");
        } else {
            Instances testInstances = FileLoaderFactory.loadFile(testDataset);
            BasicCognitiveAgent bcaAgent1;
            BasicCognitiveAgent bcaAgent2;
            int noOfTestInstances = testInstances.numInstances();
            BasicClassificationEvaluator bcEval = new BasicClassificationEvaluator();
            if (Converge == null) {
                Converge = new Convergence(NoOfAgents);
            }
            Instance currentContext;
            CategoricalComm currentClassVal = null;

            //System.out.println("Play games!!");
            for (int i = 0; i < noOfTestInstances; i++) {
                Converge.emptyTable();
                currentContext = testInstances.get(i);
                while (!Converge.isConverged(Agents, currentContext)) {
                    bcaAgent1 = (BasicCognitiveAgent) getRandomAgent();
                    bcaAgent2 = (BasicCognitiveAgent) getRandomAgent();
                    while (bcaAgent1.equals(bcaAgent2)) {
                        bcaAgent2 = (BasicCognitiveAgent) getRandomAgent();
                    }
                    CategoricalComm cat1 = bcaAgent1.speak(currentContext);
                    CategoricalComm cat2 = bcaAgent2.speak(currentContext);
                    double confidence1 = cat1.getConfidence() * Math.pow(Confidences[bcaAgent1.getId()], 2);
                    double confidence2 = cat2.getConfidence() * Math.pow(Confidences[bcaAgent2.getId()], 2);
                    if (confidence1 > confidence2) {
                        updateCommunication(bcaAgent1, bcaAgent2, cat1, cat2);
                    } else if (confidence1 == confidence2) {
                        int speakerCatNo = ((new Random(System.nanoTime()).nextInt()) % 2);
                        if (speakerCatNo == 0){
                            updateCommunication(bcaAgent1, bcaAgent2, cat1, cat2);
                        } else {
                            updateCommunication(bcaAgent2, bcaAgent1, cat2, cat1);
                        }
                    } else {
                        updateCommunication(bcaAgent2, bcaAgent1, cat2, cat1);
                    }
                }
                currentClassVal = Converge.getConvergedCategory();
                bcEval.addPerformanceObservation(currentClassVal, currentContext);
                prepareForNewGame();
            }

            //System.out.println("Total Number of failures:" + noOfFails);
            //System.out.println("Total Number of successes:" + noOfSuccess);

            double avgTime = (double)(noOfFails+noOfSuccess)/(double)noOfTestInstances;
            //System.out.println("Average time: " + avgTime);

            int noOfTestEx = testInstances.size();

            /*for(int i = 0; i < this.NoOfAgents; i++){
                System.out.println("Accuracy for agent " + i + ": " + agPerf.getAccuracyForSpecificAgent(i, noOfTestEx));
            } */
            //System.out.println(bcEval.getPerformanceMetrics());
            Accuracy = bcEval.getAccuracyPercent();
        }
    }
}

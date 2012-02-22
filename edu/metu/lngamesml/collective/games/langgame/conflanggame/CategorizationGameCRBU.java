package edu.metu.lngamesml.collective.games.langgame.conflanggame;

import edu.metu.lngamesml.agents.BasicCognitiveAgent;
import edu.metu.lngamesml.agents.beliefs.BeliefUpdaterFactory;
import edu.metu.lngamesml.agents.beliefs.SigmoidFunctionTypes;
import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.collective.games.langgame.CategorizationGame;
import edu.metu.lngamesml.criterions.ConvergenceCriterion;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.eval.classification.BasicClassificationEvaluator;
import edu.metu.lngamesml.network.Network;
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

public class CategorizationGameCRBU extends CategorizationGame {

    private final String GameName = "Categorization Game with Confidence Rated Belief Score Updates";
    private ConvergenceCriterion converge = null;
    private int noOfSuccess = 0;
    private int noOfFails = 0;
    private double scalingPow = 2;
    private boolean ReverseUpdateOrder = false;

    public CategorizationGameCRBU() {
        super();
    }

    public CategorizationGameCRBU(int noOfAgents, int samplingRatio) {
        super(noOfAgents, samplingRatio);
    }

    protected void adjustBeliefs (CategoricalComm sCat, CategoricalComm hCat, SigmoidFunctionTypes sigmoidFunctionType, boolean isSuccess) {
        if (UseBeliefUpdates) {
            BeliefUpdaterFactory.init();
            BeliefUpdaterFactory.updateBeliefs(sCat, hCat, sigmoidFunctionType, isSuccess);
        }
    }

    protected void updateCommunication (BasicCognitiveAgent sAgent, BasicCognitiveAgent hAgent, CategoricalComm sCat, CategoricalComm hCat, SigmoidFunctionTypes sigmoidFunctionType) {
        CategoricalComm hearCat = null;
        try {
            hearCat = (CategoricalComm) hCat.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (sCat.equals(hCat)) {
            adjustBeliefs(sCat, hCat, sigmoidFunctionType, true);
            noOfSuccess++;
        } else {
            noOfFails++;
            converge.balanceTable(sCat, hCat);
            adjustBeliefs(sCat, hCat, sigmoidFunctionType, false);
            hearCat.setConfidence(hCat.getConfidence());
            hearCat.setComStatement(sCat.getComStatement());
            hearCat.setStatementNo(sCat.getStatementNo());
        }

        Agents.get(hAgent.getId()).hear(hearCat);
        Agents.get(sAgent.getId()).hear(sCat);
    }

    public void reverseAgentUpdates(boolean isInverseOrder){
        this.ReverseUpdateOrder = isInverseOrder;
    }

    @Override
    public void playGames(String testDataset, SigmoidFunctionTypes sigmoidFunctionType) throws Exception {
        if (!this.UseConfidences) {
            throw new Exception("In confidence based Game useConfidences must be true");
        } else {
            Instances testInstances = FileLoaderFactory.loadFile(testDataset);
            BasicCognitiveAgent bcaAgent1;
            BasicCognitiveAgent bcaAgent2;
            int noOfTestInstances = testInstances.numInstances();
            BasicClassificationEvaluator bcEval = new BasicClassificationEvaluator();

            noOfSuccess = 0;
            noOfFails = 0;

            if (converge == null) {
                converge = new ConvergenceCriterion(NoOfAgents);
            }
            Instance currentContext;
            CategoricalComm currentClassVal = null;

            for (int i = 0; i < noOfTestInstances; i++) {
                converge.emptyTable();
                currentContext = testInstances.get(i);
                while (!converge.isConverged(Agents, currentContext)) {
                    bcaAgent1 = (BasicCognitiveAgent) getRandomAgent();
                    bcaAgent2 = (BasicCognitiveAgent) getRandomAgent();
                    while (bcaAgent1.equals(bcaAgent2)) {
                        bcaAgent2 = (BasicCognitiveAgent) getRandomAgent();
                    }
                    CategoricalComm cat1 = bcaAgent1.speak(currentContext);
                    CategoricalComm cat2 = bcaAgent2.speak(currentContext);
                    double confidence1 = cat1.getConfidence() * Math.pow(Confidences[bcaAgent1.getId()], scalingPow);
                    double confidence2 = cat2.getConfidence() * Math.pow(Confidences[bcaAgent2.getId()], scalingPow);
                    if (confidence1 > confidence2) {
                        if (!ReverseUpdateOrder) {
                            updateCommunication(bcaAgent1, bcaAgent2, cat1, cat2, sigmoidFunctionType);
                        } else {
                            updateCommunication(bcaAgent2, bcaAgent1, cat2, cat1, sigmoidFunctionType);
                        }

                    } else if (confidence1 == confidence2) {
                        int speakerCatNo = ((new Random(System.nanoTime()).nextInt()) % 2);
                        if (speakerCatNo == 0){
                            updateCommunication(bcaAgent1, bcaAgent2, cat1, cat2, sigmoidFunctionType);
                        } else {
                            updateCommunication(bcaAgent2, bcaAgent1, cat2, cat1, sigmoidFunctionType);
                        }
                    } else {
                        if (!ReverseUpdateOrder) {
                            updateCommunication(bcaAgent2, bcaAgent1, cat2, cat1, sigmoidFunctionType);
                        } else {
                            updateCommunication(bcaAgent1, bcaAgent2, cat1, cat2, sigmoidFunctionType);
                        }
                    }
                }
                currentClassVal = converge.getConvergedCategory();
                bcEval.addPerformanceObservation(currentClassVal, currentContext);
                prepareForNewGame();
            }

            double avgTime = (double)(noOfFails+noOfSuccess)/(double)noOfTestInstances;
            System.out.println("Fail/Success Rate " + (double)((double)noOfFails/(double)noOfSuccess));
            System.out.println("Average dialogs per example: " + avgTime );
            int noOfTestEx = testInstances.size();

            Accuracy = bcEval.getAccuracyPercent();
        }
    }
}

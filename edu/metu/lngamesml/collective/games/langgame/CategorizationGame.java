package edu.metu.lngamesml.collective.games.langgame;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.BasicCognitiveAgent;
import edu.metu.lngamesml.agents.LearnerTypes;
import edu.metu.lngamesml.agents.beliefs.BeliefUpdaterFactory;
import edu.metu.lngamesml.agents.beliefs.SigmoidFunctionTypes;
import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.collective.IteratedLearningModel;
import edu.metu.lngamesml.collective.games.LGame;
import edu.metu.lngamesml.core.InstancesList;
import edu.metu.lngamesml.criterions.ConvergenceCriterion;
import edu.metu.lngamesml.data.ConfidenceEstimator;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.data.sample.WeightedSampling;
import edu.metu.lngamesml.eval.classification.BasicClassificationEvaluator;
import edu.metu.lngamesml.eval.game.BasicGameEvaluator;
import edu.metu.lngamesml.network.Network;
import edu.metu.lngamesml.stats.nosql.Game;
import edu.metu.lngamesml.stats.nosql.game.AgentStat;
import edu.metu.lngamesml.stats.nosql.game.RunningAgentStat;
import edu.metu.lngamesml.stats.nosql.game.Stat;
import edu.metu.lngamesml.stats.sqllite.game.RunningStat;
import edu.metu.lngamesml.utils.log.Logging;
import edu.metu.lngamesml.utils.random.MersenneTwister;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 15, 2010
 * Time: 2:56:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class CategorizationGame implements LGame {
    private final String GameName = "Categorization Game";
    private Network net;
    private boolean RunningStatsFlag;

    protected int NoOfAgents;
    protected int SamplingRatio;
    protected ArrayList<Agent> Agents = new ArrayList<Agent>();
    protected MersenneTwister MRnd = new MersenneTwister(System.nanoTime());
    protected LearnerTypes LType;
    protected ConfidenceEstimator CfEstimator;
    protected double[] Confidences;
    protected boolean UseConfidences = false;
    protected BasicGameEvaluator BGEval = null;
    protected boolean UseBeliefUpdates = false;
    protected boolean UseMStatsDb = false;
    protected Game MGame = new Game();
    protected double Accuracy = 0.0;

    public CategorizationGame() {
        RunningStatsFlag = false;
        NoOfAgents = 10;
        SamplingRatio = 40;
        MGame = new Game();
    }

    public CategorizationGame(int noOfAgents, int samplingRatio) {
        NoOfAgents = noOfAgents;
        SamplingRatio = samplingRatio;
    }

    public void setSamplingRatio(int samplingRatio) {
        SamplingRatio = samplingRatio;
    }

    public void setUseBeliefUpdates(boolean useBeliefUpdates) {
        UseBeliefUpdates = useBeliefUpdates;
    }

    public void setNoOfAgents(int noOfAgents) {
        NoOfAgents = noOfAgents;
    }

    public void setUseConfidences(boolean useConfidences) {
        UseConfidences = useConfidences;
    }

    public boolean isUseMStatsDb() {
        return UseMStatsDb;
    }

    public void setUseMStatsDb(boolean useMStatsDb) {
        UseMStatsDb = useMStatsDb;
    }

    public Game getGame() {
        return MGame;
    }

    protected void calcConfidences(String trainingDataset) {
        CfEstimator = new ConfidenceEstimator(NoOfAgents);
        Instances insts = FileLoaderFactory.loadFile(trainingDataset);
        Confidences = CfEstimator.estimateConfidences(insts, Agents);
    }

    protected void calcConfidence(Instances insts) {
        CfEstimator = new ConfidenceEstimator(NoOfAgents);
        CfEstimator.setSamplingRatio(100);
        Confidences = CfEstimator.estimateConfidences(insts, Agents);
    }

    protected double agentConfCV(Agent agent, int k, InstancesList[] dataList) {
        double confidence = 0.0;
        Random rnd = new Random();
        for (int i = 0; i < k; i++) {
            int randomIdx = rnd.nextInt(NoOfAgents);
            while (randomIdx == agent.getId()) {
                randomIdx = rnd.nextInt(NoOfAgents);
            }
            Instances validationData = dataList[randomIdx].getInstances();
            confidence += CfEstimator.estimateConfidence(validationData, agent);
        }
        confidence /= (double) k;
        return confidence;
    }

    @Override
    public void createAgents(String trainingDataset) {
        WeightedSampling wSampling = new WeightedSampling(trainingDataset, NoOfAgents);
        if (UseConfidences) {
            Confidences = new double[NoOfAgents];
            CfEstimator = new ConfidenceEstimator(NoOfAgents);
        }
        try {
            wSampling.setSamplingRatio(SamplingRatio);
        } catch (Exception e) {
            Logging.log(Level.WARNING, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        InstancesList[] dataList = wSampling.partitionDataset();
        Random rnd = new Random();
        if (LType != null) {
            for (int i = 0; i < NoOfAgents; i++) {
                BasicCognitiveAgent agent = new BasicCognitiveAgent(LType, null);
                Instances learningData = dataList[i].getInstances();

                agent.setId(i);
                agent.learnFromData(learningData);
                if (UseConfidences) {
                    Confidences[i] = agentConfCV(agent, 4, dataList);
                }
                Agents.add(i, agent);
            }
        } else {
            throw new NullPointerException("Learner Type can't be null!");
        }
    }

    public void createILMAgents(String trainingDataset) {
        IteratedLearningModel iteratedLearningModel = new IteratedLearningModel(NoOfAgents, SamplingRatio, LType);
        iteratedLearningModel.startModel(trainingDataset);
        Confidences = iteratedLearningModel.getConfidences();
        Agents = iteratedLearningModel.getAgents();
    }

    public void setLearningType(LearnerTypes lType) {
        LType = lType;
    }

    protected void setGameProps(String testDataset, String gName) {
        MGame.setNoOfAgents(NoOfAgents);
        MGame.setGameName(gName);
        MGame.setTestDataset(testDataset);
        MGame.setIsBeliefUpdates(UseBeliefUpdates);
    }

    protected Agent getRandomAgent() {
        int agentNo = MRnd.nextInt(NoOfAgents);
        return Agents.get(agentNo);
    }

    protected void prepareForNewGame() {
        for (Agent bca : Agents) {
            ((BasicCognitiveAgent) bca).prepareForNewGame();
        }
    }

    protected void addRunningAgentStatsToGame(List<RunningAgentStat> runningAgentStats) {
        for (RunningAgentStat rAgentStat : runningAgentStats) {
            MGame.addRunningAgentStats(rAgentStat);
        }
    }

    protected void addRunningAgentStatsToAgentStats(List<RunningAgentStat> runningAgentStats) {
        for (RunningAgentStat rAgentStat : runningAgentStats) {
            AgentStat agentStat = new AgentStat();
            agentStat.setAgentID(rAgentStat.getAgentId());
            agentStat.setNoOfFails(rAgentStat.getNoOfFails());
            agentStat.setNoOfSuccess(rAgentStat.getNoOfSuccess());
            agentStat.setAccuracy(rAgentStat.getCurrentAccuracy());
            //agentStat.setValidationAccuracy(Confidences[rAgentStat.getAgentId()]);
            MGame.addAgentStats(agentStat);
        }
    }

    protected void addFailuresToAgentStats(RunningAgentStat rAgentStat1, RunningAgentStat rAgentStat2) {
        rAgentStat1.addNoOfFailures();
        rAgentStat2.addNoOfFailures();
    }

    protected void addSuccessToAgentStats(RunningAgentStat rAgentStat1, RunningAgentStat rAgentStat2) {
        rAgentStat1.addNoOfSuccesses();
        rAgentStat2.addNoOfSuccesses();
    }

    protected void addStatToGame(Stat stat, double accuracy, int noOfFails, int noOfSuccesses) {
        stat.setAccuracy(accuracy);
        stat.setNoOfFails(noOfFails);
        stat.setNoOfSucesses(noOfSuccesses);
        MGame.setStats(stat);

    }

    @Override
    public void playGames(String testDataset, SigmoidFunctionTypes sigmoidFunctionType) throws Exception {
        BasicCognitiveAgent bcaSpeaker;
        BasicCognitiveAgent bcaHearer;
        ConvergenceCriterion convergenceCriterion = new ConvergenceCriterion(NoOfAgents);
        BasicClassificationEvaluator bcEval = new BasicClassificationEvaluator();

        Instances testInstances = FileLoaderFactory.loadFile(testDataset);
        int noOfTestInstances = testInstances.numInstances();

        BGEval = new BasicGameEvaluator(NoOfAgents, noOfTestInstances);

        Instance currentContext = null;
        CategoricalComm currentClassVal = null;
        boolean isSuccess = false;

        int noOfSuccess = 0;
        int noOfFails = 0;

        List<RunningAgentStat> rAgentStats = new ArrayList<RunningAgentStat>();
        RunningStat rStat = null;
        Stat stat = null;
        if (RunningStatsFlag) {
            rStat = new RunningStat();
            stat = new Stat();
        }

        setGameProps(testDataset, GameName);

        for (int i = 0; i < noOfTestInstances; i++) {
            convergenceCriterion.emptyTable();
            currentContext = testInstances.get(i);
            while (!convergenceCriterion.isConverged(Agents, currentContext, rAgentStats)) {
                isSuccess = true;
                bcaSpeaker = (BasicCognitiveAgent) getRandomAgent();
                bcaHearer = (BasicCognitiveAgent) getRandomAgent();
                while (bcaHearer.equals(bcaSpeaker)) {
                    bcaHearer = (BasicCognitiveAgent) getRandomAgent();
                }
                CategoricalComm speakerCat = bcaSpeaker.speak(currentContext);
                CategoricalComm hearerCat = bcaHearer.speak(currentContext);
                if (!speakerCat.equals(hearerCat)) {
                    addFailuresToAgentStats(rAgentStats.get(bcaSpeaker.getId()), rAgentStats.get(bcaHearer.getId()));
                    if (RunningStatsFlag) {
                        rStat.incrementNoOfFails();
                    }
                    isSuccess = false;
                    convergenceCriterion.balanceTable(speakerCat, hearerCat);
                    if (UseBeliefUpdates) {
                        BeliefUpdaterFactory.init();
                        BeliefUpdaterFactory.updateBeliefs(speakerCat, hearerCat, sigmoidFunctionType, false);
                    }
                    Agents.get(bcaHearer.getId()).hear(speakerCat);
                    noOfFails++;
                } else {
                    addSuccessToAgentStats(rAgentStats.get(bcaSpeaker.getId()), rAgentStats.get(bcaHearer.getId()));
                    if (RunningStatsFlag){
                        rStat.incrementNoOfSuccesses();
                    }
                    if (UseBeliefUpdates) {
                        BeliefUpdaterFactory.init();
                        BeliefUpdaterFactory.updateBeliefs(speakerCat, hearerCat, sigmoidFunctionType, false);
                    }
                    noOfSuccess++;
                }
                //testInstances.remove(currentContext);
                //rStat.setTimestep(sWatch.getDuration());
                if (RunningStatsFlag) {
                    rStat.setNoOfItemsProcessed(i);
                }
                BGEval.addMeasurements(bcaSpeaker.getId(), bcaHearer.getId(), i, isSuccess);
            }
            currentClassVal = convergenceCriterion.getConvergedCategory();
            bcEval.addPerformanceObservation(currentClassVal, currentContext);
            prepareForNewGame();
        }
        double avgTime = (double) (noOfFails + noOfSuccess) / (double) noOfTestInstances;
        System.out.println("Fail/Success Rate " + (double)((double)noOfFails/(double)noOfSuccess));
        System.out.println("Average dialogs per example: " + avgTime );
        Accuracy = bcEval.getAccuracyPercent();
    }

    public BasicGameEvaluator getBasicGameEval() {
        if (BGEval == null) {
            BGEval = new BasicGameEvaluator(0, 0);
        }
        return BGEval;
    }

    public void enableRunningStatsFlag(boolean runningStatsFlag) {
        RunningStatsFlag = runningStatsFlag;
    }

    public double getAccuracy() {
        return Accuracy;
    }

    public Network getNet() {
        return net;
    }

    public void setNet(Network net) {
        this.net = net;
    }

    @Override
    public void setAgentsOnGraph(int x, int y) {
    }
}

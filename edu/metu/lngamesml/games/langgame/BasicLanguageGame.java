package edu.metu.lngamesml.games.langgame;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.BasicCognitiveAgent;
import edu.metu.lngamesml.agents.LearnerTypes;
import edu.metu.lngamesml.agents.beliefs.BeliefUpdaterFactory;
import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.core.InstancesList;
import edu.metu.lngamesml.data.ConfidenceEstimator;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.data.sample.WeightedSampling;
import edu.metu.lngamesml.eval.classification.BasicClassificationEvaluator;
import edu.metu.lngamesml.eval.game.BasicGameEvaluator;
import edu.metu.lngamesml.games.Convergence;
import edu.metu.lngamesml.games.LGame;
import edu.metu.lngamesml.stats.nosql.Game;
import edu.metu.lngamesml.stats.nosql.game.AgentStat;
import edu.metu.lngamesml.stats.nosql.game.RunningAgentStat;
import edu.metu.lngamesml.stats.nosql.game.RunningStat;
import edu.metu.lngamesml.stats.nosql.game.Stat;
import edu.metu.lngamesml.utils.log.Logging;
import edu.metu.lngamesml.utils.random.MersenneTwister;

import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 15, 2010
 * Time: 2:56:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicLanguageGame implements LGame {

    protected int NoOfAgents;
    protected int SamplingRatio;
    protected ArrayList<Agent> Agents = new ArrayList<Agent>();
    protected MersenneTwister MRnd = new MersenneTwister(System.nanoTime());
    protected LearnerTypes LType;
    protected ConfidenceEstimator CfEstimator;
    protected double []Confidences;
    protected boolean UseConfidences = false;
    protected BasicGameEvaluator BGEval = null;
    protected boolean UseBeliefUpdates = false;
    protected boolean UseMStatsDb = false;
    protected Game MGame = new Game();
    private final String GameName = "BasicLanguageGame";
    protected double Accuracy = 0.0;

    public BasicLanguageGame() {
        NoOfAgents = 10;
        SamplingRatio = 40;
        MGame = new Game();

    }

    public BasicLanguageGame(int noOfAgents, int samplingRatio) {
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
    
    @Override
    public void createAgents(String trainingDataset) {
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
                BasicCognitiveAgent agent = new BasicCognitiveAgent(LType, null);
                Instances learningData = dataList[i].getInstances();
                agent.setId(i);
                agent.learnFromData(learningData);
                Agents.add(i, agent);
            }
        } else {
            throw new NullPointerException("Learner Type can't be null!");
        }
        if (UseConfidences) {
            calcConfidences(trainingDataset);
        }
    }

    public void setLearningType(LearnerTypes lType) {
        LType = lType;
    }

    protected void setGameProps(String testDataset, String gName){
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

    protected void addRunningAgentStatsToGame(List<RunningAgentStat> runningAgentStats){
        for(RunningAgentStat rAgentStat: runningAgentStats){
            MGame.addRunningAgentStats(rAgentStat);
        }
    }

    protected void addRunningAgentStatsToAgentStats(List<RunningAgentStat> runningAgentStats){
        for(RunningAgentStat rAgentStat: runningAgentStats){
            AgentStat agentStat = new AgentStat();
            agentStat.setAgentID(rAgentStat.getAgentId());
            agentStat.setNoOfFails(rAgentStat.getNoOfFails());
            agentStat.setNoOfSuccess(rAgentStat.getNoOfSuccess());
            agentStat.setAccuracy(rAgentStat.getCurrentAccuracy());
            //agentStat.setValidationAccuracy(Confidences[rAgentStat.getAgentId()]);
            MGame.addAgentStats(agentStat);
        }
    }

    protected void addFailuresToAgentStats(RunningAgentStat rAgentStat1, RunningAgentStat rAgentStat2){
        rAgentStat1.addNoOfFailures();
        rAgentStat2.addNoOfFailures();
    }

    protected void addSuccessToAgentStats(RunningAgentStat rAgentStat1, RunningAgentStat rAgentStat2){
        rAgentStat1.addNoOfSuccesses();
        rAgentStat2.addNoOfSuccesses();
    }

    protected void addStatToGame(Stat stat, double accuracy, int noOfFails, int noOfSuccesses){
        stat.setAccuracy(accuracy);
        stat.setNoOfFails(noOfFails);
        stat.setNoOfSucesses(noOfSuccesses);
        MGame.setStats(stat);

    }

    @Override
    public void playGames(String testDataset) throws Exception {
        System.gc();
        //StopWatch sWatch = new StopWatch();
        BasicCognitiveAgent bcaSpeaker;
        BasicCognitiveAgent bcaHearer;
        Convergence convergence = new Convergence(NoOfAgents);
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
        //RunningStat rStat = new RunningStat();
        //Stat stat = new Stat();

        //sWatch.startTimer();
        setGameProps(testDataset, GameName);

        for (int i = 0; i < noOfTestInstances; i++) {
            convergence.emptyTable();
            currentContext = testInstances.get(i);
            while (!convergence.isConverged(Agents, currentContext, rAgentStats)) {
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
//                    rStat.incrementNoOfFails();
                    isSuccess = false;
                    convergence.balanceTable(speakerCat, hearerCat);
                    if (UseBeliefUpdates) {
                        BeliefUpdaterFactory.init();
                        BeliefUpdaterFactory.updateBeliefs(speakerCat, hearerCat, false);
                    }
                    Agents.get(bcaHearer.getId()).hear(speakerCat);
                    noOfFails++;
                } else {
                    addSuccessToAgentStats(rAgentStats.get(bcaSpeaker.getId()), rAgentStats.get(bcaHearer.getId()));
//                    rStat.incrementNoOfSuccesses();
                    if (UseBeliefUpdates) {
                        BeliefUpdaterFactory.init();
                        BeliefUpdaterFactory.updateBeliefs(speakerCat, hearerCat, false);
                    }
                    noOfSuccess++;
                }
                //testInstances.remove(currentContext);
                //rStat.setTimestep(sWatch.getDuration());
//                rStat.setNoOfItemsProcessed(i);
                BGEval.addMeasurements(bcaSpeaker.getId(), bcaHearer.getId(), i, isSuccess);
            }
            //MGame.addRunningStat(rStat);
            //addRunningAgentStatsToGame(rAgentStats);
            currentClassVal = convergence.getConvergedCategory();
            bcEval.addPerformanceObservation(currentClassVal, currentContext);
//            rStat.setAccuracy(bcEval.getAccuracyPercent());
            prepareForNewGame();
        }
        //System.out.println(bcEval.getPerformanceMetrics());
        //System.out.println("Total Number of failures:" + noOfFails);
        //System.out.println("Total Number of successes:" + noOfSuccess);
        double avgTime = (double)(noOfFails+noOfSuccess)/(double)noOfTestInstances;
        //System.out.println("Average time: " + avgTime);
        Accuracy = bcEval.getAccuracyPercent();
        //addRunningAgentStatsToAgentStats(rAgentStats);
//        addStatToGame(stat,bcEval.getAccuracyPercent(), rStat.getNoOfFails(), rStat.getNoOfSuccesses());
        //sWatch.stopTimer();
    }

    public BasicGameEvaluator getBasicGameEval() {
        if (BGEval == null) {
            BGEval = new BasicGameEvaluator(0,0);
        }
        return BGEval;
    }

    public double getAccuracy() {
        return Accuracy;
    }

    @Override
    public void setAgentsOnGraph(int x, int y) {}
}

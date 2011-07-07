package edu.metu.lngamesml;

import edu.metu.lngamesml.agents.LearnerTypes;
import edu.metu.lngamesml.agents.beliefs.SigmoidFunctionTypes;
import edu.metu.lngamesml.alt.ClassClusterMajorityVoting;
import edu.metu.lngamesml.alt.MajorityVoting;
import edu.metu.lngamesml.collective.games.langgame.CategorizationGame;
import edu.metu.lngamesml.collective.games.langgame.conflanggame.*;
import edu.metu.lngamesml.stats.nosql.Game;
import edu.metu.lngamesml.stats.nosql.MongoGameStatsFactory;
import edu.metu.lngamesml.stats.nosql.TestSet;
import edu.metu.lngamesml.utils.log.Logging;

import java.net.UnknownHostException;
import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 1/22/11
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */

public class GameEngineX {

    public static void startMajorityVotingSim() {
        String trainingDataset = "/home/caglar/Codes/bash/mnist_convert/mnist_train.arff"; //"/home/caglar/Dataset/Day1.TCP.arff";
        String testDataset = "/home/caglar/Codes/bash/mnist_convert/mnist_test.arff"; //"/home/caglar/Dataset/Day3.TCP.arff";
        int noOfAgents = 10;
        int samplingRatio = 10;
        Logging.info("=============MajorityVoting Simulation have started=================");
        Logging.info("Agents creation is started");
        MajorityVoting mVoting = new MajorityVoting(noOfAgents, samplingRatio);
        mVoting.setLearningType(LearnerTypes.C45);
        mVoting.createAgentsWSampled(trainingDataset);
        Logging.info("Agents are created");
        Logging.info("The simulation has started");
        mVoting.startSimulation(testDataset);
        Logging.info("The simulation has ended");
    }

    public static void startClassClusterMajorityVotingSim() {
        String trainingDataset = "/home/caglar/Dataset/Day1.TCP.arff";
        String testDataset = "/home/caglar/Dataset/Day3.TCP.arff";
        int noOfAgents = 10;
        int noOfClustersPerClassifier = 2;
        Logging.info("=============Class Cluster Majority Voting Simulation have started=================");
        Logging.info("Agents creation is started");
        ClassClusterMajorityVoting mVoting = new ClassClusterMajorityVoting(noOfAgents, noOfClustersPerClassifier);
        mVoting.setLearningType(LearnerTypes.C45);
        mVoting.createAgents(trainingDataset);
        Logging.info("Agents are created");
        Logging.info("The simulation has started");
        mVoting.startSimulation(testDataset);
        Logging.info("The simulation has ended");
    }

    public static void startBasicLanguageGame(boolean useBeliefUpdates) {
        String trainingDataset ="/home/caglar/Dataset/Day1_50.TCP.arff";
        String testDataset = "/home/caglar/Dataset/Day2_25.TCP.arff";
        int noOfAgents = 10;
        int samplingRatio = 10;
        Logging.info("=============CategorizationGame Simulation have started================");
        Logging.info("Agents are being created");
        CategorizationGame bLangGame = new CategorizationGame(noOfAgents, samplingRatio);
        bLangGame.setUseBeliefUpdates(true);
        bLangGame.setUseConfidences(false);
        bLangGame.setLearningType(LearnerTypes.C45);
        bLangGame.createAgents(trainingDataset);
        Logging.info("Agents are created");
        try {
            Logging.info("The game has started");
            MongoGameStatsFactory.initGame();
            TestSet testSet = new TestSet();
            testSet.setTestNo(1);
            testSet.startTestset();
            bLangGame.playGames(testDataset, SigmoidFunctionTypes.NONE);
            Game game = bLangGame.getGame();
            testSet.addGame(game);
            testSet.endTestSet();
            MongoGameStatsFactory.addTestSetToDB(testSet);
            Logging.info("The game has ended");
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
    }

    public static void startConfidenceLangGame(boolean useBeliefUpdates) {
        String trainingDataset = "/home/caglar/Codes/bash/mnist_convert/mnist_train.arff";//"/home/caglar/Dataset/Day1.TCP.arff";
        String testDataset = "/home/caglar/Codes/bash/mnist_convert/mnist_test.arff";//"/home/caglar/Dataset/Day2.TCP.arff";
        int noOfAgents = 10;
        int samplingRatio = 10;
        Logging.info("=============ConfidenceLanguageGame Simulation have started================");
        Logging.info("Agents are being created");
        CategorizationGameCRBU cLangGameCRBU = new CategorizationGameCRBU(noOfAgents, samplingRatio);
        cLangGameCRBU.setUseBeliefUpdates(useBeliefUpdates);
        cLangGameCRBU.setNoOfAgents(noOfAgents);
        cLangGameCRBU.setUseConfidences(true);
        cLangGameCRBU.setLearningType(LearnerTypes.C45);
        cLangGameCRBU.createAgents(trainingDataset);
        Logging.info("Agents are created");
        try {
            Logging.info("The game has started");
            cLangGameCRBU.playGames(testDataset, SigmoidFunctionTypes.NONE);
            Logging.info("The game has ended");
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void startClusterConfidenceGame() {
        String trainingDataset = "/home/caglar/Codes/bash/mnist_convert/mnist_train.arff"; //"/home/caglar/Dataset/Day1.TCP.arff";
        String testDataset = "/home/caglar/Codes/bash/mnist_convert/mnist_test.arff"; //"/home/caglar/Dataset/Day3.TCP.arff";
        int noOfAgents = 10;
        Logging.info("=============ClusterConfidenceLanguageGame Simulation have started================");
        Logging.info("Agents are being created");
        ClusterCategorizationGameCRBU cLangGame = new ClusterCategorizationGameCRBU(2);
        cLangGame.setNoOfAgents(noOfAgents);
        cLangGame.setUseConfidences(true);
        cLangGame.setLearningType(LearnerTypes.C45);
        cLangGame.createAgents(trainingDataset);
        Logging.info("Agents are created");
        try {
            Logging.info("The game has started");
            cLangGame.playGames(testDataset, SigmoidFunctionTypes.NONE);
            Logging.info("The game has ended");
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void startClassClusterConfidenceGame() {
        String trainingDataset = "/home/caglar/Dataset/Day1.TCP.arff";
        String testDataset = "/home/caglar/Dataset/Day3.TCP.arff";
        int noOfAgents = 10;
        Logging.info("=============Class Cluster Confidence Language Game Simulation have started================");
        Logging.info("Agents are being created");
        ClassClusterCategorizationGameCRBU cLangGame = new ClassClusterCategorizationGameCRBU(2);
        cLangGame.setNoOfAgents(noOfAgents);
        cLangGame.setUseConfidences(true);
        cLangGame.setLearningType(LearnerTypes.C45);
        cLangGame.createAgents(trainingDataset);
        Logging.info("Agents are created");
        try {
            Logging.info("The game has started");
            cLangGame.playGames(testDataset, SigmoidFunctionTypes.NONE);
            Logging.info("The game have ended");
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void startClassClusterGame() {
        String trainingDataset = "/home/caglar/Dataset/Day1.TCP.arff";
        String testDataset = "/home/caglar/Dataset/Day3.TCP.arff";
        int noOfAgents = 10;
        Logging.info("=============Class Cluster Confidence Language Game Simulation have started================");
        Logging.info("Agents are being created");
        ClassClusterLGame cLangGame = new ClassClusterLGame(2);
        cLangGame.setNoOfAgents(noOfAgents);
        cLangGame.setLearningType(LearnerTypes.C45);
        cLangGame.createAgents(trainingDataset);
        Logging.info("Agents are created");
        try {
            Logging.info("The game has started");
            cLangGame.playGames(testDataset, SigmoidFunctionTypes.NONE);
            Logging.info("The game has ended");
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void startAdaBoostConfGame() {
        String trainingDataset = "/home/caglar/Codes/bash/mnist_convert/mnist_train.arff";
        String testDataset = "/home/caglar/Codes/bash/mnist_convert/mnist_test.arff";//"/home/caglar/Programs/weka-3-7-1/data/segment-test.arff";
        int noOfAgents = 10;
        Logging.info("=============Class Cluster Confidence Language Game Simulation have started================");
        Logging.info("Agents are being created");
        AdaBoostConfidenceLGame abcLangGame = new AdaBoostConfidenceLGame();
        abcLangGame.setNoOfAgents(noOfAgents);
        abcLangGame.setLTypes(LearnerTypes.C45);
        abcLangGame.createAgents(trainingDataset);
        Logging.info("Agents are created");
        try {
            Logging.info("The game has started");
            abcLangGame.playGames(testDataset, SigmoidFunctionTypes.NONE);
            Logging.info("The game has ended");
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        //MongoGameStatsFactory.initGame();
        //TestSet tests = new TestSet();
        //tests.setTestNo(1);
        //tests.startTestset();
        startBasicLanguageGame(true);
        //tests.endTestSet();
        //MongoGameStatsFactory.addTestSetToDB(tests);
    }
}

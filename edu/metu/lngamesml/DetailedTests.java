package edu.metu.lngamesml;

import edu.metu.lngamesml.agents.LearnerTypes;
import edu.metu.lngamesml.agents.beliefs.SigmoidFunctionTypes;
import edu.metu.lngamesml.alt.BaggingClassifiers;
import edu.metu.lngamesml.alt.MajorityVoting;
import edu.metu.lngamesml.alt.SingleClassifier;
import edu.metu.lngamesml.alt.WeightedMajorityVoting;
import edu.metu.lngamesml.collective.IteratedLearningModel;
import edu.metu.lngamesml.collective.games.langgame.CategorizationGame;
import edu.metu.lngamesml.collective.games.langgame.conflanggame.CategorizationGameCRBU;
import edu.metu.lngamesml.exception.UninitializedClassifierException;
import edu.metu.lngamesml.utils.log.Logging;

import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 6/26/11
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */

public class DetailedTests {

    public static void startMajorityVotingSim(String trainingDataset, String testDataset, LearnerTypes lType, int noOfAgents, int samplingRatio) {
        System.out.println("=============MajorityVoting Simulation have started=================");
        System.out.println("Agents creation is started");
        System.out.println("Game Props are: AgNo: " + noOfAgents + " test dataset" + testDataset + " sampling " + samplingRatio + "training " + trainingDataset);
        MajorityVoting mVoting = new MajorityVoting(noOfAgents, samplingRatio);
        //mVoting.setLearningType(LearnerTypes.NBAYES);
        mVoting.setLearningType(lType);
        mVoting.createAgentsWSampled(trainingDataset);
        System.out.println("Agents are created");
        System.out.println("The simulation has started");
        mVoting.startSimulation(testDataset);
        System.out.println("Accuracy on test data set: " + mVoting.getAccuracy());
        mVoting.startSimulation(trainingDataset);
        System.out.println("Accuracy on training data set: " + mVoting.getAccuracy());
        System.out.println("The simulation has ended");
    }

    public static void startWeightedMajorityVotingSim(String trainingDataset, String testDataset, LearnerTypes lType, int noOfAgents, int samplingRatio) {
        System.out.println("=============Weighted Majority Voting Simulation have started=================");
        System.out.println("Agents creation is started");
        System.out.println("Game Props are: AgNo: " + noOfAgents + " test dataset" + testDataset + " sampling " + samplingRatio + "training " + trainingDataset);
        MajorityVoting mVoting = new WeightedMajorityVoting(noOfAgents, samplingRatio);
        //mVoting.setLearningType(LearnerTypes.NBAYES);
        mVoting.setLearningType(lType);
        mVoting.createAgentsWSampled(trainingDataset);
        System.out.println("Agents are created");
        System.out.println("The simulation has started");
        mVoting.startSimulation(testDataset);
        System.out.println("Accuracy on test data set is: " + mVoting.getAccuracy());
        mVoting.startSimulation(trainingDataset);
        System.out.println("Accuracy on training data set is: " + mVoting.getAccuracy());
        System.out.println("The simulation has ended");
    }

    public static void startBasicLanguageGame(String trainingDataset, String testDataset, LearnerTypes lType, int noOfAgents, int samplingRatio, boolean useBeliefUpdates) {

        System.out.println("=============CategorizationGame Simulation have started================");
        System.out.println("Agents are being created");
        System.out.println("Game Props are: AgNo: " + noOfAgents + " test dataset" + testDataset + " sampling " + samplingRatio + "training " + trainingDataset);
        CategorizationGame bLangGame = new CategorizationGame(noOfAgents, samplingRatio);
        bLangGame.setUseBeliefUpdates(true);
        bLangGame.setLearningType(lType);
        //bLangGame.setLearningType(LearnerTypes.C45);
        bLangGame.createAgents(trainingDataset);
        System.out.println("Agents are created");
        try {
            System.out.println("The game has started");
            bLangGame.playGames(testDataset, SigmoidFunctionTypes.TANH);
            System.out.println("The game has ended");
            System.out.println("Accuracy on test data set is: " + bLangGame.getAccuracy());
            bLangGame.playGames(trainingDataset, SigmoidFunctionTypes.TANH);
            System.out.println("Accuracy on training data set is: " + bLangGame.getAccuracy());
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
    }

    public static void startConfidenceLangGame(String trainingDataset, String testDataset, LearnerTypes lType, int noOfAgents, int samplingRatio, boolean useBeliefUpdates) {

        System.out.println("=============ConfidenceLanguageGame Simulation have started================");
        System.out.println("Agents are being created");
        System.out.println("Game Props are: AgNo: " + noOfAgents + " test dataset" + testDataset + " sampling " + samplingRatio + "training " + trainingDataset);
        CategorizationGameCRBU cLangGameCRBU = new CategorizationGameCRBU(noOfAgents, samplingRatio);
        cLangGameCRBU.setUseBeliefUpdates(useBeliefUpdates);
        cLangGameCRBU.setNoOfAgents(noOfAgents);
        cLangGameCRBU.setUseConfidences(true);
        cLangGameCRBU.setLearningType(lType);
        //cLangGameCRBU.setLearningType(LearnerTypes.C45);
        cLangGameCRBU.createAgents(trainingDataset);
        System.out.println("Agents are created");
        try {
            System.out.println("The game has started");
            cLangGameCRBU.playGames(testDataset, SigmoidFunctionTypes.TANH);
            System.out.println("The game has ended");
            System.out.println("Accuracy on test data set is: " + cLangGameCRBU.getAccuracy());
            cLangGameCRBU.playGames(trainingDataset, SigmoidFunctionTypes.TANH);
            System.out.println("Accuracy on training data set is: " + cLangGameCRBU.getAccuracy());
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void startSocraticConfidenceLangGame(String trainingDataset, String testDataset, LearnerTypes lType, int noOfAgents, int samplingRatio, boolean useBeliefUpdates) {
        System.out.println("=============Socratic Confidence Language Game Simulation have started================");
        System.out.println("Agents are being created");
        System.out.println("Game Props are: AgNo: " + noOfAgents + " test dataset" + testDataset + " sampling " + samplingRatio + "training " + trainingDataset);
        CategorizationGameCRBU cLangGameCRBU = new CategorizationGameCRBU(noOfAgents, samplingRatio);
        cLangGameCRBU.setUseBeliefUpdates(useBeliefUpdates);
        cLangGameCRBU.setNoOfAgents(noOfAgents);
        cLangGameCRBU.reverseAgentUpdates(true);
        cLangGameCRBU.setUseConfidences(true);
        cLangGameCRBU.setLearningType(lType);
        //cLangGameCRBU.setLearningType(LearnerTypes.C45);
        cLangGameCRBU.createAgents(trainingDataset);
        System.out.println("Agents are created");
        try {
            System.out.println("The game has started");
            cLangGameCRBU.playGames(testDataset, SigmoidFunctionTypes.TANH);
            System.out.println("The game has ended");
            System.out.println("Accuracy on test data set is: " + cLangGameCRBU.getAccuracy());
            cLangGameCRBU.playGames(trainingDataset, SigmoidFunctionTypes.TANH);
            System.out.println("Accuracy on training data set is: " + cLangGameCRBU.getAccuracy());
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void startBagging(String trainingDataset, String testDataset, LearnerTypes lType, int noOfAgents, int samplingRatio) {

        System.out.println("=============BaggingClassifiers Simulation have started================");
        System.out.println("Agents are being created");
        System.out.println("Game Props are: AgNo: " + noOfAgents + " test dataset" + testDataset + " sampling " + samplingRatio + "training " + trainingDataset);

        System.out.println("Bagging training classifiers have started.");
        BaggingClassifiers baggingClassifiers = new BaggingClassifiers();
        baggingClassifiers.trainAlgorithm(trainingDataset, lType, noOfAgents, samplingRatio);
        System.out.println("Training classifiers have finished.");
        System.out.println("Testing classifiers have started.");
        try {
            baggingClassifiers.testAlgorithm(testDataset);
            System.out.println("Accuracy on test data set is: " + baggingClassifiers.getAccuracy());
            baggingClassifiers.testAlgorithm(trainingDataset);
            System.out.println("Accuracy on training data set is: " + baggingClassifiers.getAccuracy());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("Testing have ended.");

    }

    public static void startSingleClassifierTest(String trainingDataset, String testDataset, LearnerTypes lType, int samplingRatio) {
        System.out.println("=============Single Classifier Simulation have started================");
        System.out.println("Game Props are test dataset, " + testDataset + " sampling " + samplingRatio + "training " + trainingDataset);
        SingleClassifier singleClassifier = new SingleClassifier();
        singleClassifier.trainClassifier(trainingDataset, lType);
        System.out.println("Classifier is created.");
        try {
            singleClassifier.testClassifier(testDataset);
            System.out.println("Accuracy on test data set: " + singleClassifier.getAccuracy());
            singleClassifier.testClassifier(trainingDataset);
            System.out.println("Accuracy on training data set: " + singleClassifier.getAccuracy());
        } catch (UninitializedClassifierException e) {
            e.printStackTrace();
        }
    }

    public static void startMajorityVotingSimNTimes(String trainingDataset, String testDataset, LearnerTypes lType, int noOfAgents, int samplingRatio) {
        int noOfIterations = 10;
        System.out.println("=============MajorityVoting Simulation have started=================");
        System.out.println("Agents creation is started");
        System.out.println("Game Props are: AgNo: " + noOfAgents + " test dataset" + testDataset + " sampling " + samplingRatio + "training " + trainingDataset);
        MajorityVoting mVoting = new MajorityVoting(noOfAgents, samplingRatio);
        //mVoting.setLearningType(LearnerTypes.NBAYES);
        mVoting.setLearningType(lType);
        mVoting.createAgentsWSampled(trainingDataset);
        System.out.println("Agents are created");
        System.out.println("The simulation has started");
        double accuracy = 0.0;
        for (int i = 0; i < noOfIterations; i++) {
            mVoting.startSimulation(testDataset);
            accuracy += mVoting.getAccuracy();
        }
        accuracy /= noOfIterations;
        System.out.println("Accuracy percentage is :" + accuracy);
        System.out.println("The simulation has ended");
    }

    public static void startBasicLanguageGameNTimes(String trainingDataset, String testDataset, LearnerTypes lType, int noOfAgents, int samplingRatio, boolean useBeliefUpdates) {
        int noOfIterations = 10;
        System.out.println("=============CategorizationGame Simulation have started================");
        System.out.println("Agents are being created");
        System.out.println("Game Props are: AgNo: " + noOfAgents + " test dataset" + testDataset + " sampling " + samplingRatio + "training " + trainingDataset);
        CategorizationGame bLangGame = new CategorizationGame(noOfAgents, samplingRatio);
        bLangGame.setUseBeliefUpdates(true);
        //bLangGame.setLearningType(LearnerTypes.NBAYES);
        bLangGame.setLearningType(lType);
        bLangGame.createAgents(trainingDataset);
        System.out.println("Agents are created");
        double accuracy = 0.0;
        try {
            //System.out.println("The game has started");
            for (int i = 0; i < noOfIterations; i++) {
                bLangGame.playGames(testDataset, SigmoidFunctionTypes.NONE);
                accuracy += bLangGame.getAccuracy();
            }
            //System.out.println("The game has ended");
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
        accuracy /= noOfIterations;
        System.out.println("Averaged Accuracy is: " + accuracy);
    }

    public static void startConfidenceLangGameNTimes(String trainingDataset, String testDataset, LearnerTypes lType, int noOfAgents, int samplingRatio, boolean useBeliefUpdates) {
        int noOfIterations = 2; //50
        System.out.println("=============ConfidenceLanguageGame Simulation have started================");
        System.out.println("Agents are being created");
        System.out.println("Game Props are: AgNo: " + noOfAgents + " test dataset" + testDataset + " sampling " + samplingRatio + "training " + trainingDataset);
        CategorizationGameCRBU cLangGameCRBU = new CategorizationGameCRBU(noOfAgents, samplingRatio);
        cLangGameCRBU.setUseBeliefUpdates(useBeliefUpdates);
        cLangGameCRBU.setNoOfAgents(noOfAgents);
        cLangGameCRBU.setUseConfidences(true);
        //cLangGameCRBU.setLearningType(LearnerTypes.NBAYES);
        cLangGameCRBU.setLearningType(lType);
        cLangGameCRBU.createAgents(trainingDataset);
        System.out.println("Agents are created");
        double accuracy = 0.0;
        try {
            //System.out.println("The game has started");
            for (int i = 0; i < noOfIterations; i++) {
                cLangGameCRBU.playGames(testDataset, SigmoidFunctionTypes.NONE);
                accuracy += cLangGameCRBU.getAccuracy();
                System.out.println("Accuracy is: " + cLangGameCRBU.getAccuracy());
            }
            //System.out.println("The game has ended");
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        accuracy /= noOfIterations;
        System.out.println("Averaged Accuracy is: " + accuracy);
    }


    public static void mnistTests(LearnerTypes lType) {
        String trainingDataset = "/home/caglar/Datasets/mnist/mnist_train_40_sampled.arff";
        String testDataset = "/home/caglar/Datasets/mnist/mnist_test_40_sampled.arff";
        int noOfAgents = 10;
        int samplingRatio = 20;

        startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startWeightedMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startBagging(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startSingleClassifierTest(trainingDataset, testDataset, lType, samplingRatio);
        //startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
    }

    public static void abstractRussiaTests()
    {
        String trainingDataset = "/home/caglar/Datasets/letter/letter_train.arff";
        String testDataset = "/home/caglar/Datasets/letter/letter_test2.arff";
        int noOfAgents = 10;
        int samplingRatio = 20;
        System.out.println("C4.5 Learning algorithm tests.");
        startBasicLanguageGame(trainingDataset, testDataset, LearnerTypes.C45, noOfAgents, samplingRatio, true);
        startConfidenceLangGame(trainingDataset, testDataset, LearnerTypes.C45, noOfAgents, samplingRatio, true);
        startSingleClassifierTest(trainingDataset, testDataset, LearnerTypes.C45, samplingRatio);
        startSingleClassifierTest(trainingDataset, testDataset, LearnerTypes.C45, 2);

        System.out.println("NB Learning algorithm tests.");
        startBasicLanguageGame(trainingDataset, testDataset, LearnerTypes.NBAYES, noOfAgents, samplingRatio, true);
        startConfidenceLangGame(trainingDataset, testDataset, LearnerTypes.NBAYES, noOfAgents, samplingRatio, true);
        startSingleClassifierTest(trainingDataset, testDataset, LearnerTypes.NBAYES, samplingRatio);
        startSingleClassifierTest(trainingDataset, testDataset, LearnerTypes.NBAYES, 2);

        System.out.println("SVM Learning algorithm tests.");
        startBasicLanguageGame(trainingDataset, testDataset, LearnerTypes.SVM, noOfAgents, samplingRatio, true);
        startConfidenceLangGame(trainingDataset, testDataset, LearnerTypes.SVM, noOfAgents, samplingRatio, true);
        startSingleClassifierTest(trainingDataset, testDataset, LearnerTypes.SVM, samplingRatio);
        startSingleClassifierTest(trainingDataset, testDataset, LearnerTypes.SVM, 2);


    }
    public static void gtvsTests(LearnerTypes lType) {
        String trainingDataset = "/home/caglar/Datasets/gtvs/Day1_50.TCP.arff";
        String testDataset = "/home/caglar/Datasets/gtvs/Day2_50.TCP.arff";
        int noOfAgents = 10;
        int samplingRatio = 20;

        startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startWeightedMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startBagging(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startSingleClassifierTest(trainingDataset, testDataset, lType, samplingRatio);
        //startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
    }

    public static void segmentationTests(LearnerTypes lType) {
        String trainingDataset = "/home/caglar/Datasets/segment/segment-challenge.arff";
        String testDataset = "/home/caglar/Datasets/segment/segment-test.arff";
        int noOfAgents = 10;
        int samplingRatio = 40;

        //startMajorityVotingSimNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);

        startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startWeightedMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startBagging(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startSingleClassifierTest(trainingDataset, testDataset, lType, samplingRatio);
        //startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
    }


    public static void letterTests(LearnerTypes lType) {
        String trainingDataset = "/home/caglar/Datasets/letter/letter_train.arff";
        String testDataset = "/home/caglar/Datasets/letter/letter_test2.arff";
        int noOfAgents = 10;
        int samplingRatio = 20;
        //startMajorityVotingSimNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);

        startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startWeightedMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startBagging(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        //startSingleClassifierTest(trainingDataset, testDataset, lType, samplingRatio);
        //startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
    }

    public static void segmentationIncSamplingTests(LearnerTypes lType) {
        String trainingDataset = "/home/caglar/Datasets/segment/segment-challenge.arff";
        String testDataset = "/home/caglar/Datasets/segment/segment-test.arff";
        int noOfAgents = 10;
        int samplingRatio = 5;

        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);

        //10 percent:
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, 10);
        startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, 10, true);
        startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, 10, true);

        //15 percent
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, 15);
        startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, 15, true);
        startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, 15, true);

        //20 percent
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, 20);
        startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, 20, true);
        startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, 20, true);

        //25 percent
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, 25);
        startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, 25, true);
        startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, 25, true);
    }

    public static void segmentationIncAgentTests(LearnerTypes lType) {
        String trainingDataset = "/home/caglar/Datasets/segment/segment-challenge.arff";
        String testDataset = "/home/caglar/Datasets/segment/segment-test.arff";
        int noOfAgents = 5;
        int samplingRatio = 10;
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);

        noOfAgents += 5;
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);

        noOfAgents += 5;
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        noOfAgents += 5;
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        noOfAgents += 5;
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        startBasicLanguageGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
    }

    public static void segmentationIncAgentTests10Times(LearnerTypes lType) {
        String trainingDataset = "/home/caglar/Datasets/segment/segment-challenge.arff";
        String testDataset = "/home/caglar/Datasets/segment/segment-test.arff";
        int noOfAgents = 5;
        int samplingRatio = 10;
        startMajorityVotingSimNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        noOfAgents += 5;
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        noOfAgents += 5;
        startMajorityVotingSimNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        noOfAgents += 5;
        startMajorityVotingSimNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        noOfAgents += 5;
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
    }

    public static void segmentationIncSamplingTests10Times(LearnerTypes lType) {
        String trainingDataset = "/home/caglar/Datasets/segment/segment-challenge.arff";
        String testDataset = "/home/caglar/Datasets/segment/segment-test.arff";
        int noOfAgents = 10;
        int samplingRatio = 5;
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, samplingRatio);
        startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //10 percent:
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, 10);
        startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 10, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 10, true);
        //15 percent
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, 15);
        startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 15, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 15, true);
        //20 percent
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, 20);
        startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 20, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 20, true);
        //25 percent
        startMajorityVotingSim(trainingDataset, testDataset, lType, noOfAgents, 25);
        startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 25, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 25, true);
    }

    public static void letterIncAgentTests10Times(LearnerTypes lType) {
        String trainingDataset = "/home/caglar/Datasets/letter/letter_train.arff";
        String testDataset = "/home/caglar/Datasets/letter/letter_test2.arff";
        int noOfAgents = 5;
        int samplingRatio = 20;

        //startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);

        noOfAgents += 5;
        //startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);

        noOfAgents += 5;
        //startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);

        noOfAgents += 5;
        //startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);

        noOfAgents += 5;
        //startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
//        startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
    }

    public static void letterIncSamplingTests10Times(LearnerTypes lType) {
        String trainingDataset = "/home/caglar/Datasets/letter/letter_train.arff";
        String testDataset = "/home/caglar/Datasets/letter/letter_test2.arff";

        int noOfAgents = 10;
        int samplingRatio = 5;


        //startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //10 percent:

        //startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 10, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 10, true);
        //startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);

        //15 percent
        //startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 15, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 15, true);
        //startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);

        //20 percent
        //startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 20, true);
        startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
        //startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 20, true);

        //25 percent
        //startBasicLanguageGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 25, true);
        startConfidenceLangGameNTimes(trainingDataset, testDataset, lType, noOfAgents, 25, true);
        //startSocraticConfidenceLangGame(trainingDataset, testDataset, lType, noOfAgents, samplingRatio, true);
    }

    public static void combinedDataSetTests(LearnerTypes learnerTypes) {
        if (learnerTypes == LearnerTypes.NBAYES) {
            System.out.println("Naive Bayes tests");
        } else if (learnerTypes == LearnerTypes.C45) {
            System.out.println("C45 tests");
        }
        mnistTests(learnerTypes);
        gtvsTests(learnerTypes);
        segmentationTests(learnerTypes);
        letterTests(learnerTypes);
    }

    public static void startILM(String trainingDataset, String testDataset, LearnerTypes lType, int noOfAgents, int samplingRatio) {
        System.out.println("=============Iterated Learning Model Simulation have started=================");
        System.out.println("Agents creation is started");
        System.out.println("Game Props are: AgNo: " + noOfAgents + " test dataset" + testDataset + " sampling " + samplingRatio + "training " + trainingDataset);
        IteratedLearningModel iteratedLearningModel = new IteratedLearningModel(noOfAgents, samplingRatio, lType);
        System.out.println("The simulation has started");
        iteratedLearningModel.startModel(trainingDataset, testDataset);
        System.out.println("Accuracy on test data set: " + iteratedLearningModel.getAccuracy());
        iteratedLearningModel = new IteratedLearningModel(noOfAgents, samplingRatio, lType);
        iteratedLearningModel.startModel(trainingDataset, trainingDataset);
        System.out.println("Accuracy on training data set: " + iteratedLearningModel.getAccuracy());
        System.out.println("The simulation has ended");
    }

    public static void testILM() {
        String segtrainingDataset = "/home/caglar/Datasets/segment/segment-challenge.arff";
        String segtestDataset = "/home/caglar/Datasets/segment/segment-test.arff";
        String trainingDataset = "/home/caglar/Datasets/letter/letter_train.arff";
        String testDataset = "/home/caglar/Datasets/letter/letter_test2.arff";
        String gtvstrainingDataset = "/home/caglar/Datasets/gtvs/Day1_50.TCP.arff";
        String gtvstestDataset = "/home/caglar/Datasets/gtvs/Day2_50.TCP.arff";
        String mnisttrainingDataset = "/home/caglar/Datasets/mnist/mnist_train_40_sampled.arff";
        String mnisttestDataset = "/home/caglar/Datasets/mnist/mnist_test_40_sampled.arff";
        int noOfAgents = 10;
        int samplingRatio = 40;

        startILM(trainingDataset, testDataset, LearnerTypes.C45, noOfAgents, samplingRatio);
        //startILM(segtrainingDataset, segtestDataset, LearnerTypes.NBAYES, noOfAgents, samplingRatio);
        //startILM(gtvstrainingDataset, gtvstestDataset, LearnerTypes.NBAYES, noOfAgents, 20);
        //startILM(mnisttrainingDataset, mnisttestDataset, LearnerTypes.NBAYES, noOfAgents, 20);
    }

    public static void main(String args[]) {
        //testILM();
        //letterTests(LearnerTypes.C45);
        //segmentationTests(LearnerTypes.C45);

        //combinedDataSetTests(LearnerTypes.C45);
        //combinedDataSetTests(LearnerTypes.SVM);
        //combinedDataSetTests(LearnerTypes.RBFNET);
        //combinedDataSetTests(LearnerTypes.NBAYES);
        //segmentationIncSamplingTests(LearnerTypes.C45);
        //segmentationIncAgentTests(LearnerTypes.C45);
        //segmentationIncAgentTests10Times(LearnerTypes.C45);
        //segmentationIncSamplingTests10Times(LearnerTypes.C45);
        //letterIncAgentTests10Times(LearnerTypes.NBAYES);
        //letterIncSamplingTests10Times(LearnerTypes.C45);
        //String trainingDataset = "/home/caglar/Datasets/letter/letter_train.arff";
        //String testDataset = "/home/caglar/Datasets/letter/letter_test2.arff";
        //startConfidenceLangGameNTimes(trainingDataset, testDataset, LearnerTypes.SVM, 10, 20, true);
        abstractRussiaTests();
    }
}

package edu.metu.lngamesml.tests;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 12, 2010
 * Time: 6:36:30 AM
 * To change this template use File | Settings | File Templates.
 */

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.GraphLink;
import edu.metu.lngamesml.agents.SupervisedCognitiveAgent;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.graphs.jung.AnimatedGraphView;
import edu.metu.lngamesml.graphs.jung.SimpleGraphView;
import edu.metu.lngamesml.utils.random.MersenneTwister;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AdaBoostM1;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralTests {

    public static void testJavaLogger() {
        Logger.getAnonymousLogger().info("This is just an Information");
        Logger.getLogger("deneme").log(Level.WARNING, "This is just a warning");
        Logger.getAnonymousLogger().entering("Entering here", "And here");
    }

    public static void testMersenneTwister() {
        long seed = 0;
        Random rnd = new Random();
        MersenneTwister mTwister = new MersenneTwister(rnd.nextInt());
        //double randDouble = mTwister.nextFloat();
        for (int i = 0; i < 10000; i++) {
            mTwister.setSeed(System.nanoTime());
            System.out.println(System.nanoTime());
            System.out.println(mTwister.nextDouble());
        }
    }

    public static void testSimpleGraphView() {
        SimpleGraphView sgv = new SimpleGraphView();
        Agent agent1 = new SupervisedCognitiveAgent();
        Agent agent2 = new SupervisedCognitiveAgent();
        Agent agent3 = new SupervisedCognitiveAgent();
        GraphLink gLink1 = new GraphLink(1, 0, 0);
        GraphLink gLink2 = new GraphLink(2, 0, 0);
        GraphLink gLink3 = new GraphLink(3, 0, 0);
        agent1.setId(1);
        agent2.setId(2);
        agent3.setId(3);

        sgv.addVertex(agent1);
        sgv.addVertex(agent2);
        sgv.addVertex(agent3);
        sgv.addEdge(gLink1, agent1, agent2);
        sgv.addEdge(gLink2, agent2, agent3);
        sgv.addEdge(gLink3, agent3, agent1);
        sgv.prepareGraphView();
        BasicVisualizationServer<Agent, String> vv = sgv.getVisServer();
        JFrame frame = new JFrame("Simple Graph View 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);

    }

/*
    public static void testSimpleGraphView2 () {
        SimpleGraphView2 sgv = new SimpleGraphView2();
        sgv.addVertex((Integer)1);
        sgv.addVertex((Integer)2);
        sgv.addVertex((Integer)3);
        sgv.addEdge("Edge-1", (Integer)1, (Integer)2);
        sgv.addEdge("Edge-2", (Integer)2, (Integer)3);
        sgv.addEdge("Edge-3", (Integer)3, (Integer)1);
        sgv.prepareGraphView();
        BasicVisualizationServer<Agent, String> vv = sgv.getVisServer();
        JFrame frame = new JFrame("Simple Graph View 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
  */

    public static void testAnimatedGraphView() {
        AnimatedGraphView animatedGraphView = new AnimatedGraphView();
        animatedGraphView.prepareGraphView();
        Agent agent1 = new SupervisedCognitiveAgent();
        Agent agent2 = new SupervisedCognitiveAgent();
        Agent agent3 = new SupervisedCognitiveAgent();
        Agent agent4 = new SupervisedCognitiveAgent();
        Agent agent5 = new SupervisedCognitiveAgent();
        GraphLink gLink1 = new GraphLink(1, 0, 0);
        GraphLink gLink2 = new GraphLink(2, 0, 0);
        GraphLink gLink3 = new GraphLink(3, 0, 0);
        GraphLink gLink4 = new GraphLink(4, 0, 0);
        GraphLink gLink5 = new GraphLink(5, 0, 0);
        agent1.setId(1);
        agent2.setId(2);
        agent3.setId(3);
        agent4.setId(4);
        agent5.setId(5);
        animatedGraphView.addVertex(agent1);
        animatedGraphView.addVertex(agent2);
        animatedGraphView.addEdge(gLink1, agent1, agent2);
        animatedGraphView.addVertex(agent3);
        animatedGraphView.addEdge(gLink2, agent2, agent3);
        animatedGraphView.addEdge(gLink3, agent3, agent1);
        BasicVisualizationServer<Agent, GraphLink> vv = animatedGraphView.getVisServer();

        animatedGraphView.addVertex(agent4);
        animatedGraphView.addEdge(gLink4, agent3, agent4);
        animatedGraphView.addVertex(agent5);
        animatedGraphView.addEdge(gLink5, agent4, agent5);

        JFrame frame = new JFrame("Animated Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }

    /*
    public static void testBootstrap() {
        Bootstrap part = new Bootstrap("/home/caglar/Dataset/Day1.TCP.arff", 10);
        Instances parts[] = part.partitionDataset("/home/caglar/Dataset/Day1.TCP.arff", 10);
        System.out.println(parts.length);
        System.out.println(parts[0].size());
        System.out.println(parts[1].size());
        System.out.println(parts[2].size());
        System.out.println(parts[3].size());
        System.out.println(parts[4].size());
        System.out.println(parts[5].size());
        System.out.println(parts[6].size());
        System.out.println(parts[7].size());
    }

    public static void testSampling() {
        Sampling part = new Sampling("/home/caglar/Dataset/Day1.TCP.arff", 10);
        InstancesList parts[] = part.partitionDataset();
        System.out.println(parts.length);
        System.out.println(parts[0].size());
        System.out.println(parts[1].size());
        System.out.println(parts[2].size());
        System.out.println(parts[3].size());
        System.out.println(parts[4].size());
        System.out.println(parts[5].size());
        System.out.println(parts[6].size());
        System.out.println(parts[7].size());
        System.out.println(parts[8].size());
        System.out.println(parts[9].size());
    }
    */
    public static void testWeights() {
        String dataset = "/home/caglar/Dataset/Day1.TCP.arff";
        Instances data = FileLoaderFactory.loadFile(dataset);
        for (Instance inst : data) {
            if (inst.weight() > 1 || inst.weight() < 0) {
                System.out.println("Fuck");
            }
            System.out.println(inst.weight());
        }
    }

    public static void testInstances() {
        String dataset = "/home/caglar/Dataset/Day1.TCP.arff";
        Instances data = FileLoaderFactory.loadFile(dataset);
        ArrayList<Attribute> arrList = new ArrayList<Attribute>();
        for (int i = 0; i < data.numAttributes(); i++) {
            arrList.add(data.attribute(i));
        }
        Instances insts = new Instances("test", arrList, data.numInstances() / 3);
        /*try {
            inst = new Instances(new FileReader(dataset));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } */
        insts.add(data.instance(0));
        System.out.println(insts);
    }

    /*
    public static void testBayesNet() {
        BayesNet bayNet = new BayesNet();
        String dataset = "/home/caglar/Dataset/Day1.TCP.arff";
        //Instances data = FileLoaderFactory.loadFile(dataset);
        Sampling sampler = new Sampling(dataset, 10);
        InstancesList[] instList = sampler.partitionDataset();
        try {
            bayNet.buildClassifier(instList[0].getInstances(sampler.getRelation()));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        double bayScore = bayNet.measureBayesScore();
        System.out.println(bayScore);
    }
    */
    public void trash() {
        AdaBoostM1 adboost = new AdaBoostM1();
    }
    
    public static void main(String[] args) {
        //testAnimatedGraphView();
        //testBootstrap();
        //testSampling();
        //testWeights();
        //testInstances();
        //testBayesNet();
    }
}

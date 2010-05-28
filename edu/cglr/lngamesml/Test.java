package edu.cglr.lngamesml;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 12, 2010
 * Time: 6:36:30 AM
 * To change this template use File | Settings | File Templates.
 */

import edu.cglr.lngamesml.agents.Agent;
import edu.cglr.lngamesml.agents.GraphLink;
import edu.cglr.lngamesml.agents.SimpleAgent;
import edu.cglr.lngamesml.utils.random.MersenneTwister;
import edu.cglr.lngamesml.graphs.jung.AnimatedGraphView;
import edu.cglr.lngamesml.graphs.jung.SimpleGraphView;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

import javax.swing.*;
import java.util.Random;
import java.util.logging.*;

import static java.lang.Thread.sleep;

public class Test {

    public static void testJavaLogger ()
    {
        Logger.getAnonymousLogger().info("This is just an Information");
        Logger.getLogger("deneme").log(Level.WARNING, "This is just a warning");
        Logger.getAnonymousLogger().entering("Entering here", "And here");
    }

    public static void testMersenneTwister ()
    {
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

    public static void testSimpleGraphView () {
        SimpleGraphView sgv = new SimpleGraphView();
        Agent agent1 = new SimpleAgent();
        Agent agent2 = new SimpleAgent();
        Agent agent3 = new SimpleAgent();
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
        BasicVisualizationServer<Agent, String> vv =sgv.getVisServer();
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
    public static void testAnimatedGraphView () {
        AnimatedGraphView animatedGraphView = new AnimatedGraphView();
        animatedGraphView.prepareGraphView();
        Agent agent1 = new SimpleAgent();
        Agent agent2 = new SimpleAgent();
        Agent agent3 = new SimpleAgent();
        Agent agent4 = new SimpleAgent();
        Agent agent5 = new SimpleAgent();
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

    public static void main (String [] args)
    {
        testAnimatedGraphView();
    }
}

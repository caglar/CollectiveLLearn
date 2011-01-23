/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.metu.lngamesml.stats;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import edu.metu.lngamesml.stats.game.AgentStat;
import edu.metu.lngamesml.stats.game.RunningAgentStat;
import edu.metu.lngamesml.stats.game.RunningStat;
import edu.metu.lngamesml.stats.game.Stat;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caglar
 */
public class Main {

    public static void test1() throws MongoException, UnknownHostException{
        Morphia morphia = new Morphia();
        morphia.map(TestSet.class).map(Game.class);
        morphia.map(TestSet.class).map(Game.class).map(AgentStat.class);
        morphia.map(TestSet.class).map(Game.class).map(RunningAgentStat.class);
        morphia.map(TestSet.class).map(Game.class).map(RunningStat.class);
        morphia.map(TestSet.class).map(Game.class).map(Stat.class);

        Datastore ds = morphia.createDatastore(new Mongo(), "my_database");
        TestSet testSet = new TestSet();
        testSet.setTestNo(1);
        testSet.startTestset();

        Game g1 = new Game();
        g1.setGameName("Deneme");
        g1.setIsBeliefUpdates(true);
        g1.setNoOfAgents(100);
        g1.setSamplingRatio(100);
        g1.setTestDataset("Heyt Be!");

        AgentStat agStats = new AgentStat();
        agStats.setAgentID(1);
        agStats.setNoOfFails(10);
        agStats.setNoOfSuccess(100);
        agStats.setValidationAccuracy(100);
        g1.addAgentStats(agStats);
        List <AgentStat> agStat = new ArrayList<AgentStat>();
        agStat.add(agStats);
        testSet.addGame(g1);
        testSet.endTestSet();
        ds.save(testSet);
    }

    public static void test2() throws UnknownHostException {
        MongoGameStatsFactory.initGame();
        TestSet testSet = new TestSet();
        testSet.setTestNo(1);
        testSet.startTestset();

        Game g1 = new Game();
        g1.setGameName("Deneme");
        g1.setIsBeliefUpdates(true);
        g1.setNoOfAgents(100);
        g1.setSamplingRatio(100);
        g1.setTestDataset("Heyt Be!");

        AgentStat agStats = new AgentStat();
        agStats.setAgentID(1);
        agStats.setNoOfFails(10);
        agStats.setNoOfSuccess(100);
        agStats.setValidationAccuracy(100);
        g1.addAgentStats(agStats);
        List <AgentStat> agStat = new ArrayList<AgentStat>();
        agStat.add(agStats);
        testSet.addGame(g1);
        testSet.endTestSet();
        MongoGameStatsFactory.addTestSetToDB(testSet);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            test2();
        } catch (MongoException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

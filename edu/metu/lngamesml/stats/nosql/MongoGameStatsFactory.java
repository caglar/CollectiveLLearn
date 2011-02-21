package edu.metu.lngamesml.stats.nosql;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.Mongo;
import edu.metu.lngamesml.stats.nosql.game.AgentStat;
import edu.metu.lngamesml.stats.nosql.game.RunningAgentStat;
import edu.metu.lngamesml.stats.nosql.game.RunningStat;
import edu.metu.lngamesml.stats.nosql.game.Stat;

import java.net.UnknownHostException;

/**
 *
 * @author caglar
 */
public class MongoGameStatsFactory {
    
    private static Datastore DS = null;

    public static void initGame() throws UnknownHostException{
        Morphia morphia = new Morphia();
        morphia.map(TestSet.class).map(Game.class);
        morphia.map(TestSet.class).map(Game.class).map(AgentStat.class);
        morphia.map(TestSet.class).map(Game.class).map(RunningAgentStat.class);
        morphia.map(TestSet.class).map(Game.class).map(RunningStat.class);
        morphia.map(TestSet.class).map(Game.class).map(Stat.class);
        DS = morphia.createDatastore(new Mongo(), "GameStats");
    }

    public static void addTestSetToDB(TestSet tSet) throws NullPointerException{
        if (DS != null){
            DS.save(tSet);
        } else {
            throw new NullPointerException("Datastore is null. Please initialize the factory first.");
        }
    }

    public static void addGameToTestSet(Game game, TestSet ts){
        UpdateOperations<TestSet> ops = DS.createUpdateOperations(TestSet.class).add("Gamez", game, false);
        Query<TestSet> updateQuery = DS.createQuery(TestSet.class).field("_id").equal(ts.getId());
        DS.update(updateQuery, ops);
    }

    public static void addRunningStatToGame(RunningStat rStat, Game game){
        UpdateOperations<Game> ops = DS.createUpdateOperations(Game.class).add("runStats", rStat, false);
        Query<Game> updateQuery = DS.createQuery(Game.class).field("id").equal(game.getId());
        //updateQuery.hasThisElement(game);
        DS.update(updateQuery, ops);
    }

    public static void addStatToGame(Stat st, Game game){

    }

    public static void addAgentStatToGame(AgentStat aStat, Game game){

    }

    public static void addRAgentStatToGame(RunningAgentStat rAgStat, Game game){

    }

}

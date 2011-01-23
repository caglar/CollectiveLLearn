package edu.metu.lngamesml.stats;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import edu.metu.lngamesml.stats.game.AgentStat;
import edu.metu.lngamesml.stats.game.RunningAgentStat;
import edu.metu.lngamesml.stats.game.RunningStat;
import edu.metu.lngamesml.stats.game.Stat;

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
    
}

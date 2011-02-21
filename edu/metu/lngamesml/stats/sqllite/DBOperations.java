package edu.metu.lngamesml.stats.sqllite;

import edu.metu.lngamesml.stats.sqllite.game.RunningAgentStat;
import edu.metu.lngamesml.stats.sqllite.game.AgentStat;
import edu.metu.lngamesml.stats.sqllite.game.RunningStat;
import edu.metu.lngamesml.stats.sqllite.game.Stat;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 1/23/11
 * Time: 9:49 PM
 * To change this template use File | Settings | File Templates.
 */

public class DBOperations {
    private static final String CREATE = "CREATE TABLE ";
    private static final String TYPE_PKEY = "(id INTEGER PRIMARY KEY ASC,";
    private static final String END = ");";
    private DBManager DBMan = null;

    public DBManager getDBMan() {
        return this.DBMan;
    }

    public void setDBMan(DBManager DBMan) {
        this.DBMan = DBMan;
    }

    private boolean isDBUp() throws Exception {
        boolean status = true;
        if (DBMan == null){
            status = false;
            throw new Exception("DBManager is not set. ");
        } else if (DBMan.isConnClosed()){
            status = false;
            throw new Exception("DBManager's connection is closed. Please check it. ");
        }
        return status;
    }

    public void createDatabase(DBManager dbMan){
        try {
            isDBUp();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String queryTestSets = CREATE + "TestSet " + TYPE_PKEY + " test_no INTEGER," +
                " test_date TEXT," +
                " start_time TEXT," +
                " end_time TEXT" + END;

        String queryGames = CREATE + "Games " +
                TYPE_PKEY +
                " game_name TEXT," +
                " test_dataset TEXT, " +
                " sampling_ratio REAL," +
                " belief_updates INTEGER, " +
                " no_of_agents INTEGER," +
                " testset INTEGER," +
                " FOREIGN KEY(testset) REFERENCES TestSet(id) " + END;

        String queryStats = CREATE + "Stats " +
                TYPE_PKEY + "" +
                " no_success INTEGER," +
                " no_fails INTEGER," +
                " accuracy REAL," +
                " game INTEGER," +
                " FOREIGN KEY(game) REFERENCES Game(id)" + END;

        String queryAgentStats = CREATE + "AgentStats " +
                TYPE_PKEY +
                " agent_id INTEGER," +
                " no_success INTEGER," +
                " no_fails INTEGER," +
                " accuracy REAL," +
                " validation_acc REAL," +
                " game INTEGER," +
                " FOREIGN KEY(game) REFERENCES Game(id)" + END;

        String queryRunningStats = CREATE + "RunningStats " +
                TYPE_PKEY +
                "no_success INTEGER, " +
                "no_fails INTEGER, " +
                "current_accuracy REAL, " +
                "no_of_items_processed INTEGER, " +
                "game INTEGER, " +
                "FOREIGN KEY(game) REFERENCES Game(id)" + END;

        String queryRunningAgentStats = CREATE + "RunningAgentStats " +
                TYPE_PKEY +
                " agent_id INTEGER," +
                " no_success INTEGER," +
                " no_fails INTEGER," +
                " current_accuracy REAL," +
                " no_of_items_processed INTEGER," +
                " game INTEGER," +
                " FOREIGN KEY(game) REFERENCES Game(id)" + END;

        try {
            dbMan.executeInsertUpdate(queryTestSets);
            dbMan.executeInsertUpdate(queryGames);
            dbMan.executeInsertUpdate(queryAgentStats);
            dbMan.executeInsertUpdate(queryRunningAgentStats);
            dbMan.executeInsertUpdate(queryRunningStats);
            dbMan.executeInsertUpdate(queryStats);
        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getInsertQuery(String tableName, String fields, String values){
        String query = "INSERT INTO " + tableName + "(" + fields + ")" + " VALUES( " + values + ");";
        return query;
    }

    private String quoteString(String str){
        return "\""+str+"\"";
    }

    public void addTestset(TestSet ts) throws Exception {
        String tblName = "TestSet";
        String fields  = "test_date, " +
                        " start_time," +
                        " end_time" ;
        String values = quoteString(ts.getDate())+ "," + quoteString(ts.getStartTime()) + "," + quoteString(ts.getEndTime());
        String query = getInsertQuery(tblName, fields, values);
        boolean status = true;
        try {
            status = isDBUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(status){ DBMan.executeInsertUpdate(query);}
    }

    public void addGame(Game game) throws Exception {
        String tblName = "Games";
        String fields  = " game_name, " +
                " test_dataset, " +
                " sampling_ratio," +
                " belief_updates, " +
                " no_of_agents," +
                " testset";
        String values = quoteString(game.getGameName()) + ", " + quoteString(game.getTestDataset()) + Double.toString(game.getSamplingRatio())
                + (game.isIsBeliefUpdates()?"1, ":"0, ") + quoteString(Integer.toString(game.getNoOfAgents())) + ", " +
                quoteString(Integer.toString(game.getTestSetId())) ;
        String query = getInsertQuery(tblName, fields, values);
        boolean status = true;
        try {
            status = isDBUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(status){ DBMan.executeInsertUpdate(query);}
    }

    public void addStat(Stat stat) throws Exception {
        String tblName = "Stat";
        String fields = " no_success," +
                " no_fails," +
                " accuracy," +
                " game";
        String values = Integer.toString(stat.getNoOfSucesses()) + ", " + Integer.toString(stat.getNoOfFails()) +
                ", " + Double.toString(stat.getAccuracy()) + ", " + Integer.toString(stat.getGameId());
        String query = getInsertQuery(tblName, fields, values);
        boolean status = true;
        try {
            status = isDBUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(status){ DBMan.executeInsertUpdate(query);}
    }

    public void addAgentStat(AgentStat agentStat) throws Exception {
        String tblName = "AgentStat";
        String fields  = "agent_id, no_success, " +
                " no_fails," +
                " accuracy," +
                " validation_acc," +
                " game";
        String values = Integer.toString(agentStat.getAgentID()) + ", " + Integer.toString(agentStat.getNoOfSuccess()) + ", "
                + Integer.toString(agentStat.getNoOfFails()) +
                ", " + Double.toString(agentStat.getAccuracy()) + ", " + Double.toString(agentStat.getValidationAccuracy()) +
                ", "+ Integer.toString(agentStat.getGameId());
        String query = getInsertQuery(tblName, fields, values);
        boolean status = true;
        try {
            status = isDBUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(status){ DBMan.executeInsertUpdate(query);}
    }

    public void addRunningAgentStat(RunningAgentStat runningAgentStat) throws Exception {
        String tblName = "RunningAgentStat";
        String fields = "agent_id, " +
                "no_success, " +
                "no_fails, " +
                "current_accuracy, " +
                "no_of_items_processed, " +
                "game ";
        String values = Integer.toString(runningAgentStat.getAgentId()) + ", "
                + Integer.toString(runningAgentStat.getNoOfSuccess()) + ", " + Integer.toString(runningAgentStat.getNoOfFails()) +
                ", " + Double.toString(runningAgentStat.getCurrentAccuracy()) + ", "
                + Double.toString(runningAgentStat.getNoOfItemsProcessed()) +
                ", " + Integer.toString(runningAgentStat.getGameId());
        String query = getInsertQuery(tblName, fields, values);
        boolean status = true;
        try {
            status = isDBUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(status){ DBMan.executeInsertUpdate(query);}
    }

    public void addRunningStat(RunningStat runningStat) throws Exception {
        String tblName = "RunningStat";
        String fields = " no_success," +
                " no_fails," +
                " current_accuracy," +
                " no_of_items_processed," +
                " game";
        String values = Integer.toString(runningStat.getNoOfSuccesses()) + ", " + Integer.toString(runningStat.getNoOfFails()) + ", "
                + Double.toString(runningStat.getAccuracy())
                + ", " + Integer.toString(runningStat.getNoOfItemsProcessed()) + ", " + Integer.toString(runningStat.getGameId());
        String query = getInsertQuery(tblName, fields, values);
        boolean status = true;
        try {
            status = isDBUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(status){ DBMan.executeInsertUpdate(query);}
    }
}

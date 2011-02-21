package edu.metu.lngamesml.stats.nosql;

import com.google.code.morphia.annotations.Embedded;
import edu.metu.lngamesml.stats.nosql.game.AgentStat;
import edu.metu.lngamesml.stats.nosql.game.RunningAgentStat;
import edu.metu.lngamesml.stats.nosql.game.RunningStat;
import edu.metu.lngamesml.stats.nosql.game.Stat;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author caglar
 */
@Embedded
public final class Game {
    private String id;
    private String GameName = "";
    private String TestDataset = "";
    private double SamplingRatio = 0.0;
    private boolean IsBeliefUpdates = true;
    private int NoOfAgents = 10;

    @Embedded
    private List<AgentStat> agStats = new ArrayList<AgentStat>();

    @Embedded
    private List<RunningAgentStat> runAgStats = new ArrayList<RunningAgentStat>();

    @Embedded
    private List<RunningStat> runStats = new ArrayList<RunningStat>();

    @Embedded
    private Stat stats = new Stat();

    public Game(){
    }
    
    public Game(String gameName, String testDataset, int noOfAgents, boolean isBeliefUpdates, double samplingRatio){
        setGameName(gameName);
        setIsBeliefUpdates(isBeliefUpdates);
        setNoOfAgents(noOfAgents);
        setSamplingRatio(samplingRatio);
    }

    public void addAgentStats(AgentStat agStat){
        this.agStats.add(agStat);
    }

    public void addRunningAgentStats(RunningAgentStat runAgStat){
        this.runAgStats.add(runAgStat);
    }

    public void addRunningStat(RunningStat rStat){
        this.runStats.add(rStat);
    }

    public int getNoOfAgents() {
        return NoOfAgents;
    }

    public void setNoOfAgents(int NoOfAgents) {
        this.NoOfAgents = NoOfAgents;
        
    }

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String GameName) {
        this.GameName = GameName;
        
    }

    public boolean isIsBeliefUpdates() {
        return IsBeliefUpdates;
    }

    public void setIsBeliefUpdates(boolean IsBeliefUpdates) {
        this.IsBeliefUpdates = IsBeliefUpdates;
        
    }

    public double getSamplingRatio() {
        return SamplingRatio;
    }

    public void setSamplingRatio(double SamplingRatio) {
        this.SamplingRatio = SamplingRatio;
    }

    public String getTestDataset() {
        return TestDataset;
    }

    public void setTestDataset(String TestDataset) {
        this.TestDataset = TestDataset;
    }

    public List<AgentStat> getAgStats() {
        return agStats;
    }

    public void setAgStats(List<AgentStat> agStats) {
        this.agStats = agStats;
    }

    public List<RunningAgentStat> getRunAgStats() {
        return runAgStats;
    }

    public void setRunAgStats(List<RunningAgentStat> runAgStats) {
        this.runAgStats = runAgStats;
    }

    public Stat getStats() {
        return stats;
    }

    public void setStats(Stat stats) {
        this.stats = stats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

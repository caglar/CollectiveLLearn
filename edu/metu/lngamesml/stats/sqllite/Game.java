package edu.metu.lngamesml.stats.sqllite;

/**
 *
 * @author caglar
 */
public final class Game {
    private int id;
    private String GameName = "";
    private String TestDataset = "";
    private double SamplingRatio = 0.0;
    private boolean IsBeliefUpdates = true;
    private int NoOfAgents = 10;
    private int TestSetId;


    public Game(){
    }
    
    public Game(String gameName, String testDataset, int noOfAgents, boolean isBeliefUpdates, double samplingRatio){
        setGameName(gameName);
        setIsBeliefUpdates(isBeliefUpdates);
        setNoOfAgents(noOfAgents);
        setSamplingRatio(samplingRatio);
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

    public int getTestSetId() {
        return TestSetId;
    }

    public void setTestSetId(int testSetId) {
        TestSetId = testSetId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

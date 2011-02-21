/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.metu.lngamesml.stats.sqllite.game;


import edu.metu.lngamesml.eval.game.AgentsIndividualPerf;

/**
 *
 * @author caglar
 */


public class RunningAgentStat {

    private long Timestep;
    private int AgentId = 0;
    private double CurrentAccuracy;
    private int NoOfItemsProcessed;
    private int NoOfSuccess = 0;
    private int NoOfFails = 0;

    private AgentsIndividualPerf AIPerf;
    private int Id;
    private int GameId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getGameId() {
        return GameId;
    }

    public void setGameId(int gameId) {
        GameId = gameId;
    }
    public RunningAgentStat(){
            AIPerf = new AgentsIndividualPerf();
    }

    public double getCurrentAccuracy() {
        return CurrentAccuracy;
    }

    public void setCurrentAccuracy(double CurrentAccuracy) {
        this.CurrentAccuracy = CurrentAccuracy;
    }

    public int getNoOfFails() {
        return NoOfFails;
    }

    public void setNoOfFails(int NoOfFails) {
        this.NoOfFails = NoOfFails;
    }

    public int getNoOfItemsProcessed() {
        return NoOfItemsProcessed;
    }

    public void setNoOfItemsProcessed(int NoOfItemsProcessed) {
        this.NoOfItemsProcessed = NoOfItemsProcessed;
    }

    public int getNoOfSuccess() {
        return NoOfSuccess;
    }

    public void setNoOfSuccess(int NoOfSuccess) {
        this.NoOfSuccess = NoOfSuccess;
    }

    public long getTimestep() {
        return Timestep;
    }

    public void setTimestep(long Timestep) {
        this.Timestep = Timestep;
    }

    public void addNoOfSuccesses(){
        NoOfSuccess++;
    }

    public void addNoOfFailures(){
        NoOfFails++;
    }

    public int getAgentId() {
        return AgentId;
    }

    public void setAgentId(int agentId) {
        AgentId = agentId;
    }

    public AgentsIndividualPerf getAIPerf() {
        return AIPerf;
    }

    public void setAIPerf(AgentsIndividualPerf AIPerf) {
        this.AIPerf = AIPerf;
    }

    public void addDecision(int agentNo, int agLabel, int trueLabel){
        AIPerf.addDecision(agentNo, agLabel, trueLabel);
        CurrentAccuracy = AIPerf.getAccuracy();
    }
}

package edu.metu.lngamesml.eval.game;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 12/30/10
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */

public class AgentMeasurements implements Measurements{

    private int NoOfSuccesses = 0;
    private int NoOfFailures = 0;
    private int NoOfSpeakerRole = 0;
    private int NoOfHearerRole = 0;
    private int AgentId = -1;

    public void setGameStatus(boolean isSuccess) {
        if(isSuccess){
            addSuccess();
        } else {
            addFailure();
        }
    }

    private void addSuccess(){
        NoOfSuccesses++;
    }

    private void addFailure(){
        NoOfFailures++;
    }

    public void addSpeakerRole(){
        NoOfSpeakerRole++;
    }

    public void addHearerRole(){
        NoOfHearerRole++;
    }

    public int getAgentId() {
        return AgentId;
    }

    public void setAgentId(int agentId) {
        AgentId = agentId;
    }

    public int getNoOfSuccesses() {
        return NoOfSuccesses;
    }

    public int getNoOfFailures() {
        return NoOfFailures;
    }

    public int getNoOfSpeakerRole() {
        return NoOfSpeakerRole;
    }

    public int getNoOfHearerRole() {
        return NoOfHearerRole;
    }

    public void resetMeasurements() {
        NoOfFailures = 0;
        NoOfSuccesses = 0;
        AgentId = -1;
    }
}

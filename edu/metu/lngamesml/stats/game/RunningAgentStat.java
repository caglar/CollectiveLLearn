/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.metu.lngamesml.stats.game;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Transient;
import edu.metu.lngamesml.eval.game.AgentsIndividualPerf;

/**
 *
 * @author caglar
 */
@Embedded
public class RunningAgentStat {

    private long Timestep;
    private double CurrentAccuracy;
    private int NoOfItemsProcessed;
    private int NoOfSuccess = 0;
    private int NoOfFails = 0;
    @Transient
    private AgentsIndividualPerf AIPerf;

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

    public void addDecision(int agentNo, int agLabel, int trueLabel){
        AIPerf.addDecision(agentNo, agLabel, trueLabel);
        CurrentAccuracy = AIPerf.getAccuracy();
    }
}

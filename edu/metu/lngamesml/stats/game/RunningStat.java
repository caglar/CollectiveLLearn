/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.metu.lngamesml.stats.game;

import com.google.code.morphia.annotations.Embedded;

/**
 *
 * @author caglar
 */
@Embedded
public class RunningStat {

    private long Timestep = 0;
    private int NoOfItemsProcessed = 0;
    private int NoOfSuccesses = 0;
    private int NoOfFails = 0;
    private double Accuracy = 0.0;

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

    public int getNoOfSuccesses() {
        return NoOfSuccesses;
    }

    public void setNoOfSuccess(int NoOfSuccess) {
        this.NoOfSuccesses = NoOfSuccess;
    }

    public long getTimestep() {
        return Timestep;
    }

    public void setTimestep(long Timestep) {
        this.Timestep = Timestep;
    }

    public void incrementNoOfFails(){
        NoOfFails++;
    }

    public void incrementNoOfSuccesses(){
        NoOfSuccesses++;
    }

    public double getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(double accuracy) {
        Accuracy = accuracy;
    }
}

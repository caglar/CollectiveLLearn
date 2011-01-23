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
public class Stat {
    private int NoOfSucesses = 0;
    private int NoOfFails = 0;
    private double Accuracy = 0.0;

    public double getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(double Accuracy) {
        this.Accuracy = Accuracy;
    }

    public int getNoOfFails() {
        return NoOfFails;
    }

    public void setNoOfFails(int NoOfFails) {
        this.NoOfFails = NoOfFails;
    }

    public int getNoOfSucesses() {
        return NoOfSucesses;
    }

    public void setNoOfSucesses(int NoOfSucesses) {
        this.NoOfSucesses = NoOfSucesses;
    }
}

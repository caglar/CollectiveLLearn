/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.metu.lngamesml.stats.sqllite.game;

/**
 *
 * @author caglar
 */
public class Stat {
    private int NoOfSucesses = 0;
    private int NoOfFails = 0;
    private double Accuracy = 0.0;
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

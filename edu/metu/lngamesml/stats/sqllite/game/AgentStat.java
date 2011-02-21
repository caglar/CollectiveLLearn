/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.metu.lngamesml.stats.sqllite.game;


/**
 *
 * @author caglar
 *
 */

public class AgentStat {

    private int AgentID = 0;
    private int NoOfFails = 0;
    private int NoOfSuccess = 0;
    private double ValidationAccuracy = 0.0;
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
    public int getAgentID() {
        return AgentID;
    }

    public void setAgentID(int AgentID) {
        this.AgentID = AgentID;
    }

    public int getNoOfFails() {
        return NoOfFails;
    }

    public void setNoOfFails(int NoOfFails) {
        this.NoOfFails = NoOfFails;
    }

    public int getNoOfSuccess() {
        return NoOfSuccess;
    }

    public void setNoOfSuccess(int NoOfSuccess) {
        this.NoOfSuccess = NoOfSuccess;
    }

    public double getValidationAccuracy() {
        return ValidationAccuracy;
    }

    public void setValidationAccuracy(double ValidationAccuracy) {
        this.ValidationAccuracy = ValidationAccuracy;
    }

    public double getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(double accuracy) {
        Accuracy = accuracy;
    }
}

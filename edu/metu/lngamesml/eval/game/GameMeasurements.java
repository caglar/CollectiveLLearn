package edu.metu.lngamesml.eval.game;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 12/30/10
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */

public class GameMeasurements {

    private int NoOfSuccesses = 0;
    private int NoOfFailures = 0;
    private boolean GameStatus = false;

    public void setGameStatus(boolean gameStatus){
        GameStatus = gameStatus;
        if(gameStatus){
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

    public int getNoOfSuccesses() {
        return NoOfSuccesses;
    }

    public int getNoOfFailures() {
        return NoOfFailures;
    }

    public boolean isGameStatus() {
        return GameStatus;
    }

    public void resetMeasurements() {
        NoOfFailures = 0;
        NoOfSuccesses = 0;
        GameStatus = false;
    }
}
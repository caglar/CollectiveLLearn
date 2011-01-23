package edu.metu.lngamesml.eval.game;

import edu.metu.lngamesml.exception.ZeroDurationException;
import edu.metu.lngamesml.utils.StopWatch;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 15, 2010
 * Time: 1:45:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicGameEvaluator implements GameEvaluator {

    private int NoOfAgents = 0;
    private int NoOfInstances = 0;
    private GameMeasurements GMeasurements[];
    private AgentMeasurements AMeasurements[];
    private StopWatch SWatch;

    public BasicGameEvaluator(int noOfAgents, int noOfInstances){
        NoOfAgents = noOfAgents;
        NoOfInstances = noOfInstances;
        SWatch = new StopWatch();
        initMeasurements();
        SWatch.startTimer();
    }

    private void initGameMeasurements(){
        GMeasurements = new GameMeasurements[NoOfInstances];
        for (int i = 0; i < NoOfInstances; i++) {
            GMeasurements[i] = new GameMeasurements();
        }
    }

    private void initAgentMeasurements(){
        AMeasurements = new AgentMeasurements[NoOfAgents];
        for (int i = 0; i < NoOfAgents; i++) {
            AMeasurements[i] = new AgentMeasurements();
        }
    }

    protected void initMeasurements(){
        initGameMeasurements();
        initAgentMeasurements();
    }

    public void addMeasurements(int speakerAgentNo, int hearerAgentNo, int exampleNo, boolean isSuccess){
        addGameMeasurement(exampleNo, isSuccess);
        addAgentMeasurement(speakerAgentNo, hearerAgentNo, isSuccess);
    }

    public void addGameMeasurement(int exampleNo, boolean isSuccess){
        GMeasurements[exampleNo].setGameStatus(isSuccess);
    }

    public void addAgentMeasurement(int speakerAgentNo, int hearerAgentNo, boolean isSuccess){
        AMeasurements[speakerAgentNo].setGameStatus(isSuccess);
        AMeasurements[speakerAgentNo].addSpeakerRole();
        AMeasurements[hearerAgentNo].setGameStatus(isSuccess);
        AMeasurements[hearerAgentNo].addHearerRole();
    }

    public double getElapsedTime(){
        double durationSec = 0;
        try {
            durationSec =  ((double)SWatch.getDuration() / (double)1000000000);
        } catch (ZeroDurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return durationSec;
    }

    public void resetAgentMeasurements(){
        for (int i = 0; i < NoOfInstances; i++) {
            AMeasurements[i].resetMeasurements();
        }
    }

    public void resetGameMeasurements(){
        for (int i = 0; i < NoOfInstances; i++) {
            GMeasurements[i].resetMeasurements();
        }
    }

    public GameMeasurements[] getGMeasurements(){
        return GMeasurements;
    }

    public AgentMeasurements[] getAMeasurements() {
        return AMeasurements;
    }

    public String toString() {
        double elapsedTime = getElapsedTime();
        String message = "Elapsed Time is " + elapsedTime + "\n";
        return message;
    }
}

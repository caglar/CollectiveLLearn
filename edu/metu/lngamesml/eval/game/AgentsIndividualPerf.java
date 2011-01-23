package edu.metu.lngamesml.eval.game;


/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 1/2/11
 * Time: 8:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentsIndividualPerf {

    private int AgentsNoOfCorrect;
    private int NoOfExamples = 0;

    public void addDecision(int agentNo, int agLabel, int trueLabel){
        if (agLabel == trueLabel) {
            AgentsNoOfCorrect++;
        }
        NoOfExamples++;
    }

    public double getAccuracy(){
        return ((double)AgentsNoOfCorrect/(double)NoOfExamples) * 100;
    }

    public int getAgentsNoOfCorrect() {
        return AgentsNoOfCorrect;
    }

    public int getAgentsNoOfIncorrect() {
        return NoOfExamples - AgentsNoOfCorrect;
    }

    public int getNoOfExamples() {
        return NoOfExamples;
    }
}
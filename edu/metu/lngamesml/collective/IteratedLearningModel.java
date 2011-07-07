package edu.metu.lngamesml.collective;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.LearnerTypes;
import edu.metu.lngamesml.utils.random.MersenneTwister;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 7/12/10
 * Time: 11:06 PM
 * TODO: Implement Simon, Kirby's Iterated Learning Model for Machine Learning in this class.
 * Classifiers are trained sequentially using ILM.
 */
public class IteratedLearningModel {
    protected int NoOfAgents;
    protected int SamplingRatio;
    protected ArrayList<Agent> Agents = new ArrayList<Agent>();
    protected MersenneTwister MRnd = new MersenneTwister(System.nanoTime());
    protected LearnerTypes LType;

    protected double Accuracy;

    public IteratedLearningModel() {
        NoOfAgents = 10;
        SamplingRatio = 20;
        LType = LearnerTypes.C45;
        Accuracy = 0.0;
    }

    public void initModel() {

    }

    public void trainModel() {
    }

    public void testModel() {
    }

    public void startModel() {
    }

    public double getAccuracy() {
        return Accuracy;
    }

}

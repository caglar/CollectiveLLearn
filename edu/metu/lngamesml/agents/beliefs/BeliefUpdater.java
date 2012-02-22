package edu.metu.lngamesml.agents.beliefs;

import edu.metu.lngamesml.agents.com.CategoricalComm;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 12/30/10
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BeliefUpdater {

    protected double HearerBelief = 0.0;
    protected double SpeakerBelief = 0.0;
    //protected final double UpdateCoeff = 0.05;
    protected final double UpdateCoeff = 0.25;

    protected abstract void failUpdate(double sBelief, double hBelief);

    protected abstract void successUpdate(double sBelief, double hBelief);

    protected abstract void updateBeliefs(CategoricalComm sCatComm, CategoricalComm hCatComm, SigmoidFunctionTypes sigFunType, boolean isSuccess);

    public abstract double getBelief();
}
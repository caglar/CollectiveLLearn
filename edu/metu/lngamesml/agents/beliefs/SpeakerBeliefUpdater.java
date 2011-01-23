package edu.metu.lngamesml.agents.beliefs;

import edu.metu.lngamesml.agents.com.CategoricalComm;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 12/31/10
 * Time: 12:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class SpeakerBeliefUpdater extends BeliefUpdater{

    @Override
    public void failUpdate(double sBelief, double hBelief) {
        //SpeakerBelief = sBelief - (UpdateCoeff * UpdateCoeff) * hBelief;
        SpeakerBelief = sBelief - (UpdateCoeff) * hBelief;
    }

    @Override
    public void successUpdate(double sBelief, double hBelief) {
        SpeakerBelief = sBelief + UpdateCoeff * hBelief;
    }

    @Override
    public void updateBeliefs(CategoricalComm sCatComm, CategoricalComm hCatComm, boolean isSuccess) {
        if(isSuccess) {
            successUpdate(sCatComm.getConfidence(), hCatComm.getConfidence());
        } else {
            failUpdate(sCatComm.getConfidence(), hCatComm.getConfidence());
        }
    }

    public double getBelief() {
        return SpeakerBelief;
    }
}

package edu.metu.lngamesml.agents.beliefs;

import edu.metu.lngamesml.agents.com.CategoricalComm;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 12/31/10
 * Time: 12:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class HearerBeliefUpdater extends BeliefUpdater{
    @Override
    protected void failUpdate(double sBelief, double hBelief) {
        HearerBelief = (sBelief - hBelief) * (1 - UpdateCoeff) + UpdateCoeff * sBelief;
        System.out.println("hBelief: "+hBelief + "sBelief: " + sBelief + "new belief " + HearerBelief);
    }

    @Override
    protected void successUpdate(double sBelief, double hBelief) {
        HearerBelief = hBelief + (UpdateCoeff * sBelief);
    }

    @Override
    protected void updateBeliefs(CategoricalComm sCatComm, CategoricalComm hCatComm, boolean isSuccess) {
        if (isSuccess) {
            successUpdate(sCatComm.getConfidence(), hCatComm.getConfidence());
        } else {
            failUpdate(sCatComm.getConfidence(), hCatComm.getConfidence());
        }
    }

    @Override
    public double getBelief() {
        return HearerBelief;
    }
}

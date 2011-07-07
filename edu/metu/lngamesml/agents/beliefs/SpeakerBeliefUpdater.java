package edu.metu.lngamesml.agents.beliefs;

import edu.metu.lngamesml.agents.com.CategoricalComm;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 12/31/10
 * Time: 12:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class SpeakerBeliefUpdater extends BeliefUpdater {

    private static int SuccessFactor = 3;
    private SigmoidFunctionTypes sigmoidFunctionTypes;
    private BeliefScaler beliefScaler = null;

    public SpeakerBeliefUpdater() {
        sigmoidFunctionTypes = SigmoidFunctionTypes.NONE;
        beliefScaler = new BeliefScaler();
    }

    public SpeakerBeliefUpdater(SigmoidFunctionTypes sigFunType) {
        beliefScaler = new BeliefScaler();
        beliefScaler.setSigmoidFunctionType(sigFunType);
    }

    @Override
    public void failUpdate(double sBelief, double hBelief) {
        SpeakerBelief = sBelief - (UpdateCoeff) * hBelief;
    }

    @Override
    public void successUpdate(double sBelief, double hBelief) {
        SpeakerBelief = sBelief + SuccessFactor * UpdateCoeff * hBelief;
    }

    @Override
    public void updateBeliefs(CategoricalComm sCatComm, CategoricalComm hCatComm, SigmoidFunctionTypes sigFunType, boolean isSuccess) {
        if (isSuccess) {
            successUpdate(sCatComm.getConfidence(), hCatComm.getConfidence());
        } else {
            failUpdate(sCatComm.getConfidence(), hCatComm.getConfidence());
        }
    }

    public double getBelief() {
        return SpeakerBelief;
    }
}

package edu.metu.lngamesml.agents.beliefs;

import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.exception.UnknownSigmoidFunction;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 12/31/10
 * Time: 12:44 AM
 * To change this template use File | Settings | File Templates.
 */

public class HearerBeliefUpdater extends BeliefUpdater {
    private static int SuccessFactor = 2;
    private SigmoidFunctionTypes sigmoidFunctionTypes;
    private BeliefScaler beliefScaler = null;

    public HearerBeliefUpdater() {
        sigmoidFunctionTypes = SigmoidFunctionTypes.NONE;
        beliefScaler = new BeliefScaler();
    }

    public HearerBeliefUpdater(SigmoidFunctionTypes sigFunType) {
        beliefScaler = new BeliefScaler();
        beliefScaler.setSigmoidFunctionType(sigFunType);
    }

    @Override
    protected void failUpdate(double sBelief, double hBelief) {
        HearerBelief = (sBelief - hBelief) * (1 - UpdateCoeff) + UpdateCoeff * sBelief;
        //System.out.println("hBelief: "+hBelief + "sBelief: " + sBelief + "new belief " + HearerBelief);
        if (sigmoidFunctionTypes != SigmoidFunctionTypes.NONE) {
            try {
                HearerBelief = beliefScaler.scaleBelief(HearerBelief);
            } catch (UnknownSigmoidFunction unknownSigmoidFunction) {
                unknownSigmoidFunction.printStackTrace();
            }
        }
    }

    @Override
    protected void successUpdate(double sBelief, double hBelief) {
        HearerBelief = hBelief + SuccessFactor * (UpdateCoeff * sBelief);
        if (sigmoidFunctionTypes != SigmoidFunctionTypes.NONE) {
            try {
                HearerBelief = beliefScaler.scaleBelief(HearerBelief);
            } catch (UnknownSigmoidFunction unknownSigmoidFunction) {
                unknownSigmoidFunction.printStackTrace();
            }
        }
    }

    @Override
    protected void updateBeliefs(CategoricalComm sCatComm, CategoricalComm hCatComm, SigmoidFunctionTypes sigFunType, boolean isSuccess) {
        this.sigmoidFunctionTypes = sigFunType;
        beliefScaler.setSigmoidFunctionType(sigmoidFunctionTypes);
        if (isSuccess) {
            successUpdate(sCatComm.getConfidence(), hCatComm.getConfidence());
        } else {
            failUpdate(sCatComm.getConfidence(), hCatComm.getConfidence());
        }
    }

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

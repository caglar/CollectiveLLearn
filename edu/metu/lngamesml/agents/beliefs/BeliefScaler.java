package edu.metu.lngamesml.agents.beliefs;

import edu.metu.lngamesml.exception.UnknownSigmoidFunction;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 6/22/11
 * Time: 1:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class BeliefScaler {

    private SigmoidFunctionTypes sigmoidFunctionType;

    public void setSigmoidFunctionType(SigmoidFunctionTypes sigFunType) {
        sigmoidFunctionType = sigFunType;
    }

    public double scaleBelief(double belief) throws UnknownSigmoidFunction {
        double scaledBelief = 0.0;
        if (sigmoidFunctionType == SigmoidFunctionTypes.TANH) {
            scaledBelief = tanh(belief);
        } else if (sigmoidFunctionType == SigmoidFunctionTypes.LOGISTIC) {
            scaledBelief = logistic(belief);
        } else if (sigmoidFunctionType == SigmoidFunctionTypes.NONE) {
            scaledBelief = belief;
        } else {
            throw new UnknownSigmoidFunction("Unknown sigmoid function is passed to the belief scaler.");
        }
        return scaledBelief;
    }

    protected double tanh(double belief) {
        return Math.tanh(belief);
    }

    protected double logistic(double belief) {
        return (1 / (1 + Math.exp(-belief)));
    }
}

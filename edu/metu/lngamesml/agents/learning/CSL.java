package edu.metu.lngamesml.agents.learning;

import edu.metu.lngamesml.core.DoubleVector;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 11/01/11
 * Time: 8:37 AM
 * TODO Implement Siskind's cross situational learning algorithm for language acquisition here using the Tanenbaum's Bayesian approach.
 */

//Nonparametric bayesian learning.

public class CSL {
    protected double mPriorDists[];
    protected double mPosteriorDists[];
    protected DoubleVector mCrossSituationalFreq;
}

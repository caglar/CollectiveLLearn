package edu.metu.lngamesml.utils.random;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Jun 5, 2010
 * Time: 8:35:39 PM
 * To change this template use File | Settings | File Templates.
 */
public final class WeightedRandomNumberGen {

    double[] Totals;
    Random rnd;
    public WeightedRandomNumberGen(double[] weights, long seed) {
        rnd = new MersenneTwister(seed);
        Totals = new double[weights.length];
        initWRNG(weights);
    }

    /*
    * Initializing function of Random Number generator
    * @param weights in a double array. Note that the weights here are assumed to
    * be positive. If there are negative ones. Sort the Totals array before the binary search
    * @return void
    */

    private void initWRNG(double[] weights) {
        double runningTotal = 0;
        int i = 0;
        for (double w : weights) {
            runningTotal += w;
            Totals[i++] = runningTotal;
        }
    }

    /*
     * Initializing function of Random Number generator
     * @param weights in a double array. Note that the weights here are assumed to
     * be positive. If there are negative ones. Sort the Totals array before the binary search
     * @return void
     */

    public void reInitWRNG(double[] weights) {
        initWRNG(weights);
    }

    /*
     * @return idx the weighted random number. Actually this sends the weighted randomly
     * selected index of weights vector.
     */

    public int next() {

        double rndNum = rnd.nextDouble() * Totals[Totals.length - 1];
        int sNum = Arrays.binarySearch(Totals, rndNum);
        int idx = (sNum < 0) ? (Math.abs(sNum) - 1) : sNum;
        return idx;
    }
}

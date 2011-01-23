package edu.metu.lngamesml.eval.classification;

import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.eval.Evaluator;
import weka.core.Instance;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 15, 2010
 * Time: 1:45:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicClassificationEvaluator implements Evaluator {

    private int NoOfObservations;
    private int NoOfSuccessfulObservations;
    private int NoOfFailedObservations;
    private double AccuracyPercentage;

    public BasicClassificationEvaluator() {
        NoOfObservations = 0;
        NoOfSuccessfulObservations = 0;
        NoOfFailedObservations = 0;
        AccuracyPercentage = 0.0;
    }

    public void addPerformanceObservation(CategoricalComm catComm, Instance inst) {
        //Logging.info("Instance Class value:" + ((int)inst.classValue()) + "Cat Comm " + catComm.getStatementNo());
        if (((int) inst.classValue()) == catComm.getStatementNo()) {
            NoOfSuccessfulObservations++;
        } else {
            NoOfFailedObservations++;
        }
        NoOfObservations++;
        AccuracyPercentage = 100 * ((double) NoOfSuccessfulObservations / (double) NoOfObservations);
    }

    public void addPerformanceObservation(int category, Instance inst) {
        //Logging.info("Instance Class value:" + ((int)inst.classValue()) + "Cat Comm " + catComm.getStatementNo());
        if (((int) inst.classValue()) == category) {
            NoOfSuccessfulObservations++;
        } else {
            NoOfFailedObservations++;
        }
        NoOfObservations++;
        AccuracyPercentage = 100 * ((double) NoOfSuccessfulObservations / (double) NoOfObservations);
    }

    @Override
    public String getPerformanceMetrics() {
        String performance = "Accuracy is " + AccuracyPercentage + "% \n No of wrong instances: " + NoOfFailedObservations + " \n No of correct instances: " + NoOfSuccessfulObservations;
        return performance;
    }

    public double getAccuracyPercent() {
        return AccuracyPercentage;
    }

    public int getNoOfObservations() {
        return NoOfObservations;
    }

    public int getNoOfSuccessfulObservations() {
        return NoOfSuccessfulObservations;
    }

    public int getNoOfFailedObservations() {
        return NoOfFailedObservations;
    }
}

package edu.metu.lngamesml.data;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.WithDrawnAgent;
import edu.metu.lngamesml.eval.classification.BasicClassificationEvaluator;
import edu.metu.lngamesml.utils.log.Logging;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Nov 15, 2010
 * Time: 11:03:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfidenceEstimator {

    private double[] Confidences;
    private double SamplingRatio = 50.00;

    public ConfidenceEstimator(int NoOfAgents) {
        Confidences = new double[NoOfAgents];
    }

    public double getSamplingRatio() {
        return SamplingRatio;
    }

    public void setSamplingRatio(double samplingRatio) {
        SamplingRatio = samplingRatio;
    }

    public Instances getSampledData(Instances trainingData) {
        Random rnd = new Random(1);
        Resample resampler = new Resample();
        Instances data = null;
        resampler.setSampleSizePercent(SamplingRatio);
        try {
            resampler.setInputFormat(trainingData);
        } catch (Exception e) {
            Logging.warning(e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            data = Filter.useFilter(trainingData, resampler);
        } catch (Exception e) {
            Logging.warning(e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return data;
    }

    public double estimateConfidence(Instances data, Agent agent) {
        double confidence = 0.0;
        BasicClassificationEvaluator bcEval = new BasicClassificationEvaluator();
        for (Instance instance : data) {
            int resClass = 0;
            try {
                resClass = (int) agent.getLearner().classifyInstance(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
            bcEval.addPerformanceObservation(resClass, instance);
        }
        confidence = bcEval.getAccuracyPercent() / 100;
        return confidence;
    }

    public double estimateConfidence(Instances data, WithDrawnAgent agent) {
        double confidence = 0.0;
        BasicClassificationEvaluator bcEval = new BasicClassificationEvaluator();
        for (Instance instance : data) {
            int resClass = 0;
            try {
                resClass = (int) agent.getLearner().classifyInstance(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
            bcEval.addPerformanceObservation(resClass, instance);
        }
        confidence = bcEval.getAccuracyPercent() / 100;
        return confidence;
    }

    public double[] estimateConfidences(Instances trainingData, ArrayList<Agent> Agents) {
        Instances sampledData = getSampledData(trainingData);
        int agentNo = 0;
        for (Agent agent : Agents) {
            BasicClassificationEvaluator bcEval = new BasicClassificationEvaluator();
            for (Instance instance : sampledData) {
                int resClass = 0;
                try {
                    resClass = (int) agent.getLearner().classifyInstance(instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bcEval.addPerformanceObservation(resClass, instance);
            }
            Confidences[agentNo] = bcEval.getAccuracyPercent() / 100;
            agentNo++;
        }
        return Confidences;
    }
}

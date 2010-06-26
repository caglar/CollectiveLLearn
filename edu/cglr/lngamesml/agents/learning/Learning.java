package edu.cglr.lngamesml.agents.learning;

import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Jun 8, 2010
 * Time: 4:28:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Learning {
    public double classifyInstace (Instance instance);
    public void trainOnInstance (Instance instance);
    public void buildClassifier (Instances instances);
    public double getConfidenceEstimation();
}

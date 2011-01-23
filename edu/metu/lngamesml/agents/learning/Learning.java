package edu.metu.lngamesml.agents.learning;

import edu.metu.lngamesml.agents.com.CategoricalComm;
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
    public CategoricalComm classifyInstance(Instance instance);

    public void trainOnInstance(Instance instance);

    public void trainClassifier(Instances instances);

    public double getConfidenceEstimation();
}

package edu.metu.lngamesml.agents;

import edu.metu.lngamesml.agents.com.CategoricalComm;
import weka.classifiers.AbstractClassifier;
import weka.core.Instance;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 13, 2010
 * Time: 7:02:54 AM
 * To change this template use File | Settings | File Templates.
 */

public abstract class Agent {

    public abstract CategoricalComm speak(Instance inst) throws Exception;

    public abstract void hear(CategoricalComm catComm);

    public abstract void setLearningType(LearnerTypes learningAlgorithm);

    public abstract void setId(int id);

    public abstract int getId();

    public abstract AbstractClassifier getLearner();
}

package edu.metu.lngamesml.agents;

import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.uci.ics.jung.graph.Graph;
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

    protected Graph<Agent, String> mGraph;

    public Agent(Graph<Agent, String> graph) {
        mGraph = graph;
    }

    public abstract CategoricalComm speak(Instance inst) throws Exception;

    public abstract void hear(CategoricalComm catComm);

    public abstract void setLearningType(LearnerTypes learningAlgorithm, String []options);

    public abstract void setId(int id);

    public abstract int getId();

    public abstract void forgetFocusedCat();

    public abstract AbstractClassifier getLearner();
}

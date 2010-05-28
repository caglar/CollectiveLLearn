package edu.cglr.lngamesml.agents;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 13, 2010
 * Time: 7:02:54 AM
 * To change this template use File | Settings | File Templates.
 */

public abstract class Agent {
    public abstract String speak (int instanceNo);
    public abstract void hear (int instanceNo, String category);
    public abstract void initLearner (LearnerTypes learningAlgorithm);
    public abstract void  setId(int id);
    public abstract int getId();
}

package edu.cglr.lngamesml.agents.learning;

import edu.cglr.lngamesml.agents.LearnerTypes;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 13, 2010
 * Time: 7:03:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class SupervisedLearner implements Learning {

    /**
    @TODO
        Add learning algorithms' wrapper here.
     */
    
    public SupervisedLearner (LearnerTypes learningAlgo) {
        
    }

    @Override
    public double classifyInstace(Instance instance) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void trainOnInstance(Instance instance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void buildClassifier(Instances instances) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getConfidenceEstimation() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

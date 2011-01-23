package edu.metu.lngamesml.agents;

import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.agents.learning.SupervisedLearning;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Oct 27, 2010
 * Time: 6:52:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class WithDrawnAgent {

    private SupervisedLearning LearningAlgorithm = null;
    private int AgentId = 0;

    public void setLearningType(LearnerTypes learningTechnique) {
        LearningAlgorithm = new SupervisedLearning(learningTechnique);
    }

    public void learnFromData(Instances insts) {
        if (LearningAlgorithm == null) {
            throw new NullPointerException("Learning Algorithm should be set before starting learning.");
        } else {
            LearningAlgorithm.trainClassifier(insts);
        }
    }

    public CategoricalComm classifyInstance(Instance inst) throws Exception {
        CategoricalComm catComm;
        if (LearningAlgorithm != null) {
            catComm = LearningAlgorithm.classifyInstance(inst);
        } else {
            throw new Exception("Please enter the LearnerType before starting the game!");
        }
        return catComm;
    }

    public int getAgentId() {
        return AgentId;
    }

    public void setAgentId(int agentId) {
        AgentId = agentId;
    }
}

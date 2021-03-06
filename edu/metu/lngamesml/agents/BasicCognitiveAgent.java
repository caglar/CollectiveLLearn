package edu.metu.lngamesml.agents;

import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.agents.learning.SupervisedLearning;
import edu.metu.lngamesml.agents.memory.ShortTermMemory;
import edu.metu.lngamesml.env.Objects;
import edu.uci.ics.jung.graph.Graph;
import weka.classifiers.AbstractClassifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Oct 24, 2010
 * Time: 2:07:15 AM
 * To change this template use File | Settings | File Templates.
 */

public class BasicCognitiveAgent extends Agent {

    private SupervisedLearning LearningAlgorithm = null;
    private ShortTermMemory STM = new ShortTermMemory();
    private Objects Objs = new Objects();
    private int id;

    public BasicCognitiveAgent(Graph<Agent, String> graph, LearnerTypes learnerType, String []options) {
        super(graph);
        setLearningType(learnerType, options);
    }

    public BasicCognitiveAgent(LearnerTypes learnerType, String []options) {
        super(null);
        setLearningType(learnerType, options);
    }

    public BasicCognitiveAgent(Graph<Agent, String> graph) {
        super(graph);
    }

    public BasicCognitiveAgent() {
        super(null);
    }

    @Override
    public void setLearningType(LearnerTypes learningTechnique, String []options) {
        LearningAlgorithm = new SupervisedLearning(learningTechnique, options);
    }

    public AbstractClassifier getLearner(){
        return LearningAlgorithm.getClassifier();
    }

    public void learnFromData(Instances insts) {
        if (LearningAlgorithm == null) {
            throw new NullPointerException("Learning Algorithm should be set before starting learning.");
        } else {
            LearningAlgorithm.trainClassifier(insts);
        }
    }

    public void forgetFocusedCat(){
        STM.resetMemory();
    }

    @Override
    public CategoricalComm speak(Instance inst) throws Exception {
        CategoricalComm catComm;
        if (!STM.getFocusedCategory().isEmpty()) {
            catComm = STM.getCatComm();
        } else if (LearningAlgorithm != null) {
            catComm = LearningAlgorithm.classifyInstance(inst);
            STM.setCatComm(catComm);
        } else {
            throw new Exception("Please enter the LearnerType before starting the game!");
        }
        return catComm;
    }


    public CategoricalComm speak(Instance inst, double weight) throws Exception {
        CategoricalComm catComm;
        if (!STM.getFocusedCategory().isEmpty()) {
            catComm = STM.getCatComm();
        } else if (LearningAlgorithm != null) {
            catComm = LearningAlgorithm.classifyInstance(inst, weight);
            STM.setCatComm(catComm);
        } else {
            throw new Exception("Please enter the LearnerType before starting the game!");
        }
        return catComm;
    }

    public void hear(CategoricalComm catComm) {
        STM.setCatComm(catComm);
    }

    public void prepareForNewGame() {
        STM.resetMemory();
    }

    public int getFocusedCategory() {
        return STM.getFocusedCategoryId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Category is " + STM.getFocusedCategory() + " Agent id: " + this.id;
    }
}

package edu.metu.lngamesml.agents;


import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.agents.learning.SupervisedLearning;
import edu.metu.lngamesml.agents.memory.LongTermMemory;
import edu.metu.lngamesml.agents.memory.ShortTermMemory;
import edu.uci.ics.jung.graph.Graph;
import weka.classifiers.AbstractClassifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 16, 2010
 * Time: 11:43:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class SupervisedCognitiveAgent extends Agent {

    private SupervisedLearning LearningAlgorithm = null;
    private ShortTermMemory STM = new ShortTermMemory();
    private LongTermMemory LTM = new LongTermMemory();
    private int id;

    public SupervisedCognitiveAgent(LearnerTypes learnerType, String []options) {
        super(null);
        setLearningType(learnerType, options);
    }

    public SupervisedCognitiveAgent(Graph<Agent, String> graph, LearnerTypes learnerType, String []options) {
        super(graph);
        setLearningType(learnerType, options);
    }

    public SupervisedCognitiveAgent(Graph<Agent, String> graph) {
        super(graph);
    }

    public SupervisedCognitiveAgent() {
        super(null);
    }

    @Override
    public void setLearningType(LearnerTypes learningTechnique, String []options) {
        LearningAlgorithm = new SupervisedLearning(learningTechnique, options);
    }

    public void learnFromData(Instances insts) {
        if (LearningAlgorithm == null) {
            throw new NullPointerException("Learning Algorithm should be set before starting learning.");
        } else {
            LearningAlgorithm.trainClassifier(insts);
        }
    }

    @Override
    public CategoricalComm speak(Instance inst) throws Exception {

        CategoricalComm catComm;
        if (!STM.getFocusedCategory().isEmpty()) {
            catComm = new CategoricalComm();
            catComm.setComStatement(STM.getFocusedCategory());
            catComm.setStatementNo(STM.getFocusedCategoryId());
        } else if (LearningAlgorithm != null) {
            catComm = LearningAlgorithm.classifyInstance(inst);
        } else {
            throw new Exception("Please enter the LearnerType before starting the game!");
        }
        return catComm;
    }

    @Override
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

    public void forgetFocusedCat(){
        STM.resetMemory();
    }

    @Override
    public AbstractClassifier getLearner() {
        return LearningAlgorithm.getClassifier();
    }

    public void setId(int id) {
        this.id = id;
    }

    public SupervisedLearning getLearningAlgorithm() {
        return LearningAlgorithm;
    }

    @Override
    public String toString() {
        return "Category is " + STM.getFocusedCategory() + " Agent id: " + this.id;
    }
}

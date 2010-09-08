package edu.cglr.lngamesml.agents;


import edu.cglr.lngamesml.agents.learning.SupervisedLearner;
import edu.cglr.lngamesml.agents.lexicon.Lexicon;
import edu.cglr.lngamesml.agents.lexicon.SimpleLexicon;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 16, 2010
 * Time: 11:43:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleAgent extends Agent
{
    private SupervisedLearner LearningAlgorithm;
    private String TrainingDataset;
    private String TestDataset;
    private String FocusedCategory = "";
    private Lexicon Lex;
    private int id;
    
    public SimpleAgent (LearnerTypes learnerType, String trainingDataset, String testDataset)
    {
       Lex = new SimpleLexicon();
       initLearner(learnerType);
       setTrainingDataset(trainingDataset );
       setTestDataset(testDataset);
    }

    public SimpleAgent() { }

    @Override
    public void initLearner(LearnerTypes learningAlgorithm) {
        SupervisedLearner learner = new SupervisedLearner(learningAlgorithm);
    }
    
    public SupervisedLearner getLearningAlgorithm() {
        return LearningAlgorithm;
    }

    public void setLearningAlgorithm(SupervisedLearner learningAlgorithm) {
        LearningAlgorithm = learningAlgorithm;
    }

    public String getTrainingDataset() {
        return TrainingDataset;
    }

    public void setTrainingDataset(String trainingDataset) {
        if (!trainingDataset.isEmpty()) {
            TrainingDataset = trainingDataset;
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "Training dataset is empty and it was tried to be assigned");
        }
    }

    public String getTestDataset() {
        return TestDataset;
    }

    public void setTestDataset(String testDataset) {
        if (!testDataset.isEmpty()) {
            TestDataset = testDataset;
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "Test dataset is empty and it was tried to be assigned");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String speak(int instanceNo) {
        String category = Lex.getWord(instanceNo);
        return category;
    }

    @Override
    public void hear(int instanceNo, String category) {
        Lex.setWord(instanceNo, category);
    }

    @Override
    public String toString () {
        return "Category is " + FocusedCategory + " Agent id: " + this.id; 
    }
}

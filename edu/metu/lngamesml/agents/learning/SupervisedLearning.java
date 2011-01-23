package edu.metu.lngamesml.agents.learning;

import edu.metu.lngamesml.agents.LearnerTypes;
import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.utils.log.Logging;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.KStar;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;

import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 13, 2010
 * Time: 7:03:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class SupervisedLearning implements Learning {
    
    private LearnerTypes LearningAlgo;
    private AbstractClassifier MClassifier = null;
    private double Confidence = 0.0;

    /**
     * @TODO Add learning algorithms' wrapper here.
     */

    public SupervisedLearning(LearnerTypes learningAlgo) {
        LearningAlgo = learningAlgo;
        initClassifier();
    }

    public SupervisedLearning() {
        LearningAlgo = null;
    }

    public LearnerTypes getLearningAlgo() {
        return LearningAlgo;
    }

    public void setLearningAlgo(LearnerTypes learningAlgo) {
        LearningAlgo = learningAlgo;
        initClassifier();
    }

    private void initClassifier() {
        String[] options;
        if (LearningAlgo == LearnerTypes.C45) {
            MClassifier = new J48();
            options = new String[1];
            options[0] = "-U"; // unpruned tree
            ((J48) MClassifier).setUseLaplace(true);
            try {
                MClassifier.setOptions(options);
            } catch (Exception e) {
                Logging.log(Level.WARNING, e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else if (LearningAlgo == LearnerTypes.NBAYES) {
            MClassifier = new NaiveBayes();
            options = new String[1];
            options[0] = "-no-cv"; // unpruned tree
            ((NaiveBayes) MClassifier).setUseKernelEstimator(true);
            try {
                MClassifier.setOptions(options);
            } catch (Exception e) {
                Logging.log(Level.WARNING, e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else if (LearningAlgo == LearnerTypes.KNN) {
            MClassifier = new KStar();
            ((KStar) MClassifier).setEntropicAutoBlend(true);
        } else if (LearningAlgo == LearnerTypes.SMO) {
            MClassifier = new SMO();
            ((SMO) MClassifier).setBuildLogisticModels(true);
        }
    }

    @Override
    public CategoricalComm classifyInstance(Instance instance) {
        String category = "";
        Confidence = 0.0;
        double classNo = 0;
        double[] distForInst;
        CategoricalComm catComm = new CategoricalComm();
        try {
            classNo = MClassifier.classifyInstance(instance);
            category = instance.classAttribute().value((int)classNo);
            //category = instance.stringValue(instance.classIndex());
            distForInst = MClassifier.distributionForInstance(instance);
            Confidence = distForInst[(int) classNo];
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catComm.setComStatement(category);
        catComm.setConfidence(Confidence);
        catComm.setStatementNo((int) classNo);
        return catComm;
    }

    public CategoricalComm classifyInstance(Instance instance, double weight) {
        String category = "";
        Confidence = 0.0;
        double classNo = 0;
        double[] distForInst;
        CategoricalComm catComm = new CategoricalComm();
        try {
            classNo = MClassifier.classifyInstance(instance);
            category = instance.classAttribute().value((int)classNo);
            //category = instance.stringValue(instance.classIndex());
            distForInst = MClassifier.distributionForInstance(instance);
            Confidence = weight * distForInst[(int) classNo];
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catComm.setComStatement(category);
        catComm.setConfidence(Confidence);
        catComm.setStatementNo((int) classNo);
        return catComm;
    }

    @Override
    public void trainOnInstance(Instance instance) {
    }

    @Override
    public void trainClassifier(Instances instances) {
        try {
            MClassifier.buildClassifier(instances);
        } catch (Exception e) {
            Logging.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public AbstractClassifier getClassifier() {
        return MClassifier;
    }

    @Override
    public double getConfidenceEstimation() {
        return Confidence;
    }
}

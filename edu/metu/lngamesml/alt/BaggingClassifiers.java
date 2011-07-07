package edu.metu.lngamesml.alt;

import edu.metu.lngamesml.agents.LearnerTypes;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.exception.UninitializedClassifierException;
import edu.metu.lngamesml.utils.log.Logging;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.RBFNetwork;
import weka.classifiers.lazy.KStar;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.J48;
import edu.metu.lngamesml.eval.classification.BasicClassificationEvaluator;
import weka.core.Instance;
import weka.core.Instances;

import java.security.PrivilegedActionException;
import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 6/26/11
 * Time: 7:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaggingClassifiers {

    private Bagging Bagging = null;
    private double Accuracy = 0.0;

    private AbstractClassifier getClassifier(LearnerTypes lType) {
        AbstractClassifier classifier = null;
        String[] options = null;
        switch (lType) {
            case C45:
                classifier = new J48();
                options = new String[1];
                options[0] = "-U"; // unpruned tree
                ((J48) classifier).setUseLaplace(true);
                try {
                    classifier.setOptions(options);
                } catch (Exception e) {
                    Logging.log(Level.WARNING, e.getMessage());
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                break;
            case KNN:
                classifier = new KStar();
                ((KStar) classifier).setEntropicAutoBlend(true);
                break;
            case NBAYES:
                classifier = new NaiveBayes();
                break;
            case RBFNET:
                classifier = new RBFNetwork();
                options = new String[]{
                        "-B", "2"
                };
                try {
                    classifier.setOptions(options);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case SVM:
                classifier = new LibSVM();
                if (options != null) {
                    try {
                        classifier.setOptions(options);
                    } catch (Exception e) {
                        Logging.log(Level.WARNING, e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    options = new String[]{
                            "-S", "0",
                            "-K", "2",
                            "-D", "3",
                            "-G", "0.0010",
                            "-R", "0.0",
                            "-N", "0.5",
                            "-M", "40.0",
                            "-C", "1.0",
                            "-E", "0.0010",
                            "-P", "0.1",
                            "-H", "0",
                            "-model", System.getProperty("user.dir")
                    };
                    try {
                        classifier.setOptions(options);
                    } catch (Exception e) {
                        Logging.log(Level.WARNING, e.getMessage());
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
        return classifier;
    }

    public void trainAlgorithm(String trainingDataset, LearnerTypes lType, int noOfAgents, int samplingRatio) {
        Bagging = new Bagging();
        Bagging.setClassifier(getClassifier(lType));
        Bagging.setBagSizePercent(samplingRatio);
        try {
            Bagging.buildClassifier(FileLoaderFactory.loadFile(trainingDataset));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testAlgorithm(String testDataset) throws Exception {
        Instances testInstances = FileLoaderFactory.loadFile(testDataset);
        int noOfTestInstances = testInstances.numInstances();
        BasicClassificationEvaluator bcEval = new BasicClassificationEvaluator();
        Instance currentContext;
        if (Bagging == null) {
            throw new UninitializedClassifierException("bagging algorithm should be initialized!");
        }
        double currentClass = 0;
        for (int i = 0; i < noOfTestInstances; i++) {
            currentContext = testInstances.get(i);
            try {
                currentClass = Bagging.classifyInstance(currentContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
            bcEval.addPerformanceObservation((int) currentClass, currentContext);
        }
        Accuracy = bcEval.getAccuracyPercent();
    }

    public double getAccuracy() {
        return Accuracy;
    }
}

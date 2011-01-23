package edu.metu.lngamesml.games.langgame.conflanggame;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.BasicCognitiveAgent;
import edu.metu.lngamesml.agents.LearnerTypes;
import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.eval.classification.BasicClassificationEvaluator;
import edu.metu.lngamesml.games.Convergence;
import edu.metu.lngamesml.games.LGame;
import edu.metu.lngamesml.utils.log.Logging;
import edu.metu.lngamesml.utils.random.MersenneTwister;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AdaBoostM1;
import weka.core.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Nov 3, 2010
 * Time: 6:55:33 AM
 * To change this template use File | Settings | File Templates.
 */

public class AdaBoostConfidenceLGame extends AdaBoostM1
                                        implements LGame {

    /**
     * Max num iterations tried to find classifier with non-zero error.
     */
    private static int MAX_NUM_RESAMPLING_ITERATIONS = 10;
    
    protected int NoOfAgents;
    protected int SamplingRatio;
    protected ArrayList <Agent> Agents = new ArrayList<Agent>();
    private MersenneTwister MRnd = new MersenneTwister(System.nanoTime());
    private LearnerTypes LType;

    public AdaBoostConfidenceLGame(){
        super();
    }

    public void setLTypes(LearnerTypes LTypes) {
        this.LType = LTypes;
    }

    public AdaBoostConfidenceLGame(int noOfAgents){
        super();
        NoOfAgents = noOfAgents;
    }

    private void initializeAgents() {
        BasicCognitiveAgent bca;
        for (int i = 0; i < NoOfAgents; i++) {
            bca = new BasicCognitiveAgent();
            bca.setId(i);
            bca.setLearningType(LType);
            if (Agents.size() <= i ){
                Agents.add(bca);
            } else {
                Agents.set(i, bca);
            }
        }
    }
    
    /**
     * Boosting method.
     *
     * @param data the training data to be used for generating the
     *             boosted classifier.
     * @throws Exception if the classifier could not be built successfully
     */
    @Override
    public void buildClassifier(Instances data) throws Exception {
        //super.buildClassifier(data);
        // can classifier handle the data?
        getCapabilities().testWithFail(data);
        initializeAgents();
        // remove instances with missing class
        data = new Instances(data);
        data.deleteWithMissingClass();

        // only class? -> build ZeroR model
        if (data.numAttributes() == 1) {
            System.err.println(
                    "Cannot build model (only class attribute present in data!), "
                            + "using ZeroR model instead!");
            m_ZeroR = new weka.classifiers.rules.ZeroR();
            m_ZeroR.buildClassifier(data);
            return;
        } else {
            m_ZeroR = null;
        }

        m_NumClasses = data.numClasses();
        if ((!m_UseResampling) &&
                (Agents.get(0).getLearner() instanceof WeightedInstancesHandler)) {
            buildClassifierWithWeights(data);
        } else {
            buildClassifierUsingResampling(data);
        }
    }

    /**
     * Boosting method. Boosts using resampling
     *
     * @param data the training data to be used for generating the
     *             boosted classifier.
     * @throws Exception if the classifier could not be built successfully
     */
    @Override
    protected void buildClassifierUsingResampling(Instances data)
            throws Exception {

        Instances trainData, sample, training;
        double epsilon, reweight, sumProbs;
        Evaluation evaluation;
        int numInstances = data.numInstances();
        Random randomInstance = new Random(m_Seed);
        int resamplingIterations = 0;

        // Initialize data
        m_Betas = new double[NoOfAgents];
        m_NumIterationsPerformed = 0;
        // Create a copy of the data so that when the weights are diddled
        // with it doesn't mess up the weights for anyone else
        training = new Instances(data, 0, numInstances);
        sumProbs = training.sumOfWeights();
        for (int i = 0; i < training.numInstances(); i++) {
            training.instance(i).setWeight(training.instance(i).
                    weight() / sumProbs);
        }

        // Do boostrap iterations
        for (m_NumIterationsPerformed = 0; m_NumIterationsPerformed < NoOfAgents; m_NumIterationsPerformed++) {
            if (m_Debug) {
                System.err.println("Training classifier " + (m_NumIterationsPerformed + 1));
            }

            // Select instances to train the classifier on
            if (m_WeightThreshold < 100) {
                trainData = selectWeightQuantile(training,
                        (double) m_WeightThreshold / 100);
            } else {
                trainData = new Instances(training);
            }

            // Resample
            resamplingIterations = 0;
            double[] weights = new double[trainData.numInstances()];
            for (int i = 0; i < weights.length; i++) {
                weights[i] = trainData.instance(i).weight();
            }
            do {
                sample = trainData.resampleWithWeights(randomInstance, weights);

                // Build and evaluate classifier
                Agents.get(m_NumIterationsPerformed).getLearner().buildClassifier(sample);
                evaluation = new Evaluation(data);
                evaluation.evaluateModel(Agents.get(m_NumIterationsPerformed).getLearner(),
                        training);
                epsilon = evaluation.errorRate();
                resamplingIterations++;
            } while (Utils.eq(epsilon, 0) &&
                    (resamplingIterations < MAX_NUM_RESAMPLING_ITERATIONS));

            // Stop if error too big or 0
            if (Utils.grOrEq(epsilon, 0.5) || Utils.eq(epsilon, 0)) {
                if (m_NumIterationsPerformed == 0) {
                    m_NumIterationsPerformed = 1; // If we're the first we have to to use it
                }
                break;
            }

            // Determine the weight to assign to this model
            m_Betas[m_NumIterationsPerformed] = Math.log((1 - epsilon) / epsilon);
            reweight = (1 - epsilon) / epsilon;
            if (m_Debug) {
                System.err.println("\terror rate = " + epsilon
                        + "  beta = " + m_Betas[m_NumIterationsPerformed]);
            }

            // Update instance weights
            setWeights(training, reweight);
        }
    }

    /**
     * Sets the weights for the next iteration.
     *
     * @param training the training instances
     * @param reweight the reweighting factor
     * @throws Exception if something goes wrong
     */
    @Override
    protected void setWeights(Instances training, double reweight)
            throws Exception {

        double oldSumOfWeights, newSumOfWeights;

        oldSumOfWeights = training.sumOfWeights();
        Enumeration enu = training.enumerateInstances();
        while (enu.hasMoreElements()) {
            Instance instance = (Instance) enu.nextElement();
            if (!Utils.eq(Agents.get(m_NumIterationsPerformed).getLearner().classifyInstance(instance),
                    instance.classValue()))
                instance.setWeight(instance.weight() * reweight);
        }

        // Renormalize weights
        newSumOfWeights = training.sumOfWeights();
        enu = training.enumerateInstances();
        while (enu.hasMoreElements()) {
            Instance instance = (Instance) enu.nextElement();
            instance.setWeight(instance.weight() * oldSumOfWeights
                    / newSumOfWeights);
        }
    }

    /**
     * Boosting method. Boosts any
     * classifier that can handle weighted
     * instances.
     *
     * @param data the training data to be used for generating the
     *             boosted classifier.
     * @throws Exception if the classifier could not be built successfully
     */
    @Override
    protected void buildClassifierWithWeights(Instances data)
            throws Exception {

        Instances trainData, training;
        double epsilon, reweight;
        Evaluation evaluation;
        int numInstances = data.numInstances();
        Random randomInstance = new Random(m_Seed);

        // Initialize data
        m_Betas = new double[NoOfAgents];
        m_NumIterationsPerformed = 0;

        // Create a copy of the data so that when the weights are diddled
        // with it doesn't mess up the weights for anyone else
        training = new Instances(data, 0, numInstances);

        // Do boostrap iterations
        for (m_NumIterationsPerformed = 0; m_NumIterationsPerformed < NoOfAgents;
             m_NumIterationsPerformed++) {
            if (m_Debug) {
                System.err.println("Training classifier " + (m_NumIterationsPerformed + 1));
            }
            // Select instances to train the classifier on
            if (m_WeightThreshold < 100) {
                trainData = selectWeightQuantile(training,
                        (double) m_WeightThreshold / 100);
            } else {
                trainData = new Instances(training, 0, numInstances);
            }

            // Build the classifier
            if (Agents.get(m_NumIterationsPerformed).getLearner() instanceof Randomizable)
                ((Randomizable) Agents.get(m_NumIterationsPerformed).getLearner()).setSeed(randomInstance.nextInt());
            Agents.get(m_NumIterationsPerformed).getLearner().buildClassifier(trainData);

            // Evaluate the classifier
            evaluation = new Evaluation(data);
            evaluation.evaluateModel(Agents.get(m_NumIterationsPerformed).getLearner(), training);
            epsilon = evaluation.errorRate();

            // Stop if error too small or error too big and ignore this model
            if (Utils.grOrEq(epsilon, 0.5) || Utils.eq(epsilon, 0)) {
                if (m_NumIterationsPerformed == 0) {
                    m_NumIterationsPerformed = 1; // If we're the first we have to to use it
                }
                break;
            }
            // Determine the weight to assign to this model
            m_Betas[m_NumIterationsPerformed] = Math.log((1 - epsilon) / epsilon);
            reweight = (1 - epsilon) / epsilon;
            if (m_Debug) {
                System.err.println("\terror rate = " + epsilon
                        + "  beta = " + m_Betas[m_NumIterationsPerformed]);
            }
            // Update instance weights
            setWeights(training, reweight);
        }
    }

    /**
     * Calculates the class membership probabilities for the given test instance.
     *
     * @param instance the instance to be classified
     * @return predicted class probability distribution
     * @throws Exception if instance could not be classified
     *                   successfully
     */
    @Override
    public double[] distributionForInstance(Instance instance)
            throws Exception {

        // default model?
        if (m_ZeroR != null) {
            return m_ZeroR.distributionForInstance(instance);
        }

        if (m_NumIterationsPerformed == 0) {
            throw new Exception("No model built");
        }
        double[] sums = new double[instance.numClasses()];

        if (m_NumIterationsPerformed == 1) {
            return Agents.get(0).getLearner().distributionForInstance(instance);
        } else {
            for (int i = 0; i < m_NumIterationsPerformed; i++) {
                sums[(int) Agents.get(i).getLearner().classifyInstance(instance)] += m_Betas[i];
            }
            return Utils.logs2probs(sums);
        }
    }

    public int getNoOfAgents() {
        return NoOfAgents;
    }

    public void setNoOfAgents(int noOfAgents) {
        NoOfAgents = noOfAgents;
    }

    public int getSamplingRatio() {
        return SamplingRatio;
    }

    public void setSamplingRatio(int samplingRatio) {
        SamplingRatio = samplingRatio;
    }

    @Override
    public void createAgents(String trainingDataset) {
        Instances trainingData = FileLoaderFactory.loadFile(trainingDataset);
//        setUseResampling(true);
        try {
            buildClassifier(trainingData);
        } catch (Exception e) {
            Logging.warning(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void setAgentsOnGraph(int x, int y) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    protected Agent getRandomAgent() {
        int agentNo = MRnd.nextInt(NoOfAgents);
        return Agents.get(agentNo);
    }

    protected void prepareForNewGame() {
        for (Agent bca : Agents) {
            ((BasicCognitiveAgent) bca).prepareForNewGame();
        }
    }

    @Override
    public void playGames(String testDataset) throws Exception {
        Instances testInstances = FileLoaderFactory.loadFile(testDataset);
        BasicCognitiveAgent bcaAgent1;
        BasicCognitiveAgent bcaAgent2;
        int noOfTestInstances = testInstances.numInstances();
        BasicClassificationEvaluator bcEval = new BasicClassificationEvaluator();
        Convergence convergence = new Convergence(NoOfAgents);
        Instance currentContext;
        CategoricalComm currentClassVal = null;
        System.out.println("Play games!!");
        for (int i = 0; i < noOfTestInstances; i++) {
            convergence.emptyTable();
            currentContext = testInstances.get(i);
           while (!convergence.isConverged(Agents, currentContext)) {
                bcaAgent1 = (BasicCognitiveAgent) getRandomAgent();
                bcaAgent2 = (BasicCognitiveAgent) getRandomAgent();
                while (bcaAgent1.equals(bcaAgent2)) {
                    bcaAgent2 = (BasicCognitiveAgent) getRandomAgent();
                }
                CategoricalComm cat1 = bcaAgent1.speak(currentContext, m_Betas[bcaAgent1.getId()]);
                CategoricalComm cat2 = bcaAgent2.speak(currentContext, m_Betas[bcaAgent2.getId()]);

                if (cat1.getConfidence() > cat2.getConfidence()) {
                    if (!cat1.equals(cat2)) {
                        convergence.balanceTable(cat1, cat2);
                        Agents.get(bcaAgent2.getId()).hear(cat1);
                    }
                } else if (cat1.getConfidence() == cat2.getConfidence()) {
                    if (!cat1.equals(cat2)) {
                        int speakerCatNo = ((new Random(System.nanoTime()).nextInt()) % 2);
                        if (speakerCatNo == 0) {
                            convergence.balanceTable(cat1, cat2);
                            Agents.get(bcaAgent2.getId()).hear(cat1);
                        } else {
                            convergence.balanceTable(cat2, cat1);
                            Agents.get(bcaAgent1.getId()).hear(cat2);
                        }
                    }
                } else {
                    if (!cat1.equals(cat2)) {
                        convergence.balanceTable(cat2, cat1);
                        Agents.get(bcaAgent1.getId()).hear(cat2);
                    }
                }
            }
            currentClassVal = convergence.getConvergedCategory();
            bcEval.addPerformanceObservation(currentClassVal, currentContext);
            prepareForNewGame();
        }
        System.out.println(bcEval.getPerformanceMetrics());
    }
}

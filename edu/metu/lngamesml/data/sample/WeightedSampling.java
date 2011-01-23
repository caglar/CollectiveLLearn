package edu.metu.lngamesml.data.sample;

import edu.metu.lngamesml.core.InstancesList;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.data.Partitioner;
import edu.metu.lngamesml.utils.MiscUtils;
import edu.metu.lngamesml.utils.log.Logging;
import edu.metu.lngamesml.utils.random.WeightedRandomNumberGen;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 30, 2010
 * Time: 7:12:33 PM
 * To change this template use File | Settings | File Templates.
 */

public class WeightedSampling implements Partitioner {

    private String Dataset;
    private int NoOfPartitions;
    private boolean SampleWithOrWithoutReplace = false;
    private double SamplingRatio = 50;
    private ClassHistogram Chistogram;
    private ArrayList<Attribute> AList;
    private String Relation = "";
    private long Seed = 1;

    public WeightedSampling(String dataset, int noOfPartitions) {
        Dataset = dataset;
        NoOfPartitions = noOfPartitions;
        AList = new ArrayList<Attribute>();
    }

    public WeightedSampling() {
        Dataset = null;
        NoOfPartitions = -1;
        AList = new ArrayList<Attribute>();
    }

    private double[] getClassHist(Instances insts) {
        double[] classHists;
        Chistogram.addInstances(insts);
        classHists = Chistogram.getClassHist();
        return classHists;
    }

    public double getSamplingRatio() {
        return SamplingRatio;
    }

    public void setSamplingRatio(double samplingRatio) throws Exception {
        if (samplingRatio <= 100) {
            SamplingRatio = samplingRatio;
        } else {
            throw new Exception("Sampling ratio can't be larger than 100.");
        }
    }

    private InstancesList[] generateInstanceGroups(int noOfClasses, int noOfInstances) {
        InstancesList[] instanceGroups = new InstancesList[NoOfPartitions];
        String relationPart = "";
        for (int i = 0; i < instanceGroups.length; i++) {
            relationPart = Relation + "_part" + i;
            instanceGroups[i] = new InstancesList(noOfClasses, relationPart, AList, noOfInstances);
        }
        return instanceGroups;
    }

    private InstancesList[] sampleWithReplacement(Instances instances, int numberOfInstancesPerBucket) {
        int noOfInstances = instances.numInstances();
        int noOfClasses = instances.numClasses();
        InstancesList[] instanceGroups = generateInstanceGroups(noOfClasses, noOfInstances);
        WeightedRandomNumberGen wrng = new WeightedRandomNumberGen(getClassHist(instances), Seed);
        int rndClass = 0;
        for (int i = 0; i < NoOfPartitions; i++) {
            ClassHistogram cHist = null;
            try {
                cHist = (ClassHistogram) MiscUtils.deepCopy(Chistogram);
            } catch (Exception e) {
                Logging.log(Level.SEVERE, e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                //System.exit(-1);
                throw new RuntimeException("Can't copy the ClassHistogram Object");
            }

            for (int j = 0; j < numberOfInstancesPerBucket; j++) {
                rndClass = wrng.next();
                Instance inst = cHist.getRandomInstance(rndClass);
                instanceGroups[i].add(inst);
                double[] hist = cHist.getClassHist();
                wrng.reInitWRNG(hist);
            }
        }
        return instanceGroups;
    }

    private InstancesList[] sampleWithoutReplacement(Instances instances, int numberOfInstancesPerBucket) {
        int noOfInstances = instances.numInstances();
        int noOfClasses = instances.numClasses();
        InstancesList[] instanceGroups = generateInstanceGroups(noOfClasses, noOfInstances);
        WeightedRandomNumberGen wrng = new WeightedRandomNumberGen(getClassHist(instances), Seed);
        int rndClass = 0;
        for (int i = 0; i < NoOfPartitions; i++) {
            ClassHistogram cHist = null;
            try {
                cHist = (ClassHistogram) MiscUtils.deepCopy(Chistogram);
            } catch (Exception e) {
                Logging.log(Level.SEVERE, e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                //System.exit(-1);,
                throw new RuntimeException("Can't copy the ClassHistogram Object.");
            }

            for (int j = 0; j < numberOfInstancesPerBucket; j++) {
                rndClass = wrng.next();
                if (cHist.getNumberOfInstanceInClass(rndClass) > 0) {
                    Instance inst = cHist.fetchRandomInstance(rndClass);
                    if (inst != null) {
                        instanceGroups[i].add(inst);
                    }
                    double[] hist = cHist.getClassHist();
                    wrng.reInitWRNG(hist);
                } else {
                    j--;
                }
            }
        }
        return instanceGroups;
    }

    private InstancesList[] doSampling(Instances instances) {
        int noOfInstances = instances.numInstances();
        double samplePercentage = SamplingRatio / 100;
        double numberOfInstancesPerBucket = noOfInstances * samplePercentage;
        System.out.println("Sampling ratio: " + samplePercentage);
        System.out.println("Number of instance per bucket:" + numberOfInstancesPerBucket);
        InstancesList[] instanceGroups;

        Relation = instances.relationName();
        AList = Collections.list(instances.enumerateAttributes());
        AList.add(instances.classIndex(), instances.attribute(instances.classIndex()));
        Chistogram = new ClassHistogram(instances.numClasses(), AList, noOfInstances);

        if (SampleWithOrWithoutReplace) {
            instanceGroups = sampleWithReplacement(instances, (int) numberOfInstancesPerBucket);
        } else {
            instanceGroups = sampleWithoutReplacement(instances, (int) numberOfInstancesPerBucket);
        }
        return instanceGroups;
    }

    public InstancesList[] partitionDataset() {
        InstancesList result[] = null;
        if (!Dataset.isEmpty() && NoOfPartitions != -1) {
            Instances data = FileLoaderFactory.loadFile(Dataset);
            result = doSampling(data);
        } else {
            throw new NullPointerException("Dataset and Number of Partitions are null!");
        }
        return result;
    }

    public InstancesList[] partitionDataset(String dataset, int noOfPartitions) {
        Dataset = dataset;
        NoOfPartitions = noOfPartitions;
        InstancesList result[];
        Instances data = FileLoaderFactory.loadFile(dataset);
        result = doSampling(data);
        return result;
    }

    public ArrayList<Attribute> getAList() {
        return AList;
    }

    public long getSeed() {
        return Seed;
    }

    public void setSeed(long seed) {
        Seed = seed;
    }

    public String getRelation() {
        return Relation;
    }
}

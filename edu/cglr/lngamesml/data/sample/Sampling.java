package edu.cglr.lngamesml.data.sample;

import edu.cglr.lngamesml.core.InstancesList;
import edu.cglr.lngamesml.data.FileLoaderFactory;
import edu.cglr.lngamesml.utils.random.WeightedRandomNumberGen;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 30, 2010
 * Time: 7:12:33 PM
 * To change this template use File | Settings | File Templates.
 */

public class Sampling {
    private String Dataset;
    private int NoOfPartitions;
    ClassHistogram Chistogram;
    private ArrayList<Attribute> AList;
    private String Relation = "";

    public Sampling (String dataset, int noOfPartitions) {
        Dataset = dataset;
        NoOfPartitions = noOfPartitions;
        AList = new ArrayList<Attribute>();
    }

    private double[] getClassHist (Instances insts) {
        double []classHists = new double[insts.numClasses()];
        for (Instance inst : insts) {
            classHists[(int)inst.classValue()]++;
            Chistogram.addInstance((int)inst.classValue(), inst);
        }
        return classHists;
    }

    private InstancesList[] doSampling (Instances instances) {
        int noOfInstances = instances.numInstances();
        Relation = instances.relationName();
        for (int i = 0; i < instances.numAttributes(); i++) {
            AList.add(instances.attribute(i));
        }
        int noOfInstancesPerBucket = instances.numInstances() / NoOfPartitions;
        Chistogram = new ClassHistogram(instances.numClasses());
        int currentClass;
        InstancesList[]instanceGroups = new InstancesList[NoOfPartitions];
        for (int i = 0; i < instanceGroups.length; i++) {
            instanceGroups [i] = new InstancesList(instances.numClasses());
        }
        WeightedRandomNumberGen wrng = new WeightedRandomNumberGen(getClassHist(instances));
        int countBucketSize = 0;
        int countBucketNumber = 0;
        int rndClass = 0;
        for (Instance inst: instances) {
            currentClass = wrng.next();
            if (countBucketSize == noOfInstancesPerBucket && countBucketNumber != NoOfPartitions - 1) {
                countBucketSize = 0;
                countBucketNumber++;
            }
            countBucketSize++;
            rndClass = wrng.next();
            instanceGroups[countBucketNumber].add(Chistogram.getRandomInstance(rndClass));
        }
        return instanceGroups;
    }

    public InstancesList[] partitionDataset () {
        Instances data = FileLoaderFactory.loadFile(Dataset);
        InstancesList result[] = doSampling(data);
        if (Dataset == null || Dataset.length() == 0) {
            throw new NullPointerException("No dataset found to partition!");
        }
        return result;
    }

    public ArrayList<Attribute> getAList() {
        return AList;
    }

    public String getRelation() {
        return Relation;
    }
}

package edu.metu.lngamesml.core;

import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 20, 2010
 * Time: 4:42:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassBins {

    public int NoOfClasses;
    private int ClusterInstancesRatio;
    public Instances ClassBins[];

    public ClassBins (int noOfClasses) {
        NoOfClasses = noOfClasses;
        ClusterInstancesRatio = 2;
    }

    public void createBins(Instances instances) {
        if (instances == null || instances.numInstances() == 0) {
            throw new NullPointerException("Instances for hi can't be empty!");
        }
        int noOfClasses = instances.numClasses();
        ArrayList attList = Collections.list(instances.enumerateAttributes());
        attList.add(instances.attribute(instances.classIndex()));
        int capacity = instances.size();
        int classIndex = instances.classIndex();
        ClassBins = new Instances[noOfClasses];
        for (int i = 0; i < noOfClasses; i++) {
            ClassBins[i] = new Instances("ClassBins", attList, capacity);
            if (ClassBins[i].classIndex() == -1)
                ClassBins[i].setClassIndex(classIndex);

        }

        for (Instance inst : instances) {
            ClassBins[(int) inst.classValue()].add(inst);
        }
    }

    public int getNoOfClasses() {
        return NoOfClasses;
    }

    public void addInstance(Instance inst, int classNo) {
        ClassBins[classNo].add(inst);
    }

    public int getClusterInstancesRatio() {
        return ClusterInstancesRatio;
    }

    public void setClusterInstancesRatio(int clusterInstancesRatio) {
        ClusterInstancesRatio = clusterInstancesRatio;
    }

    public void setNoOfClasses(int noOfClasses) {
        NoOfClasses = noOfClasses;
    }

    public Instances getInstancesForCluster(int classNo) {
        return ClassBins[classNo];
    }
}


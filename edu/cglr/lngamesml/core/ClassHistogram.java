package edu.cglr.lngamesml.core;

import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 20, 2010
 * Time: 4:42:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassHistogram {


    public Hashtable <Integer, Instances>InstsClasses;
    public int ClassesInstsMap[][];
    public int NoOfClasses;
    public int NoOfClusters;
    private int ClusterInstancesRatio;
    public ArrayList <Integer>BestClassesForClusters;

    public ClassHistogram (int noOfClasses, int noOfClusters) {
        NoOfClasses = noOfClasses;
        NoOfClusters = noOfClusters;
        ClusterInstancesRatio = 2;
        ClassesInstsMap = new int[noOfClusters][noOfClasses];
        BestClassesForClusters = new ArrayList<Integer>();
        InstsClasses = new Hashtable<Integer, Instances>();
    }


    public void createHistogram (Instances instances) {
        if (instances == null || instances.numInstances() == 0) {
            throw new NullPointerException("Instances for hi can't be empty!");
        }
        InstancesList iList = new InstancesList(NoOfClasses);
        for (Instance inst: instances) {
            iList.add((int)inst.classValue(), inst);
        }
    }

    public int getNoOfClasses() {
        return NoOfClasses;
    }

    public void addInstance (Instance inst, Integer cluster) {
        Instances insts = InstsClasses.get(cluster);
        insts.add(inst);
        InstsClasses.put(cluster, insts);
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

    public Instances getInstancesForCluster (Integer clusterNo) {
       return  InstsClasses.get(clusterNo);
    }
}


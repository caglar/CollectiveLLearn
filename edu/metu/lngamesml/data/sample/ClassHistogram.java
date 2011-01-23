package edu.metu.lngamesml.data.sample;

import edu.metu.lngamesml.core.InstancesList;
import edu.metu.lngamesml.core.InstancesLists;
import edu.metu.lngamesml.utils.random.MersenneTwister;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Jun 6, 2010
 * Time: 8:08:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClassHistogram implements Serializable {

    private int NoOfClasses;
    private InstancesLists List;
    private double[] Hist;

    public ClassHistogram(int noOfClasses, ArrayList<Attribute> attrList, int capacity) {
        String relationName = "class";
        NoOfClasses = noOfClasses;
        List = new InstancesLists();
        Hist = new double[NoOfClasses];

        for (int i = 0; i < NoOfClasses; i++) {
            relationName += i;
            List.addInstancesList(i, new InstancesList(NoOfClasses, relationName, attrList, capacity));
            Hist[i] = 0;
        }
    }

    public void addInstances(Instances insts) {
        for (Instance inst : insts) {
            Hist[(int) inst.classValue()]++;
            addInstance((int) inst.classValue(), inst);
        }
    }

    public void addInstance(int classIdx, Instance inst) {
        List.addInstanceToInstancesList(classIdx, inst);
        Hist[classIdx]++;
    }

    public void removeInstance(int classIdx, Instance inst) {
        List.removeInstanceFromInstanceList(classIdx, inst);
        Hist[classIdx]--;
    }

    public void removeInstance(int classIdx, int instId) {
        List.removeInstanceFromInstanceList(classIdx, instId);
        Hist[classIdx]--;
    }

    public Instance fetchInstance(int classIdx, int instId) {
        Instance result = List.getInstanceFromInstanceList(classIdx, instId);
        if (result == null) {
            throw new NullPointerException("No instance with the class: " + classIdx + " and index: " + instId + " exists");
        } else {
            removeInstance(classIdx, instId);
        }
        return result;
    }

    public Instance getInstance(int classIdx, int instId) {
        Instance result = List.getInstanceFromInstanceList(classIdx, instId);
        return result;
    }

    public int getNumberOfInstanceInClass(int classIdx) {
        return (List.getInstancesList(classIdx) != null) ? (List.getInstancesList(classIdx).size()) : 0;
    }

    public Instance getRandomInstance(int classIdx) {
        int noOfInstances = getNumberOfInstanceInClass(classIdx);
        MersenneTwister msTwist = new MersenneTwister(System.nanoTime());
        Instance inst = List.getInstanceFromInstanceList(classIdx, msTwist.nextInt(noOfInstances));
        return inst;
    }

    public Instance fetchRandomInstance(int classIdx) {
        int noOfInstances = getNumberOfInstanceInClass(classIdx);
        MersenneTwister msTwist = new MersenneTwister(System.nanoTime());
        Instance inst = fetchInstance(classIdx, msTwist.nextInt(noOfInstances));
        return inst;
    }

    public double[] getClassHist() {
        return Hist;
    }

}

package edu.cglr.lngamesml.core;

import edu.cglr.lngamesml.utils.InstancesUtils;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Jun 20, 2010
 * Time: 5:27:27 PM
 * To change this template use File | Settings | File Templates.
 */

public class Data {
    private Instances Minstances;
    private int[] ClassesMap;
    private int NoOfClasses;
    private int MaxSize = 0;
    
    public Data(String relationName, ArrayList<Attribute> attrList, int noOfClasses) {
        NoOfClasses = noOfClasses;
        ClassesMap = new int[noOfClasses];
        Minstances = InstancesUtils.getEmptyInstances(relationName, attrList, MaxSize);
    }

    public void setMaxSize(int maxSize) {
        MaxSize = maxSize;
    }

    public void add(Instance inst) {
        Minstances.add(inst);
        ClassesMap[(int) inst.classValue()]++;
    }

    public void add(int idx, Instance inst) {
        Minstances.add(idx, inst);
        ClassesMap[(int) inst.classValue()]++;
    }

    public void set(int idx, Instance inst) {
        ClassesMap[(int) Minstances.get(idx).classValue()]--;
        Minstances.set(idx, inst);
        ClassesMap[(int) inst.classValue()]++;
    }

    public int[] getClassesMap() {
        return ClassesMap;
    }

    public Instances getInstances() {
        return Minstances;
    }

    public Instance get(int idx) {
        return Minstances.get(idx);
    }

    public Instance remove(int idx) {
        return Minstances.remove(idx);
    }

    public int size() {
        return Minstances.size();
    }

    public int numValues() {
        return Minstances.size();
    }

    public Object copy() {
        return this.copy();
    }
}

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
 * Date: Jun 6, 2010
 * Time: 12:51:46 PM
 * To change this template use File | Settings | File Templates.
 */

public class InstancesList {
    private LinkedList<Instance> Minstances;
    private int[] ClassesMap;
    private int NoOfClasses;
    private ArrayList<Attribute> attrList;

    public InstancesList(int noOfClasses) {
        NoOfClasses = noOfClasses;
        Minstances = new LinkedList<Instance>();
        ClassesMap = new int[noOfClasses];
        resetAttributeList();
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

    /*
        Gets the instances of instanceList
        @param relationName Defines the relation
     */
    public Instances getInstances(String relationName) {
        //If attribute list is empty than create it
        if (attrList.isEmpty()) {
           attrList = InstancesUtils.getAttributeList(Minstances.get(0));
        }
        Instances insts = new Instances(relationName, attrList, size());
        for (Instance inst : Minstances) {
            insts.add(inst);
        }
        return insts;
    }

    public void resetAttributeList () {
        attrList = new ArrayList<Attribute>();
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

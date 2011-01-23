package edu.metu.lngamesml.core;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Jun 6, 2010
 * Time: 12:51:46 PM
 * To change this template use File | Settings | File Templates.
 */

public class InstancesList implements InstancesBag {
    private Instances Minstances;
    private int[] ClassesMap;
    private int NoOfClasses;
    private ArrayList<Attribute> attrList;

    public InstancesList(int noOfClasses, String relationName, ArrayList<Attribute> attrList, int capacity) {
        NoOfClasses = noOfClasses;
        Minstances = new Instances(relationName, attrList, capacity);
        if (Minstances.classIndex() == -1) {
            Minstances.setClassIndex(attrList.size() - 1);
        }
        
        ClassesMap = new int[NoOfClasses];
    }

    public InstancesList(InstancesList iList) {
        this.Minstances = iList.getMinstances();
        this.ClassesMap = iList.getClassesMap();
        this.NoOfClasses = iList.getNoOfClasses();
        this.attrList = iList.getAttrList();
    }

    public void add(Instance inst) {
        Minstances.add(inst);

        if (inst.classValue() > NoOfClasses) {
            System.out.println(inst);
            System.err.println("Dur bir dakika");
        }
        ClassesMap[(int) inst.classValue()]++;
    }

    public void add(int idx, Instance inst) {
        Minstances.add(idx, inst);
        ClassesMap[(int) inst.classValue()]++;
    }

    public void addInstances(Instances insts) {
        for (Instance inst : insts) {
            add(inst);
        }
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
        if (Minstances.isEmpty()) {
            throw new NullPointerException("No instance to return from getInstances");
        }
        return Minstances;
    }

    public void resetAttributeList() {
        attrList = new ArrayList<Attribute>();
    }

    public Instance get(int idx) {
        return Minstances.get(idx);
    }

    public void remove(int idx) {
        Minstances.remove(idx);
    }

    public void remove(Instance inst) {
        Minstances.remove(inst);
    }

    public int size() {
        return Minstances.size();
    }

    public int numValues() {
        return Minstances.size();
    }

    public Instances getMinstances() {
        return Minstances;
    }

    public int getNoOfClasses() {
        return NoOfClasses;
    }

    public ArrayList<Attribute> getAttrList() {
        return attrList;
    }

    public Object copy() {
        return new InstancesList(this);
    }
}

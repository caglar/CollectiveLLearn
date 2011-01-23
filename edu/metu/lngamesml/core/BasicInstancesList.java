package edu.metu.lngamesml.core;

import edu.metu.lngamesml.utils.InstancesUtils;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Collections;

/*
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Nov 5, 2010
 * Time: 5:31:08 AM
 * To change this template use File | Settings | File Templates.
 */

public class BasicInstancesList implements InstancesBag {
    
    private ArrayList <Instance> Minstances;
    private ArrayList<Attribute> attrList;

    public BasicInstancesList() {
        Minstances = new ArrayList <Instance>();
    }

    public BasicInstancesList(BasicInstancesList bil) {
        this.Minstances = bil.getMinstances();
        this.attrList = bil.getAttrList();
    }
    
    public void add(Instance inst) {
        Minstances.add(inst);
    }

    public void add(int idx, Instance inst) {
        Minstances.add(idx, inst);
    }

    public void addInstances(Instances insts) {
        for (Instance inst : insts) {
            add(inst);
        }
    }

    public void set(int idx, Instance inst) {
        Minstances.set(idx, inst);
    }

    public void shuffle(){
        Collections.shuffle(Minstances);
    }

    public Instances getInstances(String relationName, ArrayList<Attribute> attrList) {
        if (Minstances.isEmpty()) {
            throw new NullPointerException("No instance to return from getInstances");
        }
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

    public ArrayList<Instance> getMinstances() {
        return Minstances;
    }

    public ArrayList<Attribute> getAttrList() {
        return attrList;
    }

    public Object copy() {
        return new BasicInstancesList(this);
    }
}

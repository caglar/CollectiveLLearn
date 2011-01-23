package edu.metu.lngamesml.core;

import weka.core.Instance;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Jun 8, 2010
 * Time: 6:19:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class InstancesLists implements Serializable {
    ArrayList<InstancesList> InstLists;

    public InstancesLists() {
        InstLists = new ArrayList<InstancesList>();
    }

    public void addInstancesList(InstancesList instList) {
        InstLists.add(instList);
    }

    public void addInstancesList(int classIdx, InstancesList instList) {
        InstLists.add(classIdx, instList);
    }

    public void addInstanceToInstancesList(int classIdx, Instance inst) {
        InstLists.get(classIdx).add(inst);
    }

    public Instance getInstanceFromInstanceList(int classIdx, int instIdx) {
        return InstLists.get(classIdx).get(instIdx);
    }

    public InstancesList getInstancesList(int classIdx) {
        return InstLists.get(classIdx);
    }

    public void removeInstanceFromInstanceList(int classIdx, int instIdx) {
        InstLists.get(classIdx).remove(instIdx);
    }

    public void removeInstanceFromInstanceList(int classIdx, Instance inst) {
        InstLists.get(classIdx).remove(inst);
    }
}

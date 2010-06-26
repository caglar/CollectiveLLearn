package edu.cglr.lngamesml.core;

import weka.core.Instance;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Jun 8, 2010
 * Time: 6:19:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class InstancesLists {
    ArrayList <InstancesList> InstLists;

    public InstancesLists () {
        InstLists = new ArrayList<InstancesList>();
    }

    public void addInstancesList(InstancesList instList){
        InstLists.add(instList);
    }

    public void addInstancesList(int idx, InstancesList instList){
        InstLists.add(idx, instList);
    }

    public void addInstanceToInstancesList(int idx, Instance inst) {
        InstLists.get(idx).add(inst);
    }

    public Instance getInstanceFromInstanceList(int idx, int instIdx) {
        return InstLists.get(idx).get(instIdx);
    }

    public InstancesList getInstancesList(int idx) {
        return InstLists.get(idx);
    }
    
    public void removeInstanceFromInstanceList (int idx, int instIdx) {
        InstLists.get(idx).remove(instIdx);
    }
}

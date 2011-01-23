package edu.metu.lngamesml.agents.lexicon;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Sep 13, 2010
 * Time: 3:42:15 AM
 * To change this template use File | Settings | File Templates.
 */

public class ClassLexicon {

    private HashMap<String, ArrayList<Integer>> ClassLex = new HashMap<String, ArrayList<Integer>>(); //Instance Table index held in a Hashmap key is category
    protected ArrayList<Instance> InstanceList; //Instance Table
    private ArrayList AttList = new ArrayList<Attribute>();

    public ClassLexicon() {
        InstanceList = new ArrayList<Instance>();
    }

    public ClassLexicon(int size) {
        InstanceList = new ArrayList<Instance>(size);
    }

    public void addClassInstance(Instance inst, String category) {
        int lastIndex;

        if (AttList.isEmpty()) {
            AttList = Collections.list(inst.enumerateAttributes());
        }

        if (ClassLex.get(category) == null) {
            InstanceList.add(inst);
            ArrayList<Integer> list = new ArrayList<Integer>();
            lastIndex = InstanceList.lastIndexOf(inst);
            list.add(lastIndex);
            ClassLex.put(category, list);
        } else {
            InstanceList.add(inst);
            lastIndex = InstanceList.lastIndexOf(inst);
            ClassLex.get(category).add(lastIndex);
        }
    }

    public ArrayList<Integer> getInstanceIdxList(String category) {
        return ClassLex.get(category);
    }

    public Instances getInstanceList(String category) {
        ArrayList<Integer> InstanceIdxs = ClassLex.get(category);
        Instances classInstances = new Instances("", AttList, InstanceIdxs.size());
        classInstances.setClassIndex(AttList.size() - 1);
        Instance tempInstance;
        for (int idx : InstanceIdxs) {
            tempInstance = InstanceList.get(idx);
            classInstances.add(tempInstance);
        }
        return classInstances;
    }

    public Instance getInstance(int idx) {
        return InstanceList.get(idx);
    }
}
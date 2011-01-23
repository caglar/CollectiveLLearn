package edu.metu.lngamesml.utils;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Enumeration;

public class InstancesUtils {

    public static Instances getEmptyInstances(String relationName, ArrayList<Attribute> attrList, int size) {
        if (relationName.isEmpty()) {
            relationName = "A Relation";
        }
        Instances insts = new Instances(relationName, attrList, size);
        return insts;
    }

    public static ArrayList<Attribute> getAttributeList(Instance inst) {
        ArrayList<Attribute> attrList = new ArrayList<Attribute>();
        Enumeration attrEnum = inst.enumerateAttributes();
        while (attrEnum.hasMoreElements()) {
            attrList.add((Attribute) attrEnum.nextElement());
        }
        return attrList;
    }
}
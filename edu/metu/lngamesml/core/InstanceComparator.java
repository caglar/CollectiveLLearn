package edu.metu.lngamesml.core;

import weka.core.Instance;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 7/11/11
 * Time: 1:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class InstanceComparator implements Comparator<Instance> {
    //Ascending Order
    @Override
    public int compare(Instance instance1, Instance instance2) {
        if (instance1.weight() < instance2.weight())
            return -1;
        else if (instance1.weight() > instance2.weight())
            return 1;
        return 0;
    }
}

package edu.metu.lngamesml.core;

import weka.core.Instance;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 7/11/11
 * Time: 1:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ComparableInstance implements Comparable{
    Instance mInst = null;
    public ComparableInstance(){
    }

    public ComparableInstance(Instance inst){
        mInst = (Instance) inst.copy();
    }

    public void setInstance(Instance inst){
        mInst = (Instance) inst.copy();
    }

    //Ascending order
    @Override
    public int compareTo(Object o) {
        double weight = ((Instance)o).weight();
        if (mInst.weight() == weight)
            return 0;
        else if (mInst.weight() > weight)
            return 1;
        else
            return -1;
    }
}

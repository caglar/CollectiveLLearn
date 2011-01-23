package edu.metu.lngamesml.core;

import weka.core.Instance;
import weka.core.Instances;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Nov 5, 2010
 * Time: 7:02:53 AM
 * To change this template use File | Settings | File Templates.
 */
public interface InstancesBag extends Serializable {
    
    public void add(Instance inst);

    public void add(int idx, Instance inst);

    public void addInstances(Instances insts); 

    public void set(int idx, Instance inst);

    public void resetAttributeList();

    public Instance get(int idx);

    public void remove(int idx);

    public void remove(Instance inst);

    public int size();

    public int numValues(); 

    public Object copy(); 
}

package edu.cglr.lngamesml.data.sample;

import edu.cglr.lngamesml.core.InstancesList;
import edu.cglr.lngamesml.core.InstancesLists;
import edu.cglr.lngamesml.utils.random.MersenneTwister;
import weka.core.Instance;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Jun 6, 2010
 * Time: 8:08:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClassHistogram {
    private int NoOfClasses;
    private InstancesLists List;

    public ClassHistogram(int noOfClasses){
        NoOfClasses = noOfClasses; 
        List = new InstancesLists();
        for (int i = 0; i < NoOfClasses; i++) {
            List.addInstancesList(i, new InstancesList(NoOfClasses));
        }
    }

    public void addInstance (int idx, Instance inst) {
        List.addInstanceToInstancesList(idx, inst);
    }

    public Instance getInstance (int idx, int instId) {
        Instance result = List.getInstanceFromInstanceList(idx, instId);
        if (result == null) {
            throw new NullPointerException("No instance with the class: " + idx + " and index: " + instId +" exists");
        } else {
            List.removeInstanceFromInstanceList(idx, instId);
        }
        return result;
    }

    public Instance getRandomInstance (int classIdx) {
        int noOfInstances = getNumberOfInstanceInClass(classIdx);
        MersenneTwister msTwist = new MersenneTwister(System.nanoTime());
        Instance inst = List.getInstanceFromInstanceList(classIdx, msTwist.nextInt (noOfInstances));
        return inst;
    }

    public int getNumberOfInstanceInClass (int classIdx) {
        return (List.getInstancesList(classIdx)!=null) ? (List.getInstancesList(classIdx).size()) : 0;
    }
}

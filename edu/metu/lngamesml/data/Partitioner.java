package edu.metu.lngamesml.data;

import edu.metu.lngamesml.core.InstancesList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 29, 2010
 * Time: 11:34:15 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Partitioner {
    public InstancesList[] partitionDataset(String dataset, int noOfPartitions);
}

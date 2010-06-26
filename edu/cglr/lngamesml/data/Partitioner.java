package edu.cglr.lngamesml.data;

import weka.core.Instances;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 29, 2010
 * Time: 11:34:15 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Partitioner {
    public abstract Instances[] partitionDataset(String dataset, int noOfPartitions);
}

package edu.metu.lngamesml.data.sample.cluster;

import edu.metu.lngamesml.core.InstancesList;
import edu.metu.lngamesml.data.Partitioner;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 29, 2010
 * Time: 11:35:41 AM
 * KmeansClusterer clusters N number of clusters for each category, to get the most different classes.
 */
public class KMeansCluster implements Partitioner {
    private int NoOfClusters;
    private String Dataset;

    public KMeansCluster(int noOfClusters, String dataset) {
        NoOfClusters = noOfClusters;
        Dataset = dataset;
    }

    public KMeansCluster() {
        NoOfClusters = -1;
        Dataset = null;
    }

    @Override
    public InstancesList[] partitionDataset(String dataset, int noOfPartitions) {
        return new InstancesList[0];  //To change body of implemented methods use File | Settings | File Templates.
    }
}
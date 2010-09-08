package edu.cglr.lngamesml.data.cluster;

import edu.cglr.lngamesml.core.InstancesList;
import weka.core.Instances;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 29, 2010
 * Time: 11:35:41 AM
 * KmeansClusterer clusters N number of clusters for each category, to get the most different classes.
 */
public class KMeansCluster {
    private int NoOfClusters;

    public KMeansCluster(int noOfClusters) {
        NoOfClusters = noOfClusters;
    }

    public InstancesList[] getClusters(Instances data) {
        return null;
    }
}
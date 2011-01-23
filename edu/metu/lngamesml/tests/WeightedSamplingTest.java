package edu.metu.lngamesml.tests;

import edu.metu.lngamesml.core.InstancesList;
import edu.metu.lngamesml.data.sample.WeightedSampling;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Oct 23, 2010
 * Time: 3:08:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class WeightedSamplingTest {

    public static void testPartitionDataset() {
        String dataset = "/home/caglar/Dataset/Day1.TCP.arff";
        int NoOfPartitions = 10;
        WeightedSampling wSampling = new WeightedSampling();
        InstancesList[] data = wSampling.partitionDataset(dataset, NoOfPartitions);
        for (InstancesList iList : data) {
            int[] classesMap = iList.getClassesMap();
            int idx = 0;
            for (int i : classesMap) {
                System.out.println("ClassNo No: " + idx + " " + i);
                idx++;
            }
            System.out.println();
        }
    }

    public static void main(String args[]) {
        testPartitionDataset();
    }
}

package edu.metu.lngamesml.data.sample;

import edu.metu.lngamesml.core.InstancesList;
import edu.metu.lngamesml.data.FileLoaderFactory;
import edu.metu.lngamesml.data.Partitioner;
import weka.core.Instances;
import weka.filters.supervised.instance.Resample;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 29, 2010
 * Time: 11:35:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Bootstrap implements Partitioner {

    private String Dataset;
    private int NoOfPartitions;

    public Bootstrap() {}
    
    public Bootstrap(String dataset, int noOfPartitions) {
        Dataset = dataset;
        NoOfPartitions = noOfPartitions;
    }

    public InstancesList[] partitionDataset(String dataset, int noOfPartitions) {
        Instances data = FileLoaderFactory.loadFile(dataset);
        Resample samplingDevice = new Resample();
        InstancesList result[] = new InstancesList[noOfPartitions];
        //train sampling algorithm
        try {
            int dataSize = data.numInstances();
            int sampleSize = dataSize / noOfPartitions;
            int noOfClasses = data.numClasses();
            int[] classes = new int[noOfClasses];
            System.out.println(data.get(245123));
            for (int i = 0; i < classes.length; i++) {
                classes[i] = i;
            }
            Random rand = null;

            for (int i = 0; i < noOfPartitions; i++) {
                rand = new Random(System.nanoTime());
                samplingDevice.setNoReplacement(true);
                samplingDevice.setSampleSizePercent(100 /(double)(noOfPartitions));
                samplingDevice.setInputFormat(data);
                samplingDevice.createSubsampleWithReplacement(rand, dataSize, sampleSize, noOfClasses, classes);
                result[i].addInstances(samplingDevice.getOutputFormat());
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result; //To change body of implemented methods use File | Settings | File Templates.
    }
}

package edu.cglr.lngamesml.data.sample;

import edu.cglr.lngamesml.data.FileLoaderFactory;
import edu.cglr.lngamesml.data.Partitioner;
import edu.cglr.lngamesml.utils.random.MersenneTwister;
import weka.classifiers.meta.Bagging;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 29, 2010
 * Time: 11:35:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Bootstrap extends Partitioner {
    private String Dataset;
    private int NoOfPartitions;

    public Bootstrap() {}

    public Bootstrap (String dataset, int noOfPartitions) {
        Dataset = dataset;
        NoOfPartitions = noOfPartitions;
    }

    public Instances[] partitionDataset (String dataset, int noOfPartitions) {
        Instances data = FileLoaderFactory.loadFile(dataset);
        Resample samplingDevice = new Resample();
        Instances result[] = new Instances[noOfPartitions];
        //train sampling algorithm
        try {

            int dataSize = data.numInstances();
            int sampleSize = dataSize/noOfPartitions;
            int noOfClasses = data.numClasses();
            int []classes = new int[noOfClasses];
            System.out.println(data.get(245123));
            for (int i = 0; i < classes.length; i++) {
                classes[i] = i;
            }
            Bagging bagging = new Bagging();
            MersenneTwister rand = null;

            for (int i = 0; i < noOfPartitions; i++) {
                rand = new MersenneTwister(System.nanoTime());
                samplingDevice.setNoReplacement(true);
                samplingDevice.setSampleSizePercent(100/(noOfPartitions));
                //samplingDevice.setRandomSeed(10);
                samplingDevice.setInputFormat(data);
                samplingDevice.createSubsampleWithoutReplacement (rand, dataSize, sampleSize, noOfClasses, classes);
                //System.out.println("Iteration: " + i);
                result[i] = samplingDevice.getOutputFormat();
                //result[i] = Filter.useFilter(data, samplingDevice);
                //for (int j=0; j < result[i].numInstances(); j++) {
                //data.removeAll(Arrays.asList(result[i].toArray()));
                //}
            }
            /*MersenneTwister rand = null;
            System.out.println("data size: " + dataSize);
            for (int i = 0; i < noOfPartitions; i++) {
                rand = new MersenneTwister(System.nanoTime());
                dataSize -= sampleSize;
                //samplingDevice.createSubsampleWithReplacement (rand, dataSize, sampleSize, noOfClasses, classes);
                System.out.println("Iteration: " + i);
                result[i] = samplingDevice.getOutputFormat();
            } */
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return result; //To change body of implemented methods use File | Settings | File Templates.
    }
}

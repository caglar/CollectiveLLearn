package edu.metu.lngamesml.data;

import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 29, 2010
 * Time: 5:27:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileLoaderFactory {

    private static Instances dataLoad(String dataset) {
        ConverterUtils.DataSource source = null;
        Instances data = null;
        try {
            source = new ConverterUtils.DataSource(dataset);
            data = source.getDataSet();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (data != null) {
            // setting class attribute if the data format does not provide this information
            // E.g., the XRFF format saves the class attribute information as well
            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);
        }
        return data;
    }

    public static Instances loadFile(String dataset) {
        Instances insts = null;
        if (dataset.endsWith(".arff")) {
            insts = ArffFileLoader.loadFile(dataset);
        } else {
            insts = dataLoad(dataset);
        }
        return insts;
    }
}

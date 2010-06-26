package edu.cglr.lngamesml.data;

import weka.core.Instances;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 29, 2010
 * Time: 5:27:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileLoaderFactory {
    public static Instances loadFile (String dataset) {
        Instances insts = null;
        if(dataset.endsWith(".arff")) {
            insts = ArffFileLoader.loadFile(dataset);
        }
        return insts;
    }
}

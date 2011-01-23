package edu.metu.lngamesml.data;

import edu.metu.lngamesml.utils.log.Logging;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 29, 2010
 * Time: 5:27:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArffFileLoader {
    public static Instances loadFile(String dataset) {
        if (dataset == null || dataset.equals("")) {
            Logging.log(Level.WARNING, "Please don't leave the dataset field empty");
        }
        Instances result = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(dataset));
            result = new Instances(reader);
            if (result.classIndex() == -1)
                result.setClassIndex(result.numAttributes() - 1);
            reader.close();
        } catch (FileNotFoundException e) {
            Logging.log(Level.WARNING, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            Logging.log(Level.WARNING, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }
}

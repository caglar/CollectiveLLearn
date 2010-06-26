package edu.cglr.lngamesml.data;

import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 29, 2010
 * Time: 5:27:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArffFileLoader {
    public static Instances loadFile (String dataset) {
        if ( dataset == null || dataset.equals("") ) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Please don't leave the dataset field empty");
        }
        Instances       result = null;
        BufferedReader  reader = null;
        try {
            reader = new BufferedReader(new FileReader(dataset));
            result = new Instances(reader);
            result.setClassIndex(result.numAttributes() - 1);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }
}

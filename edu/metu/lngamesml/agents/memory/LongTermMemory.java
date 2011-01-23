package edu.metu.lngamesml.agents.memory;

import edu.metu.lngamesml.agents.lexicon.ClassLexicon;
import weka.core.Instance;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Sep 13, 2010
 * Time: 3:04:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class LongTermMemory extends ClassLexicon {
    private ArrayList<Double> ConfidenceList = new ArrayList<Double>();

    public void addConfidence(Instance inst, double confidence) {
        int lastIndex = InstanceList.lastIndexOf(inst);
        ConfidenceList.add(lastIndex, confidence);
    }

    public double getConfidence(Instance inst) {
        int lastIndex = InstanceList.lastIndexOf(inst);
        return ConfidenceList.get(lastIndex);
    }
}

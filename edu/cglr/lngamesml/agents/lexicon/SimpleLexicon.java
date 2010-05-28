package edu.cglr.lngamesml.agents.lexicon;

import weka.core.Instance;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 18, 2010
 * Time: 6:09:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleLexicon extends Lexicon {
    private ArrayList<WordObjectPair> wopairs ;

    public SimpleLexicon () {
        wopairs = new ArrayList<WordObjectPair>();
    }

    public void addWordObjPair (WordObjectPair wopair) {
        wopairs.add(wopair);
    }

    public void addWordObjPair (int i, WordObjectPair wopair) {
        wopairs.add(wopair);
    }
    
    public void addWordObjPair (int i, String word, Object inst) {
        wopairs.add(new WordObjectPair(word, (Instance)inst));
    }
    
    public WordObjectPair getWOPair (int i) {
        return wopairs.get(i);
    }
    
    public String getWord (int i) {
        return wopairs.get(i).getWord();
    }

    public void setWord (int i, String category) {
        wopairs.get(i).setWord(category);
    }
    
    public Instance getObject (int i) {
        return wopairs.get(i).getObject();
    }
}

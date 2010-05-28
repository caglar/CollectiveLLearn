package edu.cglr.lngamesml.agents.lexicon;

import weka.core.Instance;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 13, 2010
 * Time: 7:02:15 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Lexicon {

    public abstract void addWordObjPair (WordObjectPair wopair);

    public abstract void addWordObjPair (int i, WordObjectPair wopair);

    public abstract void addWordObjPair (int i, String word, Object inst);

    public abstract WordObjectPair getWOPair (int i);

    public abstract String getWord (int i);

    public abstract void setWord (int i, String word);

}

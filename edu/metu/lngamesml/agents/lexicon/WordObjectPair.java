package edu.metu.lngamesml.agents.lexicon;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 18, 2010
 * Time: 6:17:45 AM
 * To change this template use File | Settings | File Templates.
 */

import weka.core.Instance;

public class WordObjectPair {
    private String Word = "";
    private Instance Object;

    public WordObjectPair(String word, Instance object) {
        this.Word = word;
        this.Object = object;
    }

    public WordObjectPair() {
    }

    public String getWord() {
        return this.Word;
    }

    public void setWord(String word) {
        this.Word = word;
    }

    public Instance getObject() {
        return this.Object;
    }

    public void setObject(Instance object) {
        this.Object = object;
    }

    @Override
    public int hashCode() {
        int result = Object.hashCode();
        result ^= Word.hashCode();
        return result;
    }

    public boolean equals(WordObjectPair wordopair) {
        boolean val = false;
        if (wordopair.getWord().equals(Word) && wordopair.getObject().equals(Object)) {
            val = true;
        }
        return val;
    }

    @Override
    public String toString() {
        return "Word is: " + Word + " Object is: " + Object;
    }
}
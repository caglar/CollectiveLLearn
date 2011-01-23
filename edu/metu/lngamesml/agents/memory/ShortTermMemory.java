package edu.metu.lngamesml.agents.memory;

import edu.metu.lngamesml.agents.com.CategoricalComm;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Sep 13, 2010
 * Time: 3:04:08 AM
 * To change this template use File | Settings | File Templates.
 */

public class ShortTermMemory {
    private CategoricalComm catComm;

    public ShortTermMemory() {
        catComm = new CategoricalComm();
    }

    public String getFocusedCategory() {
        return catComm.getComStatement();
    }

    public void setFocusedCategory(String focusedCat) {
        catComm.setComStatement(focusedCat);
    }

    public int getFocusedCategoryId() {
        return catComm.getStatementNo();
    }

    public void setFocusedCategoryId(int focusedCategoryId) {
        catComm.setStatementNo(focusedCategoryId);
    }

    public CategoricalComm getCatComm() {
        return catComm;
    }

    public void setCatComm(CategoricalComm catComm) {
        this.catComm = catComm;
    }

    public void resetMemory() {
        catComm.setStatementNo(-1);
        catComm.setComStatement("");
        catComm.setConfidence(0.0);
    }


}

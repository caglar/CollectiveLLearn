package edu.metu.lngamesml.agents.com;

import edu.metu.lngamesml.core.BaseCategoryObject;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Sep 13, 2010
 * Time: 2:20:39 AM
 * To change this template use File | Settings | File Templates.
 */

public class CategoricalComm implements BaseCategoryObject, Communication {

    private String Statement = "";
    private int StatementNo = -1;
    private double confidence = 0.0;

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public int getStatementNo() {
        return StatementNo;
    }

    public void setStatementNo(int statementNo) {
        StatementNo = statementNo;
    }

    @Override
    public String getComStatement() {
        return Statement;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setComStatement(String statement) {
        Statement = statement;
    }

    @Override
    public boolean equals(Object catComm) {
        boolean returnVal = false;
        if (catComm != null) {
            if (catComm instanceof CategoricalComm) {
                if (this == catComm) {
                    returnVal = true;
                } else if (((CategoricalComm) catComm).getComStatement().equals(Statement) && ((CategoricalComm) catComm).getStatementNo() == StatementNo) {
                    returnVal = true;
                }
            }
        }
        return returnVal;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + (Statement == null ? 0 : Statement.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return "Statement is " + Statement + " confidence is: " + confidence + "Statement No is: " + StatementNo;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
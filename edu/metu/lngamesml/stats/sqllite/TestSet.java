/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.metu.lngamesml.stats.sqllite;


import edu.metu.lngamesml.stats.nosql.Game;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author caglar
 */
public class TestSet {
    private int Id;
    private int TestNo = 0;
    private String StartTime = "";
    private String EndTime = "";
    private String TestDate = "";

    public String getEndTime() {
        return EndTime;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void endTestSet(){
        EndTime = getTime();
        TestDate = getDate();
    }

    public void startTestset(){
        StartTime = getTime();
    }

    public int getTestNo() {
        return TestNo;
    }

    public void setTestNo(int TestNo) {
        this.TestNo = TestNo;
    }

    public String getTestDate() {
        return TestDate;
    }

    public void setTestDate(String TestDate) {
        this.TestDate = TestDate;
    }

    private String getTime(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void end(){
        endTestSet();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    static class Games {
        ArrayList<edu.metu.lngamesml.stats.nosql.Game> GameList = new ArrayList<edu.metu.lngamesml.stats.nosql.Game>();

        public void addNewGame(String gameName, String testDataset, int noOfAgents, boolean isBeliefUpdates, double samplingRatio){
            edu.metu.lngamesml.stats.nosql.Game tGame = new edu.metu.lngamesml.stats.nosql.Game(gameName, testDataset, noOfAgents, isBeliefUpdates, samplingRatio);
            GameList.add(tGame);
        }

        public ArrayList<edu.metu.lngamesml.stats.nosql.Game> getGames(){
            return GameList;
        }

        public Game getGameAt(int idx){
            return GameList.get(idx);
        }
    }
}

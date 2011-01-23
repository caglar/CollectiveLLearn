/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.metu.lngamesml.stats;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author caglar
 */
@Entity
public class TestSet{
    @Id private String id;
    
    private int TestNo = 0;
    private String StartTime = "";
    private String EndTime = "";
    private String TestDate = "";


    @Embedded
    private ArrayList<Game> Gamez = new ArrayList<Game>();

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

    public ArrayList<Game> getGamez() {
        return Gamez;
    }

    public void setGamez(ArrayList<Game> Gamez) {
        this.Gamez = Gamez;
    }

    public void addGame(Game game){
        Gamez.add(game);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    static class Games {
        ArrayList<Game> GameList = new ArrayList<Game>();

        public void addNewGame(String gameName, String testDataset, int noOfAgents, boolean isBeliefUpdates, double samplingRatio){
            Game tGame = new Game(gameName, testDataset, noOfAgents, isBeliefUpdates, samplingRatio);
            GameList.add(tGame);
        }

        public ArrayList<Game> getGames(){
            return GameList;
        }

        public Game getGameAt(int idx){
            return GameList.get(idx);
        }
    }
}

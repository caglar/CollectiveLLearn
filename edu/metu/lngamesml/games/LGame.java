package edu.metu.lngamesml.games;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 15, 2010
 * Time: 2:56:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface LGame extends Serializable {
    
    public void createAgents(final String trainingDataset);

    public void setAgentsOnGraph(int x, int y);

    public void playGames(final String testDataset) throws Exception;
}

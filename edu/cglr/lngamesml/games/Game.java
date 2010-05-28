package edu.cglr.lngamesml.games;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 15, 2010
 * Time: 2:56:02 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Game {
    public abstract void createAgents (String []trainingDatasets, String testDataset);
    public abstract void setAgentsOnGraph (int x, int y);
    public abstract void playGames (final int testDatasetSize);
}

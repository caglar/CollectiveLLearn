package edu.metu.lngamesml.graphs.jung;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.GraphLink;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 16, 2010
 * Time: 11:01:49 PM
 * To change this template use File | Settings | File Templates.
 */
interface GameGraph {
    public void addVertex(Agent ag);

    public void addEdge(GraphLink edge, Agent agent1, Agent agent2);

    public void clearGraph();
}
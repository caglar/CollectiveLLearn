package edu.metu.lngamesml.network;

import edu.metu.lngamesml.agents.Agent;
import edu.uci.ics.jung.algorithms.generators.random.KleinbergSmallWorldGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.event.GraphEvent;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 4/26/11
 * Time: 9:43 PM
 * See the following resource for more detailed explanation on implementation of the small world networks:
 *
 * http://www.cs.cornell.edu/home/kleinber/swn.d/swn.html
 * http://www.cs.cornell.edu/home/kleinber/swn.pdf
 * http://introcs.cs.princeton.edu/java/45graph/
 *
 * Also see the following implementation:
 *
 * http://code.google.com/p/flexigraph/source/browse/trunk/src/gr/forth/ics/graph/algo/KleinbergSmallWorldGenerator.java
 */
public class SmallWorldNetwork implements Network
{
    private int NoOfNodes;
    private int NoOfEdges;
    Graph<GraphEvent.Vertex, GraphEvent.Edge> SmallWorldNetwork;

    public SmallWorldNetwork(int noOfNodes, int noOfEdges){
        NoOfEdges = noOfEdges;
        NoOfNodes = noOfNodes;
        SmallWorldNetwork = KleinbergSmallWorldGenerator<GraphEvent.Vertex, GraphEvent.Edge>();
    }

    @Override
    public Agent pickAgent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAgentsOnNetwork(ArrayList<Agent> agentList) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

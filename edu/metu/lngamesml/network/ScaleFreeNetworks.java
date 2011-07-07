package edu.metu.lngamesml.network;

import edu.metu.lngamesml.agents.Agent;
import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.event.GraphEvent;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 4/26/11
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 * See implementation:
 * http://code.google.com/p/jung/source/browse/branches/guava/jung/jung-algorithms/src/main/java/edu/uci/ics/jung/algorithms/generators/random/BarabasiAlbertGenerator.java?spec=svn19&r=19
 */
public class ScaleFreeNetworks implements Network
{
    private int NoOfNodes;
    private int NoOfEdges;
    Graph<GraphEvent.Vertex, GraphEvent.Edge> ScaleFreeNetwork;

    public ScaleFreeNetworks(int noOfNodes, int noOfEdges){
        NoOfEdges = noOfEdges;
        NoOfNodes = noOfNodes;
        SmallWorldNetwork = BarabasiAlbertGenerator <GraphEvent.Vertex, GraphEvent.Edge>();
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

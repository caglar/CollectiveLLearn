package edu.metu.lngamesml.network;

import edu.metu.lngamesml.agents.Agent;
import edu.uci.ics.jung.algorithms.generators.random.MixedRandomGraphGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.event.GraphEvent;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 7/7/11
 * Time: 8:42 AM
 * To change this template use File | Settings | File Templates.
 * See implementation:
 * http://code.google.com/p/jung/source/browse/branches/guava/jung/jung-algorithms/src/main/java/edu/uci/ics/jung/algorithms/generators/random/MixedRandomGraphGenerator.java?spec=svn19&r=19
 */
public class RandomNetwork implements Network {

    private int NoOfNodes;
    private int NoOfEdges;
    Graph<GraphEvent.Vertex, GraphEvent.Edge> RandomNetwork;

    public RandomNetwork(int noOfNodes, int noOfEdges){
        NoOfEdges = noOfEdges;
        NoOfNodes = noOfNodes;
        //RandomNetwork = MixedRandomGraphGenerator<GraphEvent.Vertex, GraphEvent.Edge>();
    }
    public Agent pickAgent(){
          Agent ag = null;

          return ag;
    }

    @Override
    public void placeAgentsOnNetwork(ArrayList<Agent> agentList) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

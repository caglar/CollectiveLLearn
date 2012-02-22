package edu.metu.lngamesml.network;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.graphs.jung.SimpleAgentFactory;
import edu.metu.lngamesml.graphs.jung.SimpleGraphFactory;
import edu.metu.lngamesml.graphs.jung.SimpleStringFactory;
import edu.uci.ics.jung.algorithms.generators.GraphGenerator;
import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.generators.random.KleinbergSmallWorldGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.event.GraphEvent;
import org.apache.commons.collections15.Factory;

import java.util.ArrayList;
import java.util.Set;

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

    private int mClusteringExponent;
    private int mNoOfVertices;
    private Graph<Agent, String> SmallWorldNetwork;

    public SmallWorldNetwork(int noOfVertices, int edgesToAttach, int noOfIterations, Set<Agent> seedVertices){
        mNoOfVertices = noOfVertices;
        mClusteringExponent = 2;

        Factory<Graph<Agent, String>> graphFactory = new SimpleGraphFactory();
        Factory<Agent> agentFactory = new SimpleAgentFactory(graphFactory);

        GraphGenerator<Agent, String> scaleFreeGraphGen = new KleinbergSmallWorldGenerator<Agent, String>(graphFactory, agentFactory, new SimpleStringFactory(), mNoOfVertices, mClusteringExponent);
        SmallWorldNetwork = scaleFreeGraphGen.create();
        //SmallWorldNetwork = BarabasiAlbertGenerator <GraphEvent.Vertex, GraphEvent.Edge>();
    }

    public Agent pickAgents() {
        SmallWorldNetwork.getEdges();
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Agent pickAgent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void placeAgentsOnNetwork(ArrayList<Agent> agentList) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


}

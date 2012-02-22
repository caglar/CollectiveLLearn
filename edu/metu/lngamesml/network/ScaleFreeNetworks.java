package edu.metu.lngamesml.network;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.graphs.jung.SimpleAgentFactory;
import edu.metu.lngamesml.graphs.jung.SimpleGraphFactory;
import edu.metu.lngamesml.graphs.jung.SimpleStringFactory;
import edu.uci.ics.jung.algorithms.generators.GraphGenerator;
import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.event.GraphEvent;
import org.apache.commons.collections15.Factory;

import java.util.ArrayList;
import java.util.Set;

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
    private int mClusteringExponent;
    private int mNoOfVertices;
    private int mNoOfEdgesToAttach;
    private int mNoOfIterations;
    private Set<Agent> mSeedVertices;
    private Graph<Agent, String> ScaleFreeNetwork;

    public ScaleFreeNetworks(int noOfVertices, int edgesToAttach, int noOfIterations, Set<Agent> seedVertices){
        mNoOfEdgesToAttach = edgesToAttach;
        mNoOfVertices = noOfVertices;
        mNoOfIterations = noOfIterations;
        mClusteringExponent = 2;
        mSeedVertices = seedVertices;

        Factory<Graph<Agent, String>> graphFactory = new SimpleGraphFactory();
        Factory<Agent> agentFactory = new SimpleAgentFactory(graphFactory);

        GraphGenerator<Agent, String> scaleFreeGraphGen = new BarabasiAlbertGenerator<Agent, String>(graphFactory, agentFactory, new SimpleStringFactory(), mNoOfVertices, mNoOfEdgesToAttach, mNoOfIterations, mSeedVertices);
        ScaleFreeNetwork = scaleFreeGraphGen.create();
        //SmallWorldNetwork = BarabasiAlbertGenerator <GraphEvent.Vertex, GraphEvent.Edge>();
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

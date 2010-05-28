package edu.cglr.lngamesml.graphs.jung;

import edu.cglr.lngamesml.agents.Agent;
import edu.cglr.lngamesml.agents.GraphLink;
import edu.cglr.lngamesml.utils.random.MersenneTwisterFast;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Graphs;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 28, 2010
 * Time: 6:29:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class BasicGameGraph implements GameGraph {
    Graph<Agent, GraphLink> MGraph;
    
    public BasicGameGraph () {
        initGraph();
    }

    public void initGraph() {
        MGraph = Graphs.synchronizedGraph(new DirectedSparseMultigraph<Agent, GraphLink>());
    }
    
    public void addVertex (Agent ag) {
        if (ag != null) {
            MGraph.addVertex(ag);
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "Please add appropriate Agent as a vertex");
        }
    }

    public void addEdge (GraphLink edge, Agent agent1, Agent agent2) {
        if (edge != null) {
            MGraph.addEdge(edge, agent1, agent2);
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "Please add appropriate name for a vertex");
        }
    }

    public void setVertex (Agent oldAg, Agent newAg ) {
        if (oldAg != null && newAg != null) {
            MGraph.removeVertex (oldAg);
            MGraph.addVertex(newAg);
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "Please don't pass empty agent variables to the setVertex Procedure");
        }
    }

    public void setEdge (GraphLink oldEdge, GraphLink newEdge, Agent agent1, Agent agent2) {
        if (oldEdge != null && newEdge != null) {
            MGraph.removeEdge(oldEdge);
            MGraph.addEdge(newEdge, agent1, agent2);
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "Please don't pass empty agent variables to the setVertex Procedure");
        }
    }

    public Object getRandomVertex () {
        int noOfVertices = MGraph.getVertexCount();
        Object agents[] = MGraph.getVertices().toArray();
        MersenneTwisterFast mTwister = new MersenneTwisterFast();
        return agents[mTwister.nextInt(noOfVertices)];
    }

    @Override
    public void clearGraph() {
        initGraph();
    }
}

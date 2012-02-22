package edu.metu.lngamesml.graphs.jung;

import edu.metu.lngamesml.agents.Agent;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import org.apache.commons.collections15.Factory;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 7/13/11
 * Time: 12:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleGraphFactory  implements Factory<Graph<Agent, String>> {
    @Override
    public Graph<Agent, String> create() {
        return  new UndirectedSparseGraph<Agent, String>();  //To change body of implemented methods use File | Settings | File Templates.
    }
}

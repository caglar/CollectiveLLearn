package edu.metu.lngamesml.graphs.jung;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.AgentTypes;
import edu.metu.lngamesml.agents.BasicCognitiveAgent;
import edu.metu.lngamesml.agents.SupervisedCognitiveAgent;
import edu.uci.ics.jung.graph.Graph;
import org.apache.commons.collections15.Factory;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 7/13/11
 * Time: 12:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleAgentFactory implements Factory<Agent> {
    private Factory<Graph<Agent, String>> graphFactory;
    private AgentTypes mAgType;

    public SimpleAgentFactory(Factory<Graph<Agent, String>> graphFactory) {
        this.graphFactory = graphFactory;
        mAgType = AgentTypes.BASIC_COGNITIVE;
    }

    public void setmAgType(AgentTypes mAgType) {
        this.mAgType = mAgType;
    }

    @Override
    public Agent create() {
        if (mAgType == AgentTypes.BASIC_COGNITIVE)
            return new BasicCognitiveAgent(graphFactory.create());
        return new SupervisedCognitiveAgent(graphFactory.create());
    }
}

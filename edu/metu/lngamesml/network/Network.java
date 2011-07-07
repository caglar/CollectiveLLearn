package edu.metu.lngamesml.network;

import edu.metu.lngamesml.agents.Agent;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 4/26/11
 * Time: 9:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Network {
    public Agent pickAgent();
    public void placeAgentsOnNetwork(ArrayList<Agent> agentList);
}

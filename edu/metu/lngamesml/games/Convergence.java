package edu.metu.lngamesml.games;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 13, 2010
 * Time: 7:13:15 AM
 * To change this template use File | Settings | File Templates.
 */

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.com.CategoricalComm;
import edu.metu.lngamesml.eval.game.AgentsIndividualPerf;
import edu.metu.lngamesml.stats.game.AgentStat;
import edu.metu.lngamesml.stats.game.RunningAgentStat;
import edu.metu.lngamesml.utils.log.Logging;
import weka.core.Instance;

import java.util.*;

/**
 * @author caglar
 */

public class Convergence {

    private Hashtable<CategoricalComm, Integer> CategoryTbl;
    private int NoOfAgents = 0;
    private List<RunningAgentStat> RAgentStats;

    public Convergence(int noOfAgents) {
        CategoryTbl = new Hashtable<CategoricalComm, Integer>();
        RAgentStats = new ArrayList<RunningAgentStat>();
        this.NoOfAgents = noOfAgents;
    }

    private boolean containsCategory(CategoricalComm catComm) {
        Enumeration catEnum = CategoryTbl.keys();
        boolean status = false;
        while (catEnum.hasMoreElements()) {
            CategoricalComm obj = (CategoricalComm) catEnum.nextElement();
            if (obj.equals(catComm)) {
                status = true;
            }
        }
        return status;
    }

    private void initRAgentIfNull(int aNo, List<RunningAgentStat> rAgentStats){
        if(rAgentStats.get(aNo) == null){
            rAgentStats.set(aNo, new RunningAgentStat());
        }
    }

    private Hashtable initTagsTable(ArrayList<Agent> agents, Instance inst, List<RunningAgentStat> rAgentStats) {
        int j = 0;
        int i = 0;
        for (Agent agent : agents) {
            CategoricalComm catComm = null;
            try {
                catComm = agent.speak(inst);
            } catch (Exception e) {
                Logging.warning("Couldn't get the Class from agent");
                e.printStackTrace();
            }
            initRAgentIfNull(i, rAgentStats);
            rAgentStats.get(i).addDecision(i, catComm.getStatementNo(), (int) inst.classValue());
            i++;
            if (j == 0) {
                CategoryTbl.put(catComm, Integer.valueOf(1));
                j++;
            } else if (containsCategory(catComm)) {
                Integer val = CategoryTbl.get(catComm);
                val++;
                CategoryTbl.put(catComm, val);
            } else {
                CategoryTbl.put(catComm, Integer.valueOf(1));
            }
        }
        return CategoryTbl;
    }

    public boolean isConverged(ArrayList<Agent> agents, Instance inst, List<RunningAgentStat> rAgentStats) {
        boolean result = false;
        if (CategoryTbl.isEmpty()) {
            initTagsTable(agents, inst, rAgentStats);
        }

        if (CategoryTbl.size() == 1) {
            result = true;
        } else {
            Collection coll = CategoryTbl.values();
            Object values[] = coll.toArray();
            for (Object value : values) {
                Integer val = (Integer) value;
                if (val >= 1 && val < this.NoOfAgents) {
                    break;
                } else if (val == (this.NoOfAgents)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public CategoricalComm getConvergedCategory() {
        CategoricalComm catComm = CategoryTbl.keys().nextElement();
        return catComm;
    }

    public void balanceTable(CategoricalComm incrementTag, CategoricalComm decrementTag) {
        Integer incrementedValue = CategoryTbl.get(incrementTag);
        Integer decrementedValue = CategoryTbl.get(decrementTag);

        incrementedValue++;
        decrementedValue--;

        if (CategoryTbl.get(decrementTag) == 1){
            CategoryTbl.remove(decrementTag);
        } else {
            CategoryTbl.put(decrementTag, decrementedValue);
        }
        CategoryTbl.put(incrementTag, incrementedValue);
    }

    public void emptyTable() {
        if (!CategoryTbl.isEmpty()) {
            CategoryTbl.clear();
        }
    }

    public int getNoOfAgents() {
        return NoOfAgents;
    }

    public List<RunningAgentStat> getAgentsIndividualPerf(){
        return RAgentStats;
    }
}


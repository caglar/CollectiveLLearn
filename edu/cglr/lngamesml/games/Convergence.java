package edu.cglr.lngamesml.games;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 13, 2010
 * Time: 7:13:15 AM
 * To change this template use File | Settings | File Templates.
 */
import edu.cglr.lngamesml.agents.Agent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

/**
 *
 * @author caglar
 */
public class Convergence {
    private static Hashtable hashTbl ;
    private int noOfAgents = 0 ;

    //protected Convergence(){}
    public Convergence( int noOfAgents ){
        hashTbl = new Hashtable();
        this.noOfAgents = noOfAgents;
    }

    private Hashtable initTagsTable(ArrayList<Agent> agents, int i){
        int j = 0;
        for(Agent agent: agents){
            String label = "";// = agent.speak(i);
            if(j==0){
                hashTbl.put(label, new Integer(1));
            }
            else if(hashTbl.containsKey(label)){
                //System.out.println("Hello Tags table\n");
                Integer val=(Integer)hashTbl.get(label);
                val++;
                hashTbl.put(label, val);
            }
            else{
                hashTbl.put(label, new Integer(1));
            }
            j++;
        }
        return hashTbl;
    }

    public boolean isConverged(ArrayList<Agent> agents, int i){
        boolean result = false;
        if(hashTbl.isEmpty()){
            initTagsTable(agents, i);
        }
        Collection coll = hashTbl.values();
        Object values[] = coll.toArray();
        for ( Object value : values ) {
            Integer val = (Integer) value;
            if(val>=1 && val<this.noOfAgents){
                break;
            }
            else if(val==(this.noOfAgents)){
                result = true;
                break;
            }
        }
        return result;
    }

    public void balanceTable(String incrementTag, String decrementTag){
        Integer incrementedValue = (Integer)hashTbl.get(incrementTag);
        Integer decrementedValue = (Integer)hashTbl.get(decrementTag);
        incrementedValue++;
        decrementedValue--;
        hashTbl.put(incrementTag, incrementedValue);
        hashTbl.put(decrementTag, decrementedValue);
    }

    public void emptyTable(){
        if(!hashTbl.isEmpty()){
            hashTbl.clear();
        }
    }

    public int getNoOfAgents() {
        return noOfAgents;
    }
}


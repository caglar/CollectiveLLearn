package edu.metu.lngamesml.graphs.jung;

import edu.metu.lngamesml.agents.Agent;
import org.apache.commons.collections15.Factory;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 7/13/11
 * Time: 12:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleStringFactory implements Factory<String> {
    private String str;

    public SimpleStringFactory(){
        str = "";
    }

    public SimpleStringFactory(String str){
        this.str = str;
    }

    @Override
    public String create() {
        return str;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

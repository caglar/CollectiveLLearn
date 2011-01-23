package edu.metu.lngamesml.graphs.jung;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.GraphLink;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Graphs;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.commons.collections15.Transformer;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 16, 2010
 * Time: 11:01:06 PM
 * To change this template use File | Settings | File Templates.
 */

public class SimpleGraphView extends GameGraphView {
    Graph<Agent, GraphLink> MGraph;
    Layout<Agent, GraphLink> Layout;
    BasicVisualizationServer<Agent, GraphLink> vv;

    public SimpleGraphView() {
        MGraph = Graphs.synchronizedGraph(new DirectedSparseMultigraph<Agent, GraphLink>());
    }

    public void prepareGraphView() {
        this.Layout = new CircleLayout(MGraph);
        this.Layout.setSize(new Dimension(700, 700));
        vv = new BasicVisualizationServer<Agent, GraphLink>(this.Layout);
        vv.setPreferredSize(new Dimension(800, 800));
        // Setup up a new vertex to paint transformer...
        Transformer<Agent, Paint> vertexPaint = new Transformer<Agent, Paint>() {
            public Paint transform(Agent ag) {
                return Color.GREEN;
            }
        };
        // Set up a new stroke Transformer for the edges
        float dash[] = {10.0f};

        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);

        Transformer<GraphLink, Stroke> edgeStrokeTransformer = new Transformer<GraphLink, Stroke>() {
            public Stroke transform(GraphLink ag) {
                return edgeStroke;
            }
        };

        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
    }

    public void addVertex(Agent ag) {
        if (ag != null) {
            MGraph.addVertex(ag);
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "Please add appropriate Agent as a vertex");
        }
    }

    public void addEdge(GraphLink edge, Agent agent1, Agent agent2) {
        if (edge != null) {
            MGraph.addEdge(edge, agent1, agent2);
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "Please add appropriate name for a vertex");
        }
    }

    public void setVertex(Agent oldAg, Agent newAg) {
        if (oldAg != null && newAg != null) {
            MGraph.removeVertex(oldAg);
            MGraph.addVertex(newAg);
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "Please don't pass empty agent variables to the setVertex Procedure");
        }
    }

    public void setEdge(GraphLink oldEdge, GraphLink newEdge, Agent agent1, Agent agent2) {
        if (oldEdge != null && newEdge != null) {
            MGraph.removeEdge(oldEdge);
            MGraph.addEdge(newEdge, agent1, agent2);
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "Please don't pass empty agent variables to the setVertex Procedure");
        }
    }

    public void clearGraph() {
        prepareGraphView();
    }

    public BasicVisualizationServer getVisServer() {
        System.out.println(MGraph.toString());
        return this.vv;
    }
}

package edu.metu.lngamesml.graphs.jung;

import edu.metu.lngamesml.agents.Agent;
import edu.metu.lngamesml.agents.GraphLink;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.layout.util.Relaxer;
import edu.uci.ics.jung.algorithms.layout.util.VisRunner;
import edu.uci.ics.jung.algorithms.util.IterativeContext;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.ObservableGraph;
import edu.uci.ics.jung.graph.event.GraphEvent;
import edu.uci.ics.jung.graph.event.GraphEventListener;
import edu.uci.ics.jung.graph.util.Graphs;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.util.Animator;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: May 21, 2010
 * Time: 9:15:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class AnimatedGraphView extends GameGraphView {

    private Graph<Agent, GraphLink> g = null;
    private VisualizationViewer<Agent, GraphLink> vv = null;
    private AbstractLayout<Agent, GraphLink> layout = null;
    private boolean AnimationStatus = true;

    public AnimatedGraphView() {

    }


    public void prepareGraphView() {
        // To change body of implemented methods use File | Settings | File Templates.
        // create a graph
        Graph<Agent, GraphLink> ig = Graphs.<Agent, GraphLink>synchronizedDirectedGraph(new DirectedSparseMultigraph<Agent, GraphLink>());
        ObservableGraph<Agent, GraphLink> og = new ObservableGraph<Agent, GraphLink>(ig);
        og.addGraphEventListener(new GraphEventListener<Agent, GraphLink>() {
            public void handleGraphEvent(GraphEvent<Agent, GraphLink> evt) {
                System.err.println("got " + evt);
            }
        });
        this.g = og;
        //create a graphdraw
        layout = new FRLayout<Agent, GraphLink>(g);
        layout.setSize(new Dimension(700, 700));
        // Setup up a new vertex to paint transformer...
        /*
        Transformer<Agent, Paint> vertexPaint = new Transformer<Agent, Paint>() {
            public Paint transform(Agent ag) {
                return Color.GREEN;
            }
        };
        */
        // Set up a new stroke Transformer for the edges
        float dash[] = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
       /*
        Transformer<GraphLink, Stroke> edgeStrokeTransformer = new Transformer<GraphLink, Stroke>() {
            public Stroke transform(GraphLink ag) {
                return edgeStroke;
            }
        };
        */
        Relaxer relaxer = new VisRunner((IterativeContext) layout);
        relaxer.stop();
        relaxer.prerelax();
        Layout<Agent, GraphLink> staticLayout =
                new StaticLayout<Agent, GraphLink>(g, layout);

        vv = new VisualizationViewer<Agent, GraphLink>(staticLayout, new Dimension(680, 680));
        vv.setGraphMouse(new DefaultModalGraphMouse<Agent, GraphLink>());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Agent>());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        //vv.setForeground(Color.white);
        vv.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent arg0) {
                super.componentResized(arg0);
                System.err.println("resized");
                layout.setSize(arg0.getComponent().getSize());
            }
        });
        repaintGraph();
    }

    public boolean isAnimationStatus() {
        return AnimationStatus;
    }

    public void setAnimationStatus(boolean animationStatus) {
        AnimationStatus = animationStatus;
    }

    @Override
    public void addVertex(Agent ag) {
        if (ag != null) {
            g.addVertex(ag);
            if (AnimationStatus) {
                vv.getRenderContext().getPickedVertexState().pick(ag, true);
                repaintGraph();
            }
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "Please add appropriate Agent as a vertex");
        }
    }

    @Override
    public void addEdge(GraphLink edge, Agent agent1, Agent agent2) {
        if (edge != null) {
            Object[] edges;
            if (AnimationStatus) {
                edges = g.getEdges().toArray();
                vv.getRenderContext().getPickedEdgeState().pick(edges.length > 0 ? (GraphLink) edges[edges.length - 1] : edge, true);
            }
            g.addEdge(edge, agent1, agent2);
            if (AnimationStatus) {
                edges = g.getEdges().toArray();
                vv.getRenderContext().getPickedEdgeState().pick(edges.length > 0 ? (GraphLink) edges[edges.length - 1] : edge, true);
                repaintGraph();
            }
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING, "Please add appropriate name for a vertex");
        }
    }

    protected void repaintGraph() {
        layout.initialize();
        Relaxer relaxer = new VisRunner((IterativeContext) layout);
        relaxer.stop();
        relaxer.prerelax();
        StaticLayout<Agent, GraphLink> staticLayout =
                new StaticLayout<Agent, GraphLink>(g, layout);
        LayoutTransition<Agent, GraphLink> lt =
                new LayoutTransition<Agent, GraphLink>(vv, vv.getGraphLayout(), staticLayout);
        Animator animator = new Animator(lt);
        animator.start();
        vv.repaint();
    }

    public Graph<Agent, GraphLink> getG() {
        return g;
    }

    public BasicVisualizationServer<Agent, GraphLink> getVisServer() {
        return this.vv;
    }

    @Override
    public void clearGraph() {
        prepareGraphView();
    }
}
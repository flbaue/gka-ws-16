package com.github.flbaue.gka_ws_15.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by florian on 31/10/2016.
 */
public class GraphGenerator {

    public static Graph generateGraph(final int amountOfNodes, final int amountOfEdges, final boolean directed, final int maxWeight) throws Graph.NodeAlreadyExistsException, Graph.EdgeAlreadyExistsException, Graph.MissingNodeException {

        ArrayList<Node> nodes = new ArrayList<>(amountOfNodes);
        Set<Edge> edges = new HashSet<>(amountOfEdges);

        Graph graph = new Graph();
        graph.isDirected = directed;

        for (int i = 0; i < amountOfNodes; i++) {
            Node node = new Node("n" + i);
            nodes.add(node);
            graph.insertNode(node);
        }

        for (int i = 0; i < amountOfEdges; i++) {
            Node source = nodes.get(random(amountOfNodes));
            Node target = nodes.get(random(amountOfNodes));

            Edge edge = new Edge("e" + i, source, target, randomWeight(maxWeight));

            graph.insertEdge(edge);
        }

        return graph;
    }


    public static void insertPath(Graph graph, int pathLength, Node source, Node target) throws Graph.EdgeAlreadyExistsException, Graph.NodeAlreadyExistsException, Graph.MissingNodeException {

        if (pathLength == 1) {
            graph.insertEdge(new Edge("generatedPath", source, target, 0));

        } else {
            Set<Node> pathNodes = new HashSet<>();
            pathNodes.add(source);
            pathNodes.add(target);

            ArrayList<Node> graphNodes = new ArrayList<>(graph.nodes.values());

            Node lastHop = null;
            for (int i = 0; i < pathLength; i++) {

                Node hop;
                do {
                    hop = graphNodes.get(random(graphNodes.size()));
                } while (pathNodes.contains(hop));

                Edge edge;
                if (i == 0) {
                    edge = new Edge("generatedPath_" + i, source, hop, 0);
                } else if (i == pathLength - 1) {
                    edge = new Edge("generatedPath_" + i, lastHop, target, 0);
                } else {
                    edge = new Edge("generatedPath_" + i, lastHop, hop, 0);
                }
                graph.insertEdge(edge);

                lastHop = hop;

                pathNodes.add(hop);
            }
        }
    }

    private static int random(int max) {
        return (int) (Math.random() * max);
    }

    private static int randomWeight(int max) {
        return (int) Math.floor((Math.random() * max) + 1);
    }
}

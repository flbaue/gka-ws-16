package com.github.flbaue.gka_ws_15.algorithms;

import com.github.flbaue.gka_ws_15.graph.Edge;
import com.github.flbaue.gka_ws_15.graph.Graph;
import com.github.flbaue.gka_ws_15.graph.Node;
import com.github.flbaue.gka_ws_15.graph.Path;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by florian on 30/10/2016.
 */
public final class BreadthFirstSearch {


    public static Path getPath(final Graph graph, final String sourceId, final String targetId) {
        Node source = graph.getNode(sourceId);
        Node target = graph.getNode(targetId);

        Objects.requireNonNull(source, "Source node not included in graph");
        Objects.requireNonNull(target, "Target node not included in graph");

        Path path = new Path();

        int currentTag = target.tag;
        Node currentNode = target;

        while (currentTag > 0) {
            path.addElement(currentNode);
            currentTag -= 1;

            Node predecessor = getPredecessor(graph, currentNode, currentTag);
            Set<Edge> edges = graph.getEdges(predecessor, currentNode);
            Edge edge = edges.stream().findAny().get();

            path.addElement(edge);
            currentNode = predecessor;
        }

        path.addElement(source);

        return path;
    }


    public static boolean search(final Graph graph, final String sourceId, final String targetId) {
        Node source = graph.getNode(sourceId);
        Node target = graph.getNode(targetId);

        Objects.requireNonNull(source, "Source node not included in graph");
        Objects.requireNonNull(target, "Target node not included in graph");

        int currentTag = 0;

        source.tag = currentTag;

        final int tag1 = currentTag;
        Set<Node> nodesWithTag = graph.getNodes(n -> n.tag == tag1);
        currentTag += 1;

        do {
            for (Node currentNode : nodesWithTag) {
                Set<Node> neighbors = getUntaggedNeighbors(graph, currentNode);

                for (Node neighbor : neighbors) {
                    neighbor.tag = currentTag;

                    if (neighbor == target) {
                        return true;
                    }
                }
            }

            final int tag2 = currentTag;
            nodesWithTag = graph.getNodes(n -> n.tag == tag2);
            currentTag += 1;

        } while (nodesWithTag.size() > 0);

        return false;
    }


    private static Set<Node> getUntaggedNeighbors(final Graph graph, final Node node) {
        if (graph.isDirected) {
            return graph.getOutgoingNeighbors(node).stream().filter(n -> n.tag == -1).collect(Collectors.toSet());
        } else {
            return graph.getAdjacentNeighbors(node).stream().filter(n -> n.tag == -1).collect(Collectors.toSet());
        }
    }


    private static Node getPredecessor(Graph graph, Node node, int tag) {
        if (graph.isDirected) {
            return graph.getIncomingNeighbors(node).stream().filter(n -> n.tag == tag).findFirst().get();
        } else {
            return graph.getAdjacentNeighbors(node).stream().filter(n -> n.tag == tag).findFirst().get();
        }
    }

}

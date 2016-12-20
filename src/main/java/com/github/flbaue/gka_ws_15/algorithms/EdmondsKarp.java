package com.github.flbaue.gka_ws_15.algorithms;

import com.github.flbaue.gka_ws_15.graph.Edge;
import com.github.flbaue.gka_ws_15.graph.Graph;
import com.github.flbaue.gka_ws_15.graph.Node;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by florian on 18/12/2016.
 */
public class EdmondsKarp {

    public static int nodeAccessCounter = 0;
    private static Map<Edge, Double> flow;

    public static double maxFlow(Graph graph, Node source, Node target) throws Graph.MissingNodeException, Graph.EdgeAlreadyExistsException {
        flow = new HashMap<>(graph.getEdges().size());

        Set<Edge> rEdges = new HashSet<>(graph.getEdges().size());

        for (Edge edge : graph.getEdges()) {
            Edge rEdge = new Edge(edge.id + "-r", edge.target, edge.source, 0);
            edge.redge = rEdge;
            rEdge.redge = edge;
            rEdges.add(rEdge);
            flow.put(edge, 0.0);
            flow.put(rEdge, 0.0);
        }

        for (Edge rEdge : rEdges) {
            graph.insertEdge(rEdge);
        }

        List<Edge> path = findPath(graph, source, target);

        while (!path.isEmpty()) {

            Set<Double> residuals = path.stream()
                    .map(edge -> edge.value - flow.get(edge))
                    .collect(Collectors.toSet());

            double pathFlow = residuals.stream()
                    .min(Double::compareTo)
                    .get();

            for (Edge edge : path) {
                flow.put(edge, flow.get(edge) + pathFlow);
                flow.put(edge.redge, flow.get(edge.redge) - pathFlow);
            }

            path = findPath(graph, source, target);
        }

        return getEdges(source).stream()
                .map(edge -> flow.get(edge))
                .reduce(Double::sum)
                .get();

    }

    private static List<Edge> findPath(Graph graph, Node source, Node target) {
        bfs(graph, source, target);
        List<Edge> result = getPath(graph, target);
        cleanNodes(graph);
        return result;
    }

    private static void cleanNodes(Graph graph) {
        graph.getNodes().forEach(node -> node.tag = -1);
    }

    private static boolean bfs(Graph graph, Node source, Node target) {

        int currentTag = 0;

        source.tag = currentTag;

        final int tag1 = currentTag;
        Set<Node> nodesWithTag = graph.getNodes(n -> n.tag == tag1);
        nodeAccessCounter += graph.getNodesAmount();
        currentTag += 1;

        do {
            for (Node currentNode : nodesWithTag) {
                Set<Node> neighbors = getUntaggedNeighbors(currentNode);
                nodeAccessCounter += currentNode.edges.size();

                for (Node neighbor : neighbors) {
                    neighbor.tag = currentTag;

                    if (neighbor == target) {
                        return true;
                    }
                }
            }

            final int tag2 = currentTag;
            nodesWithTag = graph.getNodes(n -> n.tag == tag2);
            nodeAccessCounter += graph.getNodesAmount();
            currentTag += 1;

        } while (nodesWithTag.size() > 0);

        return false;
    }


    private static Set<Node> getUntaggedNeighbors(Node node) {
        return node.edges.stream()
                .filter(edge -> edge.source == node)
                .filter(edge -> edge.target.tag == -1)
                .filter(edge -> edge.value - flow.get(edge) > 0)
                .map(edge -> edge.target)
                .collect(Collectors.toSet());
    }


    private static Node getPredecessor(Graph graph, Node node, int tag) {

        return node.edges.stream()
                .filter(edge -> edge.target == node && edge.source != node)
                .filter(edge -> edge.source.tag == tag)
                .filter(edge -> edge.value - flow.get(edge) > 0)
                .map(edge -> edge.source)
                .findAny().get();
    }


    private static List<Edge> getEdges(Node source) {
        List<Edge> edges = source.edges.stream()
                .filter(edge -> edge.source == source && edge.target != source)
                .collect(Collectors.toList());

        return edges;
    }

    private static List<Edge> getPath(Graph graph, Node target) {

        List<Edge> path = new LinkedList<>();

        int currentTag = target.tag;
        Node currentNode = target;

        while (currentTag > 0) {
            currentTag -= 1;

            Node predecessor = getPredecessor(graph, currentNode, currentTag);
            Set<Edge> edges = graph.getEdges(predecessor, currentNode);
            Edge edge = getEdgeWithCapacity(predecessor, currentNode);

            path.add(edge);
            currentNode = predecessor;
        }


        return path;
    }

    private static Edge getEdgeWithCapacity(Node source, Node target) {
        return source.edges.stream()
                .filter(edge -> edge.target == target && edge.source == source)
                .filter(edge -> edge.value - flow.get(edge) > 0)
                .findAny().get();
    }
}

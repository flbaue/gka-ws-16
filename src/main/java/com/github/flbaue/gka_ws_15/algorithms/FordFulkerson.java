package com.github.flbaue.gka_ws_15.algorithms;

import com.github.flbaue.gka_ws_15.graph.Edge;
import com.github.flbaue.gka_ws_15.graph.Graph;
import com.github.flbaue.gka_ws_15.graph.Node;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by florian on 18/12/2016.
 */
public class FordFulkerson {

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

        List<Edge> path = findPath(graph, source, target, new LinkedList<>());

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

            path = findPath(graph, source, target, new LinkedList<>());
        }

        return getEdges(graph, source).stream()
                .map(edge -> flow.get(edge))
                .reduce(Double::sum)
                .get();

    }

     private static List<Edge> findPath(Graph graph, Node source, Node target, List<Edge> path) {
        if (source.equals(target)) {
            return path;
        } else {
            for (Edge edge : getEdges(graph, source)) {
                double residual = edge.value - flow.get(edge);
                if (residual > 0 && !path.contains(edge)) {
                    List<Edge> tmpPath = new LinkedList<>(path);
                    tmpPath.add(edge);
                    List<Edge> result = findPath(graph, edge.target, target, tmpPath);
                    if (!result.isEmpty()) {
                        return result;
                    }
                }
            }

            return Collections.emptyList();
        }
    }

    private static List<Edge> getEdges(Graph graph, Node source) {
        List<Edge> edges = source.edges.stream()
                .filter(edge -> edge.source == source && edge.target != source)
                .collect(Collectors.toList());

//        edges.sort(Comparator.comparing(o -> o.id));
        return edges;
    }
}

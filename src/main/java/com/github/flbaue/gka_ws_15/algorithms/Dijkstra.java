package com.github.flbaue.gka_ws_15.algorithms;

import com.github.flbaue.gka_ws_15.graph.Edge;
import com.github.flbaue.gka_ws_15.graph.Graph;
import com.github.flbaue.gka_ws_15.graph.Node;
import com.github.flbaue.gka_ws_15.graph.Path;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by florian on 31/10/2016.
 */
public final class Dijkstra {

    public static long graphAccessCounter;

    public static Path search(final Graph graph, final Node source, final Node target) {

        graphAccessCounter = 0;

        Map<Node, Double> distanceMap = new HashMap<>(graph.getNodesAmount()); graphAccessCounter++;
        Map<Node, Node> transitMap = new HashMap<>(graph.getNodesAmount()); graphAccessCounter++;
        Set<Node> unvisitedNodes = new HashSet<>(graph.getNodesAmount()); graphAccessCounter++;

        initialize(graph, distanceMap, transitMap, unvisitedNodes);

        distanceMap.put(source, 0.0);

        while (!unvisitedNodes.isEmpty()) {
            Node node = getNodeWithMinDistance(unvisitedNodes, distanceMap);
            unvisitedNodes.remove(node);

            Set<Node> neighbors = getUnvisitedNeighbors(node, graph, unvisitedNodes);

            neighbors.forEach(neighbor -> {
                double distance = distanceMap.get(node) + graph.getMinEdge(node, neighbor).value; graphAccessCounter++;
                if (distance < distanceMap.get(neighbor)) {
                    distanceMap.put(neighbor, distance);
                    transitMap.put(neighbor, node);
                }
            });
        }

        // Collect shortest path
        Path path = new Path();
        Node currentNode = target;
        path.addElement(currentNode);
        while (transitMap.get(currentNode) != null) {
            Node previousNode = transitMap.get(currentNode);
            Edge edge = graph.getMinEdge(previousNode, currentNode); graphAccessCounter++;
            path.addElement(edge);
            path.addElement(previousNode);
            currentNode = previousNode;
        }
        return path;
    }



    private static Set<Node> getUnvisitedNeighbors(final Node node, final Graph graph, final Set<Node> unvisitedNodes) {
        Set<Node> neighbors;

        graphAccessCounter++;
        if (graph.isDirected) {
            neighbors = graph.getOutgoingNeighbors(node);
            graphAccessCounter++;
        } else {
            neighbors = graph.getAdjacentNeighbors(node);
            graphAccessCounter++;
        }

        neighbors.retainAll(unvisitedNodes);

        return neighbors;
    }

    private static Node getNodeWithMinDistance(final Set<Node> unvisitedNodes, final Map<Node, Double> distanceMap) {

        return unvisitedNodes.stream()
                .map(n -> new Tuple<>(n, distanceMap.get(n)))
                .min((t1, t2) -> Double.compare(t1.v, t2.v))
                .map(t -> t.k)
                .get();

    }

    private static void initialize(final Graph graph, final Map<Node, Double> distanceMap, final Map<Node, Node> transitMap, final Set<Node> unvisitedNodes) {

        graph.getNodes().stream().forEach(n -> {
            distanceMap.put(n, Double.POSITIVE_INFINITY);
            transitMap.put(n, null);
            unvisitedNodes.add(n);
        }); graphAccessCounter++;
    }


    private static class Tuple<K, V> {
        final K k;
        final V v;

        Tuple(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }
}

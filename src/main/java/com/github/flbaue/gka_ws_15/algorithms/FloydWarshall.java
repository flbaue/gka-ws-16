package com.github.flbaue.gka_ws_15.algorithms;

import com.github.flbaue.gka_ws_15.graph.*;

/**
 * Created by florian on 01/11/2016.
 */
public final class FloydWarshall {

    public static Path search(final Graph graph, final Node source, final Node target) {

        Matrix<Node, Double> distanceMatrix = new Matrix<>(graph.getNodes(), Double.POSITIVE_INFINITY);
        Matrix<Node, Node> transitMatrix = new Matrix<>(graph.getNodes(), null);

        graph.getEdges().stream().forEach(e -> {
            distanceMatrix.putValue(e.source, e.target, (double) e.weight); // get min edge
            transitMatrix.putValue(e.source, e.target, e.target);
        });


        graph.getNodes().forEach(k -> {
            graph.getNodes().forEach(i -> {
                graph.getNodes().forEach(j -> {
                    double alt = distanceMatrix.getValue(i, k) + distanceMatrix.getValue(k, j);
                    if (alt < distanceMatrix.getValue(i, j)) {
                        distanceMatrix.putValue(i, j, alt);
                        transitMatrix.putValue(i, j, transitMatrix.getValue(i, k));
                    }
                });
            });
        });


        // Collect shortest path
        Path path = new Path();
        path.reverse = false;

        path.addElement(source);
        Node currentNode = source;

        while (currentNode != target) {
            Node next = transitMatrix.getValue(currentNode, target);
            Edge edge = graph.getMinEdge(currentNode, next);

            path.addElement(edge);
            path.addElement(next);

            currentNode = next;
        }

        return path;
    }
}

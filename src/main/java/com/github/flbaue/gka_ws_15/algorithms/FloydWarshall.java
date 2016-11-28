package com.github.flbaue.gka_ws_15.algorithms;

import com.github.flbaue.gka_ws_15.graph.*;

/**
 * Created by florian on 01/11/2016.
 */
public final class FloydWarshall {

    public static long graphAccessCounter;

    public static Path search(final Graph graph, final Node source, final Node target) {

        graphAccessCounter = 0;

        Matrix<Node, Double> distanceMatrix = new Matrix<>(graph.getNodes(), Double.POSITIVE_INFINITY, 0.0); graphAccessCounter++;
        Matrix<Node, Node> transitMatrix = new Matrix<>(graph.getNodes(), null, null); graphAccessCounter++;

        graphAccessCounter++;
        graph.getEdges().forEach(e -> {
            if (e.weight < distanceMatrix.getValue(e.source, e.target)) {
                distanceMatrix.putValue(e.source, e.target, (double) e.weight);
                transitMatrix.putValue(e.source, e.target, e.target);
                graphAccessCounter++;
                if (!graph.isDirected) {
                    distanceMatrix.putValue(e.target, e.source, (double) e.weight);
                    transitMatrix.putValue(e.target, e.source, e.source);
                }
            }
        });

        graphAccessCounter++;
        graph.getNodes().forEach(k -> {
            graphAccessCounter++;
            graph.getNodes().forEach(i -> {
                graphAccessCounter++;
                graph.getNodes().forEach(j -> {
                    double alt = distanceMatrix.getValue(i, k) + distanceMatrix.getValue(k, j);
                    if (alt < distanceMatrix.getValue(i, j)) {
                        distanceMatrix.putValue(i, j, alt);
                        transitMatrix.putValue(i, j, transitMatrix.getValue(i, k));
                        graphAccessCounter++;
                        if (!graph.isDirected) {
                            distanceMatrix.putValue(j, i, alt);
                            transitMatrix.putValue(j, i, transitMatrix.getValue(j, k));
                        }
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
            Edge edge = graph.getMinEdge(currentNode, next); graphAccessCounter++;

            path.addElement(edge);
            path.addElement(next);

            currentNode = next;
        }

        return path;
    }
}

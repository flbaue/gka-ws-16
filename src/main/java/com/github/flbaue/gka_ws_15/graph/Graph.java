package com.github.flbaue.gka_ws_15.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by florian on 30/10/2016.
 */
public final class Graph {

    private final Map<String, Node> nodes = new HashMap<>();
    private final Map<String, Edge> edges = new HashMap<>();

    public boolean isDirected = false;

    public Node getNode(final String id) {
        return nodes.get(id);
    }

    public Edge getEdge(final String id) {
        return edges.get(id);
    }

    public void insertNode(final Node node) throws NodeAlreadyExistsException {
        if (!nodes.containsKey(node.id)) {
            nodes.put(node.id, node);
        } else {
            throw new NodeAlreadyExistsException();
        }
    }

    public void insertEdge(final Edge edge) throws EdgeAlreadyExistsException {
        if (!edges.containsKey(edge.id) && nodes.containsKey(edge.source.id) && nodes.containsKey(edge.target.id)) {
            edges.put(edge.id, edge);

            if (!edge.source.edges.contains(edge)) {
                edge.source.edges.add(edge);
            }

            if (!edge.target.edges.contains(edge)) {
                edge.target.edges.add(edge);
            }

        } else {
            throw new EdgeAlreadyExistsException();
        }
    }

    public Set<Node> getAdjacentNeighbors(final Node node) {
        return node.edges.stream()
                .filter(edge -> (edge.source != node && edge.target == node) || (edge.source == node && edge.target != node))
                .map(edge -> edge.source != node ? edge.source : edge.target)
                .collect(Collectors.toSet());
    }

    public Set<Node> getIncomingNeighbors(final Node node) {
        return node.edges.stream()
                .filter(edge -> edge.source != node && edge.target == node)
                .map(edge -> edge.source)
                .collect(Collectors.toSet());
    }

    public Set<Node> getOutgoingNeighbors(final Node node) {
        return node.edges.stream()
                .filter(edge -> edge.source == node && edge.target != node)
                .map(edge -> edge.target)
                .collect(Collectors.toSet());
    }

    public Set<Edge> getEdges(final Node source, final Node target) {
        return source.edges.stream()
                .filter(edge -> edge.source == source && edge.target == target)
                .collect(Collectors.toSet());
    }

    public Set<Node> getNodes(final Predicate<Node> filter) {
        return nodes.values().stream()
                .filter(filter)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder(edges.size() * 16);
        final String edgeSymbol = isDirected ? "->" : "--";

        edges.values().forEach(edge -> buffer
                .append(edge.source.id)
                .append(edgeSymbol)
                .append(edge.target.id)
                .append("(")
                .append(edge.id)
                .append("):")
                .append(edge.weight)
                .append(";\n")
        );

        nodes.values().forEach(node -> {
            if (node.edges.isEmpty()) {
                buffer.append(node.id).append(";\n");
            }
        });
        return buffer.toString();
    }

    public static class NodeAlreadyExistsException extends Exception {
    }

    public static class EdgeAlreadyExistsException extends Exception {
    }

}

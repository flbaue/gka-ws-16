package com.github.flbaue.gka_ws_15.graph;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by florian on 30/10/2016.
 */
public final class Graph {

    final Map<String, Node> nodes = new HashMap<>();
    final Map<String, Edge> edges = new HashMap<>();

    public boolean isDirected = false;

    public Node getNode(final String id) {
        return nodes.get(id);
    }

    public Edge getEdge(final String id) {
        return edges.get(id);
    }

    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(nodes.values());
    }

    public Collection<Edge> getEdges() {
        return Collections.unmodifiableCollection(edges.values());
    }

    public Edge getMinEdge(final Node source, final Node target) {
        Set<Edge> edges = getEdges(source, target);
        if (!isDirected) {
            edges.addAll(getEdges(target, source));
        }
        return edges.stream()
                .min(Comparator.comparingInt(e -> e.value))
                .get();
    }

    public int getNodesAmount() {
        return nodes.size();
    }

    public void insertNode(final Node node) throws NodeAlreadyExistsException {
        if (!nodes.containsKey(node.id)) {
            nodes.put(node.id, node);
        } else {
            throw new NodeAlreadyExistsException();
        }
    }

    public void insertEdge(final Edge edge) throws EdgeAlreadyExistsException, MissingNodeException {
        if (!nodes.containsKey(edge.source.id)) {
            throw new MissingNodeException("Missing Node " + edge.source.id);
        }

        if (!nodes.containsKey(edge.target.id)) {
            throw new MissingNodeException("Missing Node " + edge.target.id);
        }

        if (!edges.containsKey(edge.id)) {
            edges.put(edge.id, edge);

            if (!edge.source.edges.contains(edge)) {
                edge.source.edges.add(edge);
            }

            if (!edge.target.edges.contains(edge)) {
                edge.target.edges.add(edge);
            }

        } else {
            throw new EdgeAlreadyExistsException("Edge.id: " + edge.id);
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
                .append(edge.value)
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
        public EdgeAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class MissingNodeException extends Exception {
        public MissingNodeException(String message) {
            super(message);
        }
    }

}

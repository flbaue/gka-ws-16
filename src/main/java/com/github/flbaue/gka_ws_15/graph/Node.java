package com.github.flbaue.gka_ws_15.graph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by florian on 30/10/2016.
 */
public final class Node {
    public final String id;
    public final Set<Edge> edges = new HashSet<>();
    public int tag = -1;

    public Node(final String id) {
        Objects.requireNonNull(id, "Node ID cannot be null");

        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return id.equals(node.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

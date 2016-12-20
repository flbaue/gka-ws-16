package com.github.flbaue.gka_ws_15.graph;

import java.util.Objects;

/**
 * Created by florian on 30/10/2016.
 */
public final class Edge {

    public final String id;
    public final Node source;
    public final Node target;
    public int value;

    public Edge redge;

    public Edge(final String id, final Node source, final Node target) {
        this(id, source, target, 0);
    }

    public Edge(final String id, final Node source, final Node target, final int value) {
        Objects.requireNonNull(id, "Edge id cannot be null");
        Objects.requireNonNull(id, "Edge source cannot be null");
        Objects.requireNonNull(id, "Edge target cannot be null");

        this.id = id;
        this.source = source;
        this.target = target;
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        return id.equals(edge.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

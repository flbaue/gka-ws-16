package com.github.flbaue.gka_ws_15.graph;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by florian on 30/10/2016.
 */
public final class Path {
    private final LinkedList<Object> elements = new LinkedList<>();
    public boolean reverse = true;

    public void addElement(final Object element) {
        if (elements.size() % 2 == 0) {
            if (element instanceof Node) {
                elements.add(element);
            } else {
                throw new IllegalArgumentException("Element has to be a Node");
            }
        } else {
            if (element instanceof Edge) {
                elements.add(element);
            } else {
                throw new IllegalArgumentException("Element has to be an Edge");
            }
        }
    }

    public int getPathLength() {
        return (elements.size() - 1) / 2;
    }

    public int getTotalEdgeWeight() {
        int weight = 0;
        for (Object o : elements) {
            if (o instanceof Edge) {
                weight += ((Edge) o).weight;
            }
        }
        return weight;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder(elements.size() * 6);

        Iterator<Object> iterator = reverse ? elements.descendingIterator() : elements.iterator();
        while (iterator.hasNext()) {
            Object element = iterator.next();
            if (element instanceof Node) {
                Node node = (Node) element;
                buffer.append("[").append(node.id).append("]");
            } else {
                Edge edge = (Edge) element;
                buffer.append(" --").append(edge.id).append("-> ");
            }
        }
        return buffer.toString();
    }
}

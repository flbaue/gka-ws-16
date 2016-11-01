package com.github.flbaue.gka_ws_15.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by florian on 01/11/2016.
 */
public class Matrix<K,V> {
    private final Map<K, Map<K, V>> distanceMatrix;

    public Matrix(final Collection<K> collection, V initialValue) {
        this.distanceMatrix = new HashMap<>(collection.size());

        collection.stream().forEach(c1 -> {

            final HashMap<K, V> value = new HashMap<>(collection.size());

            collection.stream().forEach(c2 -> {
                value.put(c2, initialValue);
            });

            distanceMatrix.put(c1, value);
        });
    }

    public V getValue(K x, K y) {
        return distanceMatrix.get(x).get(y);
    }

    public void putValue(K x, K y, V value) {
        distanceMatrix.get(x).put(y, value);
    }
}

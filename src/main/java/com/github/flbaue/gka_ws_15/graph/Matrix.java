package com.github.flbaue.gka_ws_15.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by florian on 01/11/2016.
 */
public class Matrix<K, V> {
    private final Map<K, Map<K, V>> valueMatrix;

    public Matrix(final Collection<K> collection, final V initialValue, final V initialValueWhenEqual) {
        this.valueMatrix = new HashMap<>(collection.size());

        collection.stream().forEach(c1 -> {

            final HashMap<K, V> value = new HashMap<>(collection.size());

            collection.stream().forEach(c2 -> {

                if (c1.equals(c2)) {
                    value.put(c2, initialValueWhenEqual);
                } else {
                    value.put(c2, initialValue);
                }
            });

            valueMatrix.put(c1, value);
        });
    }

    public V getValue(K x, K y) {
        return valueMatrix.get(x).get(y);
    }

    public void putValue(K x, K y, V value) {
        valueMatrix.get(x).put(y, value);
    }
}

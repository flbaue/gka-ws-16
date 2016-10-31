package com.github.flbaue.gka_ws_15.graph;

/**
 * Created by florian on 31/10/2016.
 */
public class GraphUtil {

    public static void cleanTags(Graph graph) {
        for(Node node : graph.nodes.values()) {
            node.tag = -1;
        }
    }
}

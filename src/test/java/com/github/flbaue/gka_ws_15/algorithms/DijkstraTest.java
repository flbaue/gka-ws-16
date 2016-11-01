package com.github.flbaue.gka_ws_15.algorithms;

import com.github.flbaue.gka_ws_15.graph.Graph;
import com.github.flbaue.gka_ws_15.graph.GraphIO;
import com.github.flbaue.gka_ws_15.graph.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by florian on 01/11/2016.
 */
public class DijkstraTest {

    @Test
    public void search() throws Exception {
        Graph graph = GraphIO.read(new File("./testGraphs/graph_medium_directed.gka"));

        Path path = Dijkstra.search(graph,graph.getNode("s"),graph.getNode("t"));

        System.out.println(path.toString());
    }

}
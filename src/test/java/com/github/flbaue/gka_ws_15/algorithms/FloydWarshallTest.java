package com.github.flbaue.gka_ws_15.algorithms;

import com.github.flbaue.gka_ws_15.graph.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by florian on 01/11/2016.
 */
public class FloydWarshallTest {

    @Test
    public void shouldFindShortestPathInMediumDirectedGraph() throws Exception {
        Graph graph = GraphIO.read(new File("./testGraphs/graph_medium_directed.gka"));

        Path path = FloydWarshall.search(graph, graph.getNode("s"), graph.getNode("t"));

        Assert.assertEquals(4, path.getTotalEdgeWeight());
    }

    @Test
    public void shouldFindShortestPathInMediumUndirectedGraph() throws Exception {
        Graph graph = GraphIO.read(new File("./testGraphs/graph_medium_undirected.gka"));

        Path path = FloydWarshall.search(graph, graph.getNode("s"), graph.getNode("t"));

        Assert.assertEquals(3, path.getTotalEdgeWeight());
    }

    @Test
    public void shouldFindShortestPathInBigGeneratedGraph() throws Exception {
        Graph graph = GraphGenerator.generateGraph(1_000, 3_000, true, 10);
        Node s = new Node("s");
        Node t = new Node("t");
        graph.insertNode(s);
        graph.insertNode(t);
        GraphGenerator.insertPath(graph, 10, s, t);

        Path path = FloydWarshall.search(graph, graph.getNode("s"), graph.getNode("t"));

        Assert.assertEquals(0, path.getTotalEdgeWeight());
        Assert.assertEquals(10, path.getPathLength());
    }

}
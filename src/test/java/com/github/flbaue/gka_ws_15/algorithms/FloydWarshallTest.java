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

    @Test
    public void negativeEdges() throws Exception {
        Graph graph = new Graph();

        graph.isDirected = true;

        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");

        Edge e1 = new Edge("e1", a, b, -1);
//        Edge e2 = new Edge("e2", b, a, -1);
        Edge e3 = new Edge("e3", b, c, 1);

        graph.insertNode(a);
        graph.insertNode(b);
        graph.insertNode(c);

        graph.insertEdge(e1);
//        graph.insertEdge(e2);
        graph.insertEdge(e3);

        Path path = FloydWarshall.search(graph, a, c);

        System.out.printf("\nFloydWarshall");
        System.out.println("Graph access counter: " + Dijkstra.graphAccessCounter);
        System.out.println("Total edge value: " + path.getTotalEdgeWeight());
        System.out.println(path);
        System.out.println("Path length: " + path.getPathLength());

    }
}
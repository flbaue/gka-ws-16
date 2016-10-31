package com.github.flbaue.gka_ws_15.algorithms;

import com.github.flbaue.gka_ws_15.graph.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by florian on 30/10/2016.
 */
public class BreadthFirstSearchTest {

    private Graph graph;

    @Before
    public void setUp() throws Exception {
        graph = new Graph();
    }

    @Test
    public void shouldNotTagDirectedTarget() throws Exception {
        graph = new GraphIO().read(new File("./testGraphs/graph_small_directed.gka"));

        BreadthFirstSearch.search(graph, "S", "B");

        Assert.assertEquals(-1, graph.getNode("B").tag);

        Path path = BreadthFirstSearch.getPath(graph, "S", "B");

        Assert.assertEquals("[S]", path.toString());
    }

    @Test
    public void shouldTagDirectedTargetWith4() throws Exception {
        graph = new GraphIO().read(new File("./testGraphs/graph_medium_directed.gka"));

        BreadthFirstSearch.search(graph, "s", "t");

        Assert.assertEquals(4, graph.getNode("t").tag);

        Path path = BreadthFirstSearch.getPath(graph, "s", "t");

        Assert.assertEquals("[s] --e1-> [a] --e3-> [b] --e6-> [c] --e10-> [t]", path.toString());
    }

    @Test
    public void shouldFindUndirectedPathOver3Edges() throws Exception {
        shouldTagUndirectedTargetWith3();

        Path path = BreadthFirstSearch.getPath(graph, "s", "t");

        Assert.assertEquals("[s] --e1-> [a] --e3-> [b] --e7-> [t]", path.toString());
    }

    @Test
    public void shouldTagUndirectedTargetWith3() throws Exception {
        Node nS = new Node("s");
        Node nA = new Node("a");
        Node nB = new Node("b");
        Node nC = new Node("c");
        Node nD = new Node("d");
        Node nE = new Node("e");
        Node nF = new Node("f");
        Node nT = new Node("t");

        graph.insertNode(nS);
        graph.insertNode(nA);
        graph.insertNode(nB);
        graph.insertNode(nC);
        graph.insertNode(nD);
        graph.insertNode(nE);
        graph.insertNode(nF);
        graph.insertNode(nT);

        Edge e1 = new Edge("e1", nS, nA);
        Edge e2 = new Edge("e2", nS, nF);
        Edge e3 = new Edge("e3", nA, nB);
        Edge e4 = new Edge("e4", nA, nD);
        Edge e5 = new Edge("e5", nA, nE);
        Edge e6 = new Edge("e6", nB, nC);
        Edge e7 = new Edge("e7", nB, nT);
        Edge e8 = new Edge("e8", nC, nD);
        Edge e9 = new Edge("e9", nC, nE);
        Edge e10 = new Edge("e10", nC, nT);
        Edge e11 = new Edge("e11", nD, nE);
        Edge e12 = new Edge("e12", nD, nF);
        Edge e13 = new Edge("e13", nE, nF);
        Edge e14 = new Edge("e14", nE, nT);

        graph.insertEdge(e1);
        graph.insertEdge(e2);
        graph.insertEdge(e3);
        graph.insertEdge(e4);
        graph.insertEdge(e5);
        graph.insertEdge(e6);
        graph.insertEdge(e7);
        graph.insertEdge(e8);
        graph.insertEdge(e9);
        graph.insertEdge(e10);
        graph.insertEdge(e11);
        graph.insertEdge(e12);
        graph.insertEdge(e13);
        graph.insertEdge(e14);

        graph.isDirected = false;

        BreadthFirstSearch.search(graph, "s", "t");

        Assert.assertEquals(0, nS.tag);
        Assert.assertEquals(1, nA.tag);
        Assert.assertEquals(2, nB.tag);
        Assert.assertEquals(3, nC.tag);
        Assert.assertEquals(2, nD.tag);
        Assert.assertEquals(2, nE.tag);
        Assert.assertEquals(1, nF.tag);
        Assert.assertEquals(3, nT.tag);
    }

}
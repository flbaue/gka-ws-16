package com.github.flbaue.gka_ws_15.graph;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by florian on 31/10/2016.
 */
public class GraphIOTest {

    @Test
    public void shouldReadDirectedGraph() throws Exception {

        File graphFile = new File("testGraphs/graph_small_directed.gka");

        Graph graph = GraphIO.read(graphFile);

        Assert.assertNotNull(graph.getNode("s"));
        Assert.assertNotNull(graph.getNode("t"));
        Assert.assertNotNull(graph.getNode("a"));
        Assert.assertNotNull(graph.getNode("b"));

        Assert.assertNotNull(graph.getEdge("e1"));
        Assert.assertNotNull(graph.getEdge("e2"));
        Assert.assertNotNull(graph.getEdge("e3"));

        Node nS = graph.getNode("s");
        Node nT = graph.getNode("t");
        Node nA = graph.getNode("a");
        Node nB = graph.getNode("b");

        Edge e1 = graph.getEdge("e1");
        Edge e2 = graph.getEdge("e2");
        Edge e3 = graph.getEdge("e3");

        Assert.assertTrue(nS.edges.contains(e1));
        Assert.assertTrue(nS.edges.contains(e2));

        Assert.assertTrue(nT.edges.contains(e1));
        Assert.assertTrue(nT.edges.contains(e3));

        Assert.assertTrue(nA.edges.contains(e2));
        Assert.assertTrue(nA.edges.contains(e3));

        Assert.assertTrue(nB.edges.isEmpty());

        Assert.assertTrue(e1.source == nS);
        Assert.assertTrue(e1.target == nT);
        Assert.assertTrue(e1.weight == 99);

        Assert.assertTrue(e2.source == nS);
        Assert.assertTrue(e2.target == nA);
        Assert.assertTrue(e2.weight == 0);

        Assert.assertTrue(e3.source == nA);
        Assert.assertTrue(e3.target == nT);
        Assert.assertTrue(e3.weight == 1);
    }

    @Test
    public void shouldWriteFile() throws Exception {

//        String tempDir = System.getProperty("java.io.tmpdir");

        File testFile = new File("./shouldWriteFile.gka");

        if (testFile.isFile()) {
            testFile.delete();
        }

        Node nS = new Node("s");
        Node nT = new Node("t");
        Edge e1 = new Edge("e1", nS, nT, 99);

        Graph graph = new Graph();
        graph.insertNode(nS);
        graph.insertNode(nT);
        graph.insertEdge(e1);


        GraphIO.write(graph, testFile);

        Assert.assertTrue(testFile.isFile());

        testFile.delete();
    }

    @Test
    public void shouldWriteGraph() throws Exception {

//        String tempDir = System.getProperty("java.io.tmpdir");

        File testFile = new File("./shouldWriteGraph.gka");

        if (testFile.isFile()) {
            testFile.delete();
        }

        Node nS = new Node("s");
        Node nA = new Node("a");
        Node nT = new Node("t");
        Node nB = new Node("b");
        Edge e1 = new Edge("e1", nS, nT, 99);
        Edge e2 = new Edge("e2", nS, nA, 0);
        Edge e3 = new Edge("e3", nA, nT, 1);

        Graph graph = new Graph();
        graph.insertNode(nS);
        graph.insertNode(nA);
        graph.insertNode(nT);
        graph.insertNode(nB);
        graph.insertEdge(e1);
        graph.insertEdge(e2);
        graph.insertEdge(e3);

        Set<String> expectedLines = new HashSet<>();
        expectedLines.add("s--t(e1):99;");
        expectedLines.add("s--a(e2):0;");
        expectedLines.add("a--t(e3):1;");
        expectedLines.add("b;");

        GraphIO.write(graph, testFile);

        BufferedReader reader = new BufferedReader(new FileReader(testFile));

        reader.lines().forEach(line -> {
            Assert.assertTrue("Unexpected line: '" + line + "'", expectedLines.contains(line));
            expectedLines.remove(line);
        });

        Assert.assertTrue("Not all expected lines were found", expectedLines.isEmpty());

        testFile.delete();
    }

    @Test
    public void shouldWriteDirectedGraph() throws Exception {

//        String tempDir = System.getProperty("java.io.tmpdir");

        File testFile = new File("./shouldWriteGraph.gka");

        if (testFile.isFile()) {
            testFile.delete();
        }

        Node nS = new Node("s");
        Node nA = new Node("a");
        Node nT = new Node("t");
        Node nB = new Node("b");
        Edge e1 = new Edge("e1", nS, nT, 99);
        Edge e2 = new Edge("e2", nS, nA, 0);
        Edge e3 = new Edge("e3", nA, nT, 1);

        Graph graph = new Graph();
        graph.isDirected = true;
        graph.insertNode(nS);
        graph.insertNode(nA);
        graph.insertNode(nT);
        graph.insertNode(nB);
        graph.insertEdge(e1);
        graph.insertEdge(e2);
        graph.insertEdge(e3);

        Set<String> expectedLines = new HashSet<>();
        expectedLines.add("s->t(e1):99;");
        expectedLines.add("s->a(e2):0;");
        expectedLines.add("a->t(e3):1;");
        expectedLines.add("a->t(e3):1;");
        expectedLines.add("b;");

        GraphIO.write(graph, testFile);

        BufferedReader reader = new BufferedReader(new FileReader(testFile));

        reader.lines().forEach(line -> {
            Assert.assertTrue("Unexpected line: '" + line + "'", expectedLines.contains(line));
            expectedLines.remove(line);
        });

        Assert.assertTrue("Not all expected lines were found", expectedLines.isEmpty());

        testFile.delete();
    }

}
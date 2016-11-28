package com.github.flbaue.gka_ws_15.algorithms;

import com.github.flbaue.gka_ws_15.graph.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by florian on 01/11/2016.
 */
public class ComparisonTests {

    //@Test
    public void compareGraphWith_500_NodesAnd_1000_Edges() throws Exception {
        Graph graph = GraphGenerator.generateGraph(500, 1_000, true, 10);
        Node source = new Node("s");
        graph.insertNode(source);
        Node target = new Node("t");
        graph.insertNode(target);
        GraphGenerator.insertPath(graph, 25, source, target);

        System.out.println("Setup done");
        System.out.println("Start BFS");

        long startedBFS = System.currentTimeMillis();
        Path pathBFS = BreadthFirstSearch.search(graph, source, target);
        long finishedBFS = System.currentTimeMillis();

        System.out.println("Finished BFS");
        System.out.println("Start Dijkstra");

        long startedDijkstra = System.currentTimeMillis();
        Path pathDijkstra = Dijkstra.search(graph, source, target);
        long finishedDijkstra = System.currentTimeMillis();

        System.out.println("Finished Dijkstra");
        System.out.println("Start FW");

        long startedFW = System.currentTimeMillis();
        Path pathFW = FloydWarshall.search(graph, source, target);
        long finishedFW = System.currentTimeMillis();

        System.out.println("Finished FW");

        System.out.println("\nPaths");
        System.out.println("BFS:      " + "(" + pathBFS.getTotalEdgeWeight() + "): " + pathBFS.toString());
        System.out.println("Dijkstra: " + "(" + pathDijkstra.getTotalEdgeWeight() + "): " + pathDijkstra.toString());
        System.out.println("FW:       " + "(" + pathFW.getTotalEdgeWeight() + "): " + pathFW.toString());

        System.out.println("\nTime");
        System.out.println("BFS:      " + (finishedBFS - startedBFS));
        System.out.println("Dijkstra: " + (finishedDijkstra - startedDijkstra));
        System.out.println("FW:       " + (finishedFW - startedFW));

        Assert.assertEquals(0, pathDijkstra.getTotalEdgeWeight());
        Assert.assertEquals(0, pathFW.getTotalEdgeWeight());
    }

    @Test
    public void compare_Dijkstar_and_FW_with_graph3() throws Exception {
        Graph graph03 = GraphIO.read(new File("./testGraphs/graph03.gka.txt"));

        Node s = graph03.getNode("Kiel");
        Node t = graph03.getNode("Oldenburg");

        Path pathD = Dijkstra.search(graph03, s, t);
        Path pathFW = FloydWarshall.search(graph03, s, t);

        System.out.printf("\nDijkstra");
        System.out.println("Graph access counter: " + Dijkstra.graphAccessCounter);
        System.out.println("Total edge weight: " + pathD.getTotalEdgeWeight());
        System.out.println(pathD);

        System.out.printf("\nFloydWarshall");
        System.out.println("Graph access counter: " + FloydWarshall.graphAccessCounter);
        System.out.println("Total edge weight: " + pathFW.getTotalEdgeWeight());
        System.out.println(pathFW);


        Assert.assertEquals(pathD.getTotalEdgeWeight(), pathFW.getTotalEdgeWeight());
    }

    @Test
    public void compare_Djikstra_and_FW_with_BIG() throws Exception {
        Graph graph = GraphGenerator.generateGraph(100, 2500, true, 10);

        Node s = new Node("s1");
        Node t = new Node("t1");

        graph.insertNode(s);
        graph.insertNode(t);

        GraphGenerator.insertPath(graph, 25, s, t);

        Path pathD = Dijkstra.search(graph, s, t);
        Path pathF = FloydWarshall.search(graph, s, t);

        Assert.assertEquals(pathD.getTotalEdgeWeight(), pathF.getTotalEdgeWeight());
        Assert.assertEquals(0, pathD.getTotalEdgeWeight());
        Assert.assertEquals(25, pathD.getPathLength());
        Assert.assertEquals(25, pathF.getPathLength());

        System.out.printf("\nDijkstra");
        System.out.println("Graph access counter: " + Dijkstra.graphAccessCounter);
        System.out.println("Total edge weight: " + pathD.getTotalEdgeWeight());
        System.out.println(pathD);
        System.out.println("Path length: " + pathD.getPathLength());

        System.out.printf("\nFloydWarshall");
        System.out.println("Graph access counter: " + FloydWarshall.graphAccessCounter);
        System.out.println("Total edge weight: " + pathF.getTotalEdgeWeight());
        System.out.println(pathF);
        System.out.println("Path length: " + pathF.getPathLength());
    }
}

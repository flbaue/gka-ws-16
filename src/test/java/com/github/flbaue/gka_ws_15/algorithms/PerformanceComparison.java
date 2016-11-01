package com.github.flbaue.gka_ws_15.algorithms;

import com.github.flbaue.gka_ws_15.graph.Graph;
import com.github.flbaue.gka_ws_15.graph.GraphGenerator;
import com.github.flbaue.gka_ws_15.graph.Node;
import com.github.flbaue.gka_ws_15.graph.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by florian on 01/11/2016.
 */
public class PerformanceComparison {

    @Test
    public void compareGraphWith_500_NodesAnd_1_000_Edges() throws Exception {
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
}

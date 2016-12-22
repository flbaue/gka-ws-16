package com.github.flbaue.gka_ws_15.algorithms;

import com.github.flbaue.gka_ws_15.graph.Graph;
import com.github.flbaue.gka_ws_15.graph.GraphGenerator;
import com.github.flbaue.gka_ws_15.graph.GraphIO;
import com.github.flbaue.gka_ws_15.graph.Node;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by florian on 18/12/2016.
 */
public class FlowTest {

    @Test
    public void simpleComparisonTest() throws Exception {
        Graph graph4 = GraphIO.read(new File("./testGraphs/flowGraph_small"));

        Node source = graph4.getNode("s");
        Node target = graph4.getNode("t");

        double maxFlow = FordFulkerson.maxFlow(graph4, source, target);
        System.out.println("1) Max Flow: " + maxFlow);


        Graph graph4_2 = GraphIO.read(new File("./testGraphs/flowGraph_small"));
        Node source_2 = graph4_2.getNode("s");
        Node target_2 = graph4_2.getNode("t");

        double maxFlow_2 = EdmondsKarp.maxFlow(graph4_2, source_2, target_2);
        System.out.println("2) Max Flow: " + maxFlow_2);

        Assert.assertEquals(maxFlow, maxFlow_2, 0.1);
        Assert.assertEquals(30.0, maxFlow, 0.1);
    }

    @Test
    public void Graph4ComparsionTest() throws Exception {
        Graph graph4 = GraphIO.read(new File("./testGraphs/graph04.gka.txt"));

        Node source = graph4.getNode("q");
        Node target = graph4.getNode("s");

        long timeStart_1 = System.currentTimeMillis();
        double maxFlow = FordFulkerson.maxFlow(graph4, source, target);
        long timeEnd_1 = System.currentTimeMillis();
        System.out.println("1) Max Flow: " + maxFlow);
        System.out.println("1) Time: " + (timeEnd_1 - timeStart_1));


        Graph graph4_2 = GraphIO.read(new File("./testGraphs/graph04.gka.txt"));
        Node source_2 = graph4_2.getNode("q");
        Node target_2 = graph4_2.getNode("s");

        long timeStart_2 = System.currentTimeMillis();
        double maxFlow_2 = EdmondsKarp.maxFlow(graph4_2, source_2, target_2);
        long timeEnd_2 = System.currentTimeMillis();
        System.out.println("2) Max Flow: " + maxFlow_2);
        System.out.println("2) Time: " + (timeEnd_2 - timeStart_2));

        Assert.assertEquals(maxFlow, maxFlow_2, 0.1);
        Assert.assertEquals(25.0, maxFlow, 0.1);
    }

    @Test
    public void BigGraphComparsionTest() throws Exception {

        System.out.println("Start:");

        Graph graph_1 = GraphIO.read(new File("./testGraphs/bigFlowGraph_2.gka"));
        Node s_1 = graph_1.getNode("source");
        Node t_1 = graph_1.getNode("target");
        long timeStart_1 = System.currentTimeMillis();
        double maxFlow_1 = FordFulkerson.maxFlow(graph_1, s_1, t_1);
        long timeEnd_1 = System.currentTimeMillis();
        System.out.println("1) Max Flow: " + maxFlow_1);
        System.out.println("1) Time: " + (timeEnd_1 - timeStart_1));


        Graph graph_2 = GraphIO.read(new File("./testGraphs/bigFlowGraph_2.gka"));
        Node s_2 = graph_2.getNode("source");
        Node t_2 = graph_2.getNode("target");
        long timeStart_2 = System.currentTimeMillis();
        double maxFlow_2 = EdmondsKarp.maxFlow(graph_2, s_2, t_2);
        long timeEnd_2 = System.currentTimeMillis();
        System.out.println("2) Max Flow: " + maxFlow_2);
        System.out.println("1) Time: " + (timeEnd_2 - timeStart_2));

        Assert.assertEquals(maxFlow_1, maxFlow_2, 0.1);
    }


    @Test
    public void Big2GraphComparsionTest() throws Exception {
        Graph graph = GraphGenerator.generateGraph(10, 50, true, 10);
        Node source = new Node("source");
        Node target = new Node("target");
        graph.insertNode(source);
        graph.insertNode(target);

        GraphGenerator.insertFlow(graph, 5, source, target);

        GraphIO.write(graph, new File("./testGraphs/bigFlowGraph_1.gka"));


        System.out.println("Start:");

        Graph graph_1 = GraphIO.read(new File("./testGraphs/bigFlowGraph_1.gka"));
        Node s_1 = graph_1.getNode("source");
        Node t_1 = graph_1.getNode("target");
        long timeStart_1 = System.currentTimeMillis();
        double maxFlow_1 = FordFulkerson.maxFlow(graph_1, s_1, t_1);
        long timeEnd_1 = System.currentTimeMillis();
        System.out.println("1) Max Flow: " + maxFlow_1);
        System.out.println("1) Time: " + (timeEnd_1 - timeStart_1));


        Graph graph_2 = GraphIO.read(new File("./testGraphs/bigFlowGraph_1.gka"));
        Node s_2 = graph_2.getNode("source");
        Node t_2 = graph_2.getNode("target");
        long timeStart_2 = System.currentTimeMillis();
        double maxFlow_2 = EdmondsKarp.maxFlow(graph_2, s_2, t_2);
        long timeEnd_2 = System.currentTimeMillis();
        System.out.println("2) Max Flow: " + maxFlow_2);
        System.out.println("1) Time: " + (timeEnd_2 - timeStart_2));

        Assert.assertEquals(maxFlow_1, maxFlow_2, 0.1);
    }

    @Test
    public void Big3GraphEdmondsKarpTest() throws Exception {
        Graph graph = GraphGenerator.generateGraph(50, 800, true, 10);
        Node source = new Node("source");
        Node target = new Node("target");
        graph.insertNode(source);
        graph.insertNode(target);

        GraphGenerator.insertFlow(graph, 5, source, target);

        GraphIO.write(graph, new File("./testGraphs/bigFlowGraph_3.gka"));


        System.out.println("Start:");




        Graph graph_2 = GraphIO.read(new File("./testGraphs/bigFlowGraph_3.gka"));
        Node s_2 = graph_2.getNode("source");
        Node t_2 = graph_2.getNode("target");
        long timeStart_2 = System.currentTimeMillis();
        double maxFlow_2 = EdmondsKarp.maxFlow(graph_2, s_2, t_2);
        long timeEnd_2 = System.currentTimeMillis();
        System.out.println("2) Max Flow: " + maxFlow_2);
        System.out.println("1) Time: " + (timeEnd_2 - timeStart_2));

    }

}
package com.github.flbaue.gka_ws_15.graph;

import com.github.flbaue.gka_ws_15.algorithms.BreadthFirstSearch;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by florian on 31/10/2016.
 */
public class GraphGeneratorTest {

    private GraphGenerator generator = new GraphGenerator();

    @Test
    public void shouldGenerateGraphWith3NodesAnd2Edges() throws Exception {
        Graph graph = generator.generateGraph(3, 2, false, 0);
        Assert.assertFalse(graph.isDirected);

        Assert.assertEquals(3, graph.nodes.size());
        Assert.assertEquals(2, graph.edges.size());
    }

    @Test
    public void shouldInsertShorterPathInBigGraph() throws Exception {
        Graph graph;

        do {
            graph = generator.generateGraph(5000, 10_000, false, 0);
            BreadthFirstSearch.search(graph, "n0", "n1");
        } while (graph.getNode("n1").tag < 3);

        GraphUtil.cleanTags(graph);

        generator.insertPath(graph, 2, graph.getNode("n0"), graph.getNode("n1"));
        BreadthFirstSearch.search(graph, "n0", "n1");

        Assert.assertEquals(2, graph.getNode("n1").tag);
    }

    @Test
    public void shouldInsertPathWithOneHop() throws Exception {
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");

        Graph graph = new Graph();
        graph.insertNode(a);
        graph.insertNode(b);
        graph.insertNode(c);

        generator.insertPath(graph, 2, a, c);

        Assert.assertEquals(2, graph.edges.size());

        for (Edge edge : graph.edges.values()) {
            if (edge.source == a) {
                Assert.assertEquals(b, edge.target);
            } else {
                Assert.assertEquals(b, edge.source);
                Assert.assertEquals(c, edge.target);
            }
        }
    }

}
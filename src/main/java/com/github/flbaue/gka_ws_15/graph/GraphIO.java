package com.github.flbaue.gka_ws_15.graph;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by florian on 31/10/2016.
 */
public class GraphIO {

    public static final String SOURCE_NAME_PATTERN = "^\\w+";
    public static final String TARGET_NAME_PATTERN = "-(-|>)\\w+";
    public static final String EDGE_NAME_PATTERN = "\\(\\w+\\)";
    public static final String EDGE_WEIGHT_PATTERN = ":\\d+";
    public static final String FULL_LINE_PATTERN = SOURCE_NAME_PATTERN + "(" + TARGET_NAME_PATTERN + ")?(" + EDGE_NAME_PATTERN + ")?(" + EDGE_WEIGHT_PATTERN + ")?;$";
    public static final String WHITE_SPACE_PATTERN = "\\s+";

    public Graph read(final File file) throws IOException {

        if (file == null || !file.isFile()) {
            throw new IllegalArgumentException("ERROR GraphIO::read file does not exists");
        }

        final Pattern sourceNamePattern = Pattern.compile(SOURCE_NAME_PATTERN);
        final Pattern targetNamePattern = Pattern.compile(TARGET_NAME_PATTERN);
        final Pattern edgeNamePattern = Pattern.compile(EDGE_NAME_PATTERN);
        final Pattern edgeWeightPattern = Pattern.compile(EDGE_WEIGHT_PATTERN);
        final Pattern fullLinePattern = Pattern.compile(FULL_LINE_PATTERN);

        final AtomicInteger edgeCounter = new AtomicInteger(0);

        final Graph graph = new Graph();

        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader reader = new BufferedReader(fileReader);

            reader.lines().forEach(line -> {

                line = line.replaceAll(WHITE_SPACE_PATTERN, "");


                if (!fullLinePattern.matcher(line).matches()) {
                    System.out.println("WARNING GraphIO::read skipped line because it does not match pattern");
                    return;
                }


                Matcher sourceNameMatcher = sourceNamePattern.matcher(line);
                sourceNameMatcher.find();
                String sourceName = sourceNameMatcher.group();
                Node source = new Node(sourceName);


                Node target = null;
                Matcher targetNameMatcher = targetNamePattern.matcher(line);
                if (targetNameMatcher.find()) {
                    String targetNamePart = targetNameMatcher.group();
                    if (!targetNamePart.isEmpty()) {
                        String targetName = targetNamePart.substring(2);
                        target = new Node(targetName);
                    }

                    // If one edge is directed, the whole graph is directed
                    graph.isDirected |= targetNamePart.contains(">");
                }


                String edgeName = null;
                Matcher edgeNameMatcher = edgeNamePattern.matcher(line);
                if (edgeNameMatcher.find()) {
                    String edgeNamePart = edgeNameMatcher.group();
                    if (!edgeNamePart.isEmpty()) {
                        edgeName = edgeNamePart.substring(1, edgeNamePart.indexOf(")"));
                    }
                }


                int edgeWeight = 0;
                Matcher edgeWeightMatcher = edgeWeightPattern.matcher(line);
                if (edgeWeightMatcher.find()) {
                    String edgeWeightPart = edgeWeightMatcher.group();
                    if (!edgeWeightPart.isEmpty()) {
                        edgeWeight = Integer.parseInt(edgeWeightPart.substring(1));
                    }
                }


                // add nodes to graph
                try {
                    graph.insertNode(source);
                } catch (Graph.NodeAlreadyExistsException e) {
                    System.out.println("INFO GraphIO::read node with id '" + sourceName + "' already exists");
                    source = graph.getNode(source.id);
                }

                try {
                    if (target != null) {
                        graph.insertNode(target);
                    }
                } catch (Graph.NodeAlreadyExistsException e) {
                    System.out.println("INFO GraphIO::read node with id '" + sourceName + "' already exists");
                    target = graph.getNode(target.id);
                }


                // Generate edge if if necessary
                if (edgeName == null || edgeName.isEmpty()) {
                    edgeName = "auto_id_" + edgeCounter.incrementAndGet();
                }


                // add edge to graph
                try {

                    if (target != null) {
                        Edge edge = new Edge(edgeName, source, target, edgeWeight);
                        graph.insertEdge(edge);
                    }

                } catch (Graph.EdgeAlreadyExistsException e) {
                    System.out.println("INFO GraphIO::read edge with id '" + edgeName + "' already exists");
                }
            });


        }

        return graph;
    }

    public void write(final Graph graph, final File file) throws IOException {

        try (FileWriter fileWriter = new FileWriter(file)) {
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(graph.toString());
            writer.flush();

        }
    }
}

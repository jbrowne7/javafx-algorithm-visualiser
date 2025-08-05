package tech.jamesabrowne.visualiser.util;

import tech.jamesabrowne.visualiser.model.Graph;
import tech.jamesabrowne.visualiser.model.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphBuilder {

    private static final int MAX_NODES = 10;
    private static final int MIN_NODES = 4;
    private static final int MAX_EDGES = 2 * MAX_NODES;
    private static final int MAX_WEIGHT = 100;
    private static final int MIN_WEIGHT = 0;

    public static Graph build() {

        /***
         Build method constraints:
         - All node IDs are unique
         - No edges from a node to itself
         - weights must be => 0
         - no more than `n` number of nodes and number of nodes must be > 10
         ***/

        Graph graph = new Graph();

        List<Node> nodes = new ArrayList<>();
        Random random = new Random();
        int nodeCount = MIN_NODES + random.nextInt(MAX_NODES - MIN_NODES);

        // Create random number of nodes with n >= MIN_NODES and < MAX_NODES
        for (int i = 0; i < nodeCount; i++) {
            Node node = new Node("N" + i);
            nodes.add(node);
            graph.addNode("N" + i);
        }

        // Loop through nodes and select a random node and connect it to an already connected node
        // Do this initial loop to ensure graph connectivity
        for (int i = 1; i < nodeCount; i++) {
            Node from = nodes.get(random.nextInt(i));
            Node to = nodes.get(i);
            int weight = MIN_WEIGHT + random.nextInt(MAX_WEIGHT - MIN_WEIGHT);
            graph.addEdge(from.getId(), to.getId(), weight);
        }

        int maxPossibleEdges = nodeCount * (nodeCount - 1);
        int maxEdges = Math.min(MAX_EDGES, maxPossibleEdges);
        int totalEdgesToAdd = (nodeCount - 1) + random.nextInt(maxEdges - (nodeCount - 1));
        int edgesAdded = nodeCount - 1;

        while (edgesAdded < totalEdgesToAdd) {
            Node from = nodes.get(random.nextInt(nodeCount));
            Node to = nodes.get(random.nextInt(nodeCount));

            if (from.equals(to) || graph.hasEdge(from.getId(), to.getId())) {
                continue;
            }

            int weight = MIN_WEIGHT + random.nextInt(MAX_WEIGHT - MIN_WEIGHT);
            graph.addEdge(from.getId(), to.getId(), weight);
            edgesAdded++;
        }

        return graph;
    }
}

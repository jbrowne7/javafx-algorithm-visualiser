package tech.jamesabrowne.visualiser.algorithm;

import tech.jamesabrowne.visualiser.model.Edge;
import tech.jamesabrowne.visualiser.model.Graph;
import tech.jamesabrowne.visualiser.model.Node;
import tech.jamesabrowne.visualiser.model.StepResult;

import java.util.*;

/**
 * Holds the algorithm logic for Dijkstra's algo
 */
public class Dijkstra extends Algorithm {

    private final Graph graph;
    private final PriorityQueue<NodeEntry> queue;
    private final Map<String, Integer> distances;
    private final Set<String> visited;

    private Node currentNode = null;
    private Iterator<Edge> edgeIterator = null;
    private String currentNodeId = null;


    private String lastProcessedNode;

    public Dijkstra(Graph graph, String startNodeId) {
        this.graph = graph;
        this.queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        this.distances = new HashMap<>();
        this.visited = new HashSet<>();

        for (Node node : graph.getAllNodes()) {
            distances.put(node.getId(), Integer.MAX_VALUE);
        }
        distances.put(startNodeId, 0);
        queue.add(new NodeEntry(startNodeId, 0));
    }

    @Override
    public StepResult step() {
        while ((edgeIterator == null || !edgeIterator.hasNext()) && !queue.isEmpty()) {
            NodeEntry current = queue.poll();

            if (visited.contains(current.nodeId)) {
                continue;
            }

            visited.add(current.nodeId);
            currentNodeId = current.nodeId;
            currentNode = graph.getNode(currentNodeId);
            edgeIterator = currentNode.getEdgeList().iterator();
        }

        if (edgeIterator == null || !edgeIterator.hasNext()) {
            return null;
        }

        Edge edge = edgeIterator.next();
        String neighborId = edge.getTo().getId();

        if (!visited.contains(neighborId)) {
            int newDist = distances.get(currentNodeId) + edge.getWeight();

            if (newDist < distances.get(neighborId)) {
                distances.put(neighborId, newDist);
                queue.add(new NodeEntry(neighborId, newDist));
            } else {
                queue.add(new NodeEntry(neighborId, distances.get(neighborId)));
            }
        }

        return new StepResult(currentNode.getId(), edge);
    }

    /**
     * Helper class for node comparison
     */
    private static class NodeEntry {
        String nodeId;
        int distance;

        NodeEntry(String nodeId, int distance) {
            this.nodeId = nodeId;
            this.distance = distance;
        }
    }
}

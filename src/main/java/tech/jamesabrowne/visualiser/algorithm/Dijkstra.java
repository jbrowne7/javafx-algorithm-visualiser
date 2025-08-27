package tech.jamesabrowne.visualiser.algorithm;

import tech.jamesabrowne.visualiser.model.*;

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
        this.queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.getDistance()));
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

            if (visited.contains(current.getNodeId())) {
                continue;
            }

            visited.add(current.getNodeId());
            currentNodeId = current.getNodeId();
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

    @Override
    public int getDistance(String nodeId) {
        return distances.getOrDefault(nodeId, Integer.MAX_VALUE);
    }

    @Override
    public boolean isVisited(String nodeId) {
        return visited.contains(nodeId);
    }

    @Override
    public Graph getGraph() {
        return this.graph;
    }
}

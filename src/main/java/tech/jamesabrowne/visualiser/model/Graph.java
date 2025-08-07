package tech.jamesabrowne.visualiser.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private final Map<String, Node> nodes = new HashMap<>();

    public void addNode(String id) {
        nodes.putIfAbsent(id, new Node(id));
    }

    public void addEdge(String fromId, String toId, int weight) {
        Node from = nodes.get(fromId);
        Node to = nodes.get(toId);
        if (from != null && to != null) {
            from.addEdge(new Edge(from, to, weight));
        }
    }

    public Node getNode(String id) {
        return nodes.get(id);
    }

    public Collection<Node> getAllNodes() {
        return nodes.values();
    }

    public boolean hasEdge(String fromId, String toId) {

        Node from = nodes.get(fromId);

        for (Edge edge : from.getEdgeList()) {
            if (edge.getTo().getId().equals(toId)) {
                return true;
            }
        }

        return false;
    }
}

package tech.jamesabrowne.visualiser.model;

public class NodeEntry {
    private final String nodeId;
    private final int distance;

    public NodeEntry(String nodeId, int distance) {
        this.nodeId = nodeId;
        this.distance = distance;
    }

    public String getNodeId() {
        return nodeId;
    }

    public int getDistance() {
        return distance;
    }
}


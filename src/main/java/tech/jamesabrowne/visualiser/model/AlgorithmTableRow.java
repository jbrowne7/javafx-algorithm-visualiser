package tech.jamesabrowne.visualiser.model;

import javafx.beans.property.*;

public class AlgorithmTableRow {
    private final String nodeId;
    private int distance;

    public AlgorithmTableRow(String nodeId, int distance) {
        this.nodeId = nodeId;
        this.distance = distance;
    }

    public String getNodeId() { return nodeId; }
    public int getDistance() { return distance; }
    public void setDistance(int distance) { this.distance = distance; }
}

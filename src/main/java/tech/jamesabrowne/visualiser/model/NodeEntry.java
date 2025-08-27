package tech.jamesabrowne.visualiser.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class NodeEntry {
    private final StringProperty nodeId;
    private final int distance;

    public NodeEntry(String nodeId, int distance) {
        this.nodeId = new SimpleStringProperty(nodeId);
        this.distance = distance;
    }

    public String getNodeId() {
        return nodeId.get();
    }

    public int getDistance() {
        return distance;
    }
}


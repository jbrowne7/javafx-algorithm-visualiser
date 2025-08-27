package tech.jamesabrowne.visualiser.model;

import javafx.beans.property.*;

public class AlgorithmTableRow {
    private final StringProperty nodeId;
    private final IntegerProperty distance;

    public AlgorithmTableRow(String nodeId, int distance) {
        this.nodeId = new SimpleStringProperty(nodeId);
        this.distance = new SimpleIntegerProperty(distance);}

    public String getNodeId() { return nodeId.get(); }
    public int getDistance() { return distance.get(); }

    public void setDistance(int distance) { this.distance.set(distance); }

    public StringProperty nodeIdProperty() { return nodeId; }
    public IntegerProperty distanceProperty() { return distance; }
}

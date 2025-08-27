package tech.jamesabrowne.visualiser.model;

import javafx.beans.property.*;

public class AlgorithmTableRow {
    private final StringProperty nodeId;
    private final IntegerProperty distance;
    private final BooleanProperty visited;

    public AlgorithmTableRow(String nodeId, int distance, boolean visited) {
        this.nodeId = new SimpleStringProperty(nodeId);
        this.distance = new SimpleIntegerProperty(distance);
        this.visited = new SimpleBooleanProperty(visited);
    }

    public String getNodeId() { return nodeId.get(); }
    public int getDistance() { return distance.get(); }
    public boolean isVisited() { return visited.get(); }

    public void setDistance(int distance) { this.distance.set(distance); }
    public void setVisited(boolean visited) { this.visited.set(visited); }

    public StringProperty nodeIdProperty() { return nodeId; }
    public IntegerProperty distanceProperty() { return distance; }
    public BooleanProperty visitedProperty() { return visited; }
}

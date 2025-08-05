package tech.jamesabrowne.visualiser.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String id;
    private final List<Edge> edgeList = new ArrayList<>();

    public Node(String id) {
        this.id = id;
    }

    public void addEdge(Edge edge) {
        edgeList.add(edge);
    }

    public String getId() {
        return id;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }
}

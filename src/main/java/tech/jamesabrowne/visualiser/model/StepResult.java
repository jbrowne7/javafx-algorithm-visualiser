package tech.jamesabrowne.visualiser.model;

import java.util.List;

/**
 * This model is used to represent the results of each step an algorithm takes
 */
public class StepResult {
    private List<String> visitedNodeIds;
    private List<Edge> updatedEdges;

    public StepResult(List<String> visitedNodeIds, List<Edge> updatedEdges) {
        this.visitedNodeIds = visitedNodeIds;
        this.updatedEdges = updatedEdges;
    }

    public List<String> getVisitedNodeIds() {
        return visitedNodeIds;
    }

    public List<Edge> getUpdatedEdges() {
        return updatedEdges;
    }
}

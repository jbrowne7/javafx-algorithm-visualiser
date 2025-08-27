package tech.jamesabrowne.visualiser.model;

/**
 * This model is used to represent the results of each step an algorithm takes
 */
public class StepResult {
    private String currentNodeId;
    private Edge currentEdge;

    public StepResult(String currentNodeId, Edge currentEdge) {
        this.currentNodeId = currentNodeId;
        this.currentEdge = currentEdge;
    }

    public String getCurrentNodeId() {
        return currentNodeId;
    }

    public Edge getCurrentEdge() {
        return currentEdge;
    }
}

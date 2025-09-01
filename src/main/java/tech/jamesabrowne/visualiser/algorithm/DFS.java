package tech.jamesabrowne.visualiser.algorithm;

import tech.jamesabrowne.visualiser.model.Graph;
import tech.jamesabrowne.visualiser.model.StepResult;

public class DFS extends Algorithm {

    @Override
    public void initialise(Graph graph, String startNodeId) {

    }

    @Override
    public StepResult step() {
        return null;
    }

    @Override
    public int getDistance(String nodeId) {
        return 0;
    }

    @Override
    public boolean isVisited(String nodeId) {
        return false;
    }

    @Override
    public Graph getGraph() {
        return null;
    }
}

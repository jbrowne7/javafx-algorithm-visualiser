package tech.jamesabrowne.visualiser.algorithm;

import tech.jamesabrowne.visualiser.model.Graph;
import tech.jamesabrowne.visualiser.model.StepResult;

public abstract class Algorithm {

    public abstract void initialise();
    public abstract StepResult step();
    public abstract int getDistance(String nodeId);
    public abstract boolean isVisited(String nodeId);
    public abstract Graph getGraph();
}

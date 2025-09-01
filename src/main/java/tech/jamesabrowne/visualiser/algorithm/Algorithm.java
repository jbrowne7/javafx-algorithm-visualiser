package tech.jamesabrowne.visualiser.algorithm;

import tech.jamesabrowne.visualiser.model.Graph;
import tech.jamesabrowne.visualiser.model.StepResult;

/**
 * Abstract base class for graph algorithms.
 * Provides a common interface and shared functionality for all graph algorithms:
 *  - step() performs one step of the algorithm
 *  - initialise() initialises algorithm which includes setting up parameters, starting nodes, etc
 */

public abstract class Algorithm {

    public abstract void initialise(Graph graph, String startNodeId);
    public abstract StepResult step();
    public abstract int getDistance(String nodeId);
    public abstract boolean isVisited(String nodeId);
    public abstract Graph getGraph();
}

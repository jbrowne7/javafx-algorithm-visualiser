package tech.jamesabrowne.visualiser.util;

import tech.jamesabrowne.visualiser.algorithm.Algorithm;
import tech.jamesabrowne.visualiser.algorithm.DFS;
import tech.jamesabrowne.visualiser.algorithm.Dijkstra;
import tech.jamesabrowne.visualiser.model.Graph;

public class AlgorithmFactory {

    public static Algorithm getAlgorithm(String name, Graph graph, String startNodeId) {
        Algorithm algorithm;
        switch (name) {
            case "dijkstra":
                algorithm = new Dijkstra();
                break;
            case "dfs":
                algorithm = new DFS();
                break;
            default:
                throw new IllegalArgumentException("Unkown algorithm: " + name);
        }
        algorithm.initialise(graph, startNodeId);
        return algorithm;
    }
}

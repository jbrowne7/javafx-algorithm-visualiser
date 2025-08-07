package tech.jamesabrowne.visualiser.util;

import tech.jamesabrowne.visualiser.algorithm.Algorithm;
import tech.jamesabrowne.visualiser.algorithm.Dijkstra;
import tech.jamesabrowne.visualiser.model.Graph;

public class AlgorithmFactory {

    public static Algorithm getAlgorithm(String name, Graph graph, String startNodeId) {
        switch (name) {
            case "dijkstra":
                return new Dijkstra(graph, startNodeId);
            default:
                throw new IllegalArgumentException("Unkown algorithm: " + name);
        }
    }
}

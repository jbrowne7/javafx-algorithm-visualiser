package tech.jamesabrowne.visualiser.util;

import tech.jamesabrowne.visualiser.algorithm.Algorithm;
import tech.jamesabrowne.visualiser.algorithm.Dijkstra;

public class AlgorithmFactory {

    public static Algorithm getAlgorithm(String name) {
        switch (name) {
            case "dijkstra":
                return new Dijkstra();
            default:
                throw new IllegalArgumentException("Unkown algorithm: " + name);
        }
    }
}

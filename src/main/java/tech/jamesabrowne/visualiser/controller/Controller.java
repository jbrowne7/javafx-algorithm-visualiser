package tech.jamesabrowne.visualiser.controller;

import tech.jamesabrowne.visualiser.algorithm.Algorithm;

public class Controller {
    private final Algorithm algorithm;

    public Controller(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void step() {
        algorithm.step();
    }
}

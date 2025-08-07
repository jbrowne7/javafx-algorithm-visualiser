package tech.jamesabrowne.visualiser.controller;

import tech.jamesabrowne.visualiser.algorithm.Algorithm;
import tech.jamesabrowne.visualiser.model.StepResult;

public class Controller {
    private final Algorithm algorithm;

    public Controller(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public StepResult step() {
        return algorithm.step();
    }
}

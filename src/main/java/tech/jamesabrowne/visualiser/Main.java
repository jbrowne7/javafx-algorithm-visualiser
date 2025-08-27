package tech.jamesabrowne.visualiser;

import javafx.application.Application;
import tech.jamesabrowne.visualiser.algorithm.Algorithm;
import tech.jamesabrowne.visualiser.ui.GraphVisualiser;
import tech.jamesabrowne.visualiser.util.AlgorithmFactory;

public class Main {
    public static void main(String[] args) {
        Application.launch(GraphVisualiser.class, args);
    }
}
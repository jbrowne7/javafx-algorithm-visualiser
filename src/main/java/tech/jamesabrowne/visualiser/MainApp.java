package tech.jamesabrowne.visualiser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tech.jamesabrowne.visualiser.algorithm.Algorithm;
import tech.jamesabrowne.visualiser.model.Edge;
import tech.jamesabrowne.visualiser.model.Graph;
import tech.jamesabrowne.visualiser.model.Node;
import tech.jamesabrowne.visualiser.util.AlgorithmFactory;

public class MainApp extends Application {

    private static String algorithmName;

    @Override
    public void start(Stage stage) {

        Algorithm algo = AlgorithmFactory.getAlgorithm(algorithmName);
        System.out.println(algo);
        stage.setScene(new Scene(new StackPane()));
        stage.show();

    }

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Specify a single algorithm");
            return;
        }

//        Test to check graph, edge, and node representation works
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addEdge("A", "B", 13);
        graph.addEdge("A", "C", 13);
        graph.addEdge("D", "B", 13);
        graph.addEdge("B", "C", 13);
        graph.addEdge("B", "A", 13);
        graph.addEdge("B", "D", 13);

        String output;

        for (Node node : graph.getAllNodes()) {
            output = node.getId() + " -> ";
            for (Edge edge : node.getEdgeList()) {
                output += edge.getTo().getId();
                output += ", ";
            }
            System.out.println(output);
        }

        algorithmName = args[0];
//        launch(args);

    }
}
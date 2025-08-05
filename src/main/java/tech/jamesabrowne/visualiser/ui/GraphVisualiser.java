package tech.jamesabrowne.visualiser.ui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tech.jamesabrowne.visualiser.algorithm.Algorithm;
import tech.jamesabrowne.visualiser.model.Graph;
import tech.jamesabrowne.visualiser.model.Node;
import tech.jamesabrowne.visualiser.util.AlgorithmFactory;
import tech.jamesabrowne.visualiser.util.GraphBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GraphVisualiser extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int NODE_RADIUS = 6;

    private Map<String, Double[]> nodePositions = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {

        Parameters params = getParameters();


        Graph graph = GraphBuilder.build();
        Group root = new Group();
        String algorithmName = params.getRaw().get(0);
        Algorithm algorithm = AlgorithmFactory.getAlgorithm(algorithmName);
        Random random = new Random();

        for (Node node : graph.getAllNodes()) {
            // Generate random node positions and ensure that nodes stay within the canvas
            double x = NODE_RADIUS + random.nextDouble() * (WIDTH - 2 * NODE_RADIUS);
            double y = NODE_RADIUS + random.nextDouble() * (HEIGHT - 2 * NODE_RADIUS);

            nodePositions.put(node.getId(), new Double[]{x, y});
            Circle circle = new Circle(x, y, NODE_RADIUS);

            root.getChildren().addAll(circle);
        }

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}

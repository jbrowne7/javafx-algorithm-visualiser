package tech.jamesabrowne.visualiser.ui;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import tech.jamesabrowne.visualiser.algorithm.Algorithm;
import tech.jamesabrowne.visualiser.model.Graph;
import tech.jamesabrowne.visualiser.model.Node;
import tech.jamesabrowne.visualiser.util.AlgorithmFactory;
import tech.jamesabrowne.visualiser.util.GraphBuilder;

import java.util.*;

public class GraphVisualiser extends Application {
    private static final int WIDTH = 1300;
    private static final int HEIGHT = 900;
    private static final int NODE_RADIUS = 17;

    private final Map<String, Double[]> nodePositions = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {

        Parameters params = getParameters();


        Graph graph = GraphBuilder.build();
        Group root = new Group();
        String algorithmName = params.getRaw().getFirst();
        Algorithm algorithm = AlgorithmFactory.getAlgorithm(algorithmName);
        Random random = new Random();

        boolean valid;
        double x, y;

        // Issue with this loop is if the canvas size is too small it will cause an infinite loop
        for (Node node : graph.getAllNodes()) {
            do {
                valid = true;
                x = NODE_RADIUS + random.nextDouble() * (WIDTH - 5 * NODE_RADIUS);
                y = NODE_RADIUS + random.nextDouble() * (HEIGHT - 5 * NODE_RADIUS);

                for (Double[] pos : nodePositions.values()) {
                    double dx = x - pos[0];
                    double dy = y - pos[1];
                    double distance = Math.sqrt(dx * dx + dy * dy);

                    if (distance < 2 * NODE_RADIUS + 10) {
                        valid = false;
                        break;
                    }
                }
            } while (!valid);

            nodePositions.put(node.getId(), new Double[]{x, y});
            Circle circle = new Circle(x, y, NODE_RADIUS);

            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);

            Label label = new Label(node.getId());
            label.setFont(Font.font("System", FontWeight.BLACK, 10));
            label.setLayoutX(x - NODE_RADIUS / 2);
            label.setLayoutY(y - NODE_RADIUS / 2);

            root.getChildren().addAll(circle, label);
        }



        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}

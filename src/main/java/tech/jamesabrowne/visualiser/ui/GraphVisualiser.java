package tech.jamesabrowne.visualiser.ui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import tech.jamesabrowne.visualiser.algorithm.Algorithm;
import tech.jamesabrowne.visualiser.model.Edge;
import tech.jamesabrowne.visualiser.model.Graph;
import tech.jamesabrowne.visualiser.model.Node;
import tech.jamesabrowne.visualiser.util.AlgorithmFactory;
import tech.jamesabrowne.visualiser.util.GraphBuilder;

import java.util.*;

public class GraphVisualiser extends Application {
    private static final int WIDTH = 1300;
    private static final int HEIGHT = 900;
    private static final int NODE_RADIUS = 17;
    private static final double SPARSENESS = 0.4;

    private final Map<String, Double[]> nodePositions = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {

        Parameters params = getParameters();
        Graph graph = GraphBuilder.build();
        Group root = new Group();
        String algorithmName = params.getRaw().getFirst();
        Algorithm algorithm = AlgorithmFactory.getAlgorithm(algorithmName);
        Random random = new Random();

        int nodeCount = graph.getAllNodes().size();
        int columns = (int) Math.ceil(Math.sqrt(nodeCount)); // Square layout
        int rows = (int) Math.ceil((double) nodeCount / columns);

        double cellWidth = WIDTH / columns;
        double cellHeight = HEIGHT / rows;

        int index = 0;


        // Issue with this loop is if the canvas size is too small it will cause an infinite loop
        // Draw nodes
        for (Node node : graph.getAllNodes()) {
            int col = index % columns;
            int row = index / columns;

            // Center the node within its grid cell
            double x = (col + 0.5) * cellWidth;
            double y = (row + 0.5) * cellHeight;

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
            index++;
        }

        // Draw edges
        for (Node node : graph.getAllNodes()) {
            for (Edge edge : node.getEdgeList()) {
                Double[] fromPos = nodePositions.get(edge.getFrom().getId());
                Double[] toPos = nodePositions.get(edge.getTo().getId());

                double dx = toPos[0] - fromPos[0];
                double dy = toPos[1] - fromPos[1];
                double distance = Math.sqrt(dx * dx + dy * dy);

                double unitX = dx / distance;
                double unitY = dy / distance;

                double startX = fromPos[0] + unitX * NODE_RADIUS;
                double startY = fromPos[1] + unitY * NODE_RADIUS;
                double endX = toPos[0] - unitX * NODE_RADIUS;
                double endY = toPos[1] - unitY * NODE_RADIUS;

                double curveOffset = 60; // adjust as needed

                QuadCurve curve = new QuadCurve();
                curve.setStartX(startX);
                curve.setStartY(startY);
                curve.setEndX(endX);
                curve.setEndY(endY);
                curve.setControlX((startX + endX) / 2 - unitY * curveOffset);
                curve.setControlY((startY + endY) / 2 + unitX * curveOffset);
                curve.setStroke(Color.BLACK);
                curve.setStrokeWidth(1.5);
                curve.setFill(null);

                double angle = Math.atan2(dy, dx);
                double arrowLength = 10;
                double arrowAngle = Math.toRadians(15);

                double x1 = endX;
                double y1 = endY;
                double x2 = endX - arrowLength * Math.cos(angle - arrowAngle);
                double y2 = endY - arrowLength * Math.sin(angle - arrowAngle);
                double x3 = endX - arrowLength * Math.cos(angle + arrowAngle);
                double y3 = endY - arrowLength * Math.sin(angle + arrowAngle);

                Polygon arrowHead = new Polygon(x1, y1, x2, y2, x3, y3);
                arrowHead.setFill(Color.BLACK);

                Label weightLabel = new Label(String.valueOf(edge.getWeight()));
                weightLabel.setFont(Font.font("System", FontWeight.BOLD, 10));
                weightLabel.setTextFill(Color.BLACK);
                weightLabel.setLayoutX((startX + endX) / 2);
                weightLabel.setLayoutY((startY + endY) / 2);

                root.getChildren().addAll(curve, arrowHead, weightLabel);
            }
        }

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}

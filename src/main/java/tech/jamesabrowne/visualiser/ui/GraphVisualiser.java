package tech.jamesabrowne.visualiser.ui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tech.jamesabrowne.visualiser.algorithm.Algorithm;
import tech.jamesabrowne.visualiser.controller.Controller;
import tech.jamesabrowne.visualiser.model.Edge;
import tech.jamesabrowne.visualiser.model.Graph;
import tech.jamesabrowne.visualiser.model.Node;
import tech.jamesabrowne.visualiser.util.AlgorithmFactory;
import tech.jamesabrowne.visualiser.util.GraphBuilder;

import java.util.*;

/**
 * This class is used to handle UI for graphs
 */

public class GraphVisualiser extends Application {



    private static final int WIDTH = 1300;
    private static final int HEIGHT = 900;
    private static final int NODE_RADIUS = 17;

    private final Map<String, Double[]> nodePositions = new HashMap<>();

    @Override
    public void start(Stage stage) {

        Parameters params = getParameters();
        Graph graph = GraphBuilder.presetBuild(1);
        Group root = new Group();
        String algorithmName = params.getRaw().getFirst();
        Algorithm algorithm = AlgorithmFactory.getAlgorithm(algorithmName);

        Controller controller = new Controller(algorithm);
        Button stepButton = new Button("Step");
        stepButton.setLayoutX(20);
        stepButton.setLayoutY(20);
        stepButton.setOnAction(e -> controller.step());
        root.getChildren().add(stepButton);

        int nodeCount = graph.getAllNodes().size();
        int columns = (int) Math.ceil(Math.sqrt(nodeCount));
        int rows = (int) Math.ceil((double) nodeCount / columns);

        double cellWidth = WIDTH / (columns * 1.5);
        double cellHeight = HEIGHT / (rows * 1.5);

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

            // just added double type cast here to get rid of intellij warning
            label.setLayoutX(x - (double) NODE_RADIUS / 2);
            label.setLayoutY(y - (double) NODE_RADIUS / 2);

            root.getChildren().addAll(circle, label);
            index++;
        }

        for (Node node : graph.getAllNodes()) {
            for (Edge edge : node.getEdgeList()) {

                /*
                    Drawing edge lines, how this works:
                        - Have two nodes represented as circles with radius `r` positioned at x1,y1 and x2,y2
                        - We get the direction from node1 to node2 by (dx,dy) = (x2-x1, y2-y1)
                        - We normalise direction vector to unit vector by (dx/distance, dy/distance)
                        - We can now subtract the direction unit vector * r to get the edge of the circle
                 */

                Double[] fromPos = nodePositions.get(edge.getFrom().getId());
                Double[] toPos = nodePositions.get(edge.getTo().getId());

                // direction vector (dx, dy)
                double dx = toPos[0] - fromPos[0];
                double dy = toPos[1] - fromPos[1];

                double distance = Math.sqrt(dx * dx + dy * dy);

                // direction unit vector (unitX, unitY)
                double unitX = dx / distance;
                double unitY = dy / distance;

                // calculate where to draw edge
                double startX = fromPos[0] + unitX * NODE_RADIUS;
                double startY = fromPos[1] + unitY * NODE_RADIUS;
                double endX = toPos[0] - unitX * NODE_RADIUS;
                double endY = toPos[1] - unitY * NODE_RADIUS;

                Line line = new Line(startX, startY, endX, endY);
                root.getChildren().add(line);

                /*
                    Drawing edge arrow heads, how this works:
                        - We want a small isosceles triangle at the end of toId node to represent direction of edge
                        - Set angle of triangle `arrowAngle`
                        - Set length of side of triangle `arrowLength`
                        - Using atan2(dx, dy) we get the angle of the line compared to the x-axis
                        - Calculate the 2 points of the isosceles triangle which we need (3rd point is the tip) by:
                            - Move backward from the tip by `arrowLength`
                            - We aren't moving straight back but outwards
                            - `angle +- arrowAngle` is the angle to move out from the line (since angle is the
                            direction of the line and arrowAngle is the width of the arrowHead)
                            - cos gives us the horizontal component and sin gives us the vertical component

                 */

                // arrowhead size and angle
                double arrowLength = 15;
                double arrowAngle = Math.toRadians(30);

                // arrowhead points
                double angle = Math.atan2(dy, dx);

                // other 2 points of triangle for the arrow head, 3rd point is the tip of the line
                double xArrow1 = endX - arrowLength * Math.cos(angle - arrowAngle);
                double yArrow1 = endY - arrowLength * Math.sin(angle - arrowAngle);

                double xArrow2 = endX - arrowLength * Math.cos(angle + arrowAngle);
                double yArrow2 = endY - arrowLength * Math.sin(angle + arrowAngle);

                Polygon arrowHead = new Polygon();
                arrowHead.getPoints().addAll(endX, endY, xArrow1, yArrow1, xArrow2, yArrow2);
                arrowHead.setFill(Color.BLACK);

                root.getChildren().add(arrowHead);

                /*
                    Adding weight labels to lines, how this works:
                        - Calculate mid-point of the line
                        - Calculate (x, y) position for label using mid-point moved by (unit direction vector * offset)
                 */

                // calculate mid point
                double midX = (startX + endX) / 2;
                double midY = (startY + endY) / 2;

                // offset distance for weight label
                double offset = 20;

                // calculate (x, y) pos for label
                double labelX = midX - unitY * offset;
                double labelY = midY + unitX * offset;

                Text weightLabel = new Text(labelX, labelY, String.valueOf(edge.getWeight()));
                weightLabel.setFill(Color.BLUE);
                weightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                root.getChildren().add(weightLabel);

            }
        }

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}

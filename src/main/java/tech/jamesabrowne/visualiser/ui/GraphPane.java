package tech.jamesabrowne.visualiser.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import tech.jamesabrowne.visualiser.model.Edge;
import tech.jamesabrowne.visualiser.model.Graph;
import tech.jamesabrowne.visualiser.model.Node;
import tech.jamesabrowne.visualiser.model.StepResult;


/**
 * Used for rendering the graph and its elements (nodes, edges, weights)
 */
public class GraphPane extends javafx.scene.layout.Pane {

    private int nodeRadius;
    private int paneWidth;
    private int paneHeight;

    private final Map<String, Double[]> nodePositions = new HashMap<>();
    private final Map<String, Circle> nodeCircles = new HashMap<>();
    private final Map<String, Map<String, Line>> edgeLines = new HashMap<>();
    private final Map<String, Map<String, Polygon>> edgeArrowHeads = new HashMap<>();

    private Graph graph;
    private Circle lastHighlightedNode = null;
    private Line lastHighlightedEdge = null;
    private Polygon lastHighlightedArrowHead = null;

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public void setLayout(int nodeRadius, int paneWidth, int paneHeight) {
        this.nodeRadius = nodeRadius;
        this.paneWidth = paneWidth;
        this.paneHeight = paneHeight;
    }

    public void drawGraph() {
        int nodeCount = graph.getAllNodes().size();
        int columns = (int) Math.ceil(Math.sqrt(nodeCount));
        int rows = (int) Math.ceil((double) nodeCount / columns);
        double cellWidth = paneWidth / (columns * 1.5);
        double cellHeight = paneHeight / (rows * 1.5);
        int index = 0;

        for (Node node : graph.getAllNodes()) {
            int col = index % columns;
            int row = index / columns;

            // Center the node within its grid cell
            double x = (col + 0.5) * cellWidth;
            double y = (row + 0.5) * cellHeight;

            nodePositions.put(node.getId(), new Double[]{x, y});
            Circle circle = new Circle(x, y, nodeRadius);

            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);

            Label label = new Label(node.getId());
            label.setFont(Font.font("System", FontWeight.BLACK, 10));

            // just added double type cast here to get rid of warning
            label.setLayoutX(x - (double) nodeRadius / 2);
            label.setLayoutY(y - (double) nodeRadius / 2);
            nodeCircles.put(node.getId(), circle);
            getChildren().addAll(circle, label);
            index++;
        }

        /*
            Drawing edges, how this works:
                - For each edge we get the position of the from and to nodes
                - We calculate the direction vector from the from node to the to node
                - We normalise the direction vector to a unit vector
                - We then calculate the start and end positions of the line by moving along the direction
                vector by the radius of the node circle
         */
        for (Node node : graph.getAllNodes()) {
            for (Edge edge : node.getEdgeList()) {

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
                double startX = fromPos[0] + unitX * nodeRadius;
                double startY = fromPos[1] + unitY * nodeRadius;
                double endX = toPos[0] - unitX * nodeRadius;
                double endY = toPos[1] - unitY * nodeRadius;

                Line line = new Line(startX, startY, endX, endY);
                edgeLines.putIfAbsent(edge.getFrom().getId(), new HashMap<>());
                edgeLines.get(edge.getFrom().getId()).put(edge.getTo().getId(), line);
                getChildren().add(line);

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

                edgeArrowHeads.putIfAbsent(edge.getFrom().getId(), new HashMap<>());
                edgeArrowHeads.get(edge.getFrom().getId()).put(edge.getTo().getId(), arrowHead);

                getChildren().add(arrowHead);

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
                getChildren().add(weightLabel);
            }
        }
    }

    public void applyStep(StepResult result) {
        if (lastHighlightedNode != null) lastHighlightedNode.setFill(Color.WHITE);
        if (lastHighlightedEdge != null) lastHighlightedEdge.setStroke(Color.BLACK);
        if (lastHighlightedArrowHead != null) lastHighlightedArrowHead.setFill(Color.BLACK);

        Circle currentCircle = nodeCircles.get(result.getCurrentNodeId());
        if (currentCircle != null) {
            currentCircle.setFill(Color.YELLOW);
            lastHighlightedNode = currentCircle;
        }

        String[] currentEdge = {result.getCurrentEdge().getFrom().getId(), result.getCurrentEdge().getTo().getId()};
        Line edgeLine = edgeLines.getOrDefault(currentEdge[0], Map.of()).get(currentEdge[1]);
        if (edgeLine != null) {
            edgeLine.setStroke(Color.RED);
            edgeLine.toFront();
            lastHighlightedEdge = edgeLine;
        }

        Polygon arrowHead = edgeArrowHeads.getOrDefault(currentEdge[0], Map.of()).get(currentEdge[1]);
        if (arrowHead != null) {
            arrowHead.setFill(Color.RED);
            arrowHead.toFront();
            lastHighlightedArrowHead = arrowHead;
        }
    }

}

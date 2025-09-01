package tech.jamesabrowne.visualiser.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import tech.jamesabrowne.visualiser.algorithm.Algorithm;
import tech.jamesabrowne.visualiser.model.Node;

public class DistancesPane extends javafx.scene.layout.Pane {
    private final TableView<Row> table = new TableView<>();
    private int tableWidth;
    private int tableHeight;
    private Algorithm algorithm;

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void drawTable() {
        table.setPrefSize(tableWidth, tableHeight);

        TableColumn<Row, String> nodeCol = new TableColumn<>("Node");
        nodeCol.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getNodeId())
        );

        TableColumn<Row, Number> distCol = new TableColumn<>("Distance");
        distCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getDistance())
        );

        table.getColumns().addAll(nodeCol, distCol);
        ObservableList<Row> tableData = FXCollections.observableArrayList();

        for (Node node : algorithm.getGraph().getAllNodes()) {
            int dist = algorithm.getDistance(node.getId());
            tableData.add(new Row(node.getId(), dist));
        }

        table.setItems(tableData);
        getChildren().add(table);
    }

    public void setLayout(int tableWidth, int tableHeight) {
        this.tableWidth = tableWidth;
        this.tableHeight = tableHeight;
    }

    public void refreshDistances() {
        List<Row> entries = new ArrayList<>();
        for (Node node : algorithm.getGraph().getAllNodes()) {
            String nodeId = node.getId();
            int distance = algorithm.getDistance(nodeId);
            entries.add(new Row(nodeId, distance));
        }
        table.getItems().setAll(entries);
    }

    public static class Row {
        private final String nodeId;
        private int distance;

        public Row(String nodeId, int distance) {
            this.nodeId = nodeId;
            this.distance = distance;
        }

        public String getNodeId() { return nodeId; }
        public int getDistance() { return distance; }
        public void setDistance(int distance) { this.distance = distance; }
    }
}

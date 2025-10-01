package tech.jamesabrowne.visualiser.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tech.jamesabrowne.visualiser.algorithm.Algorithm;
import tech.jamesabrowne.visualiser.model.*;
import tech.jamesabrowne.visualiser.util.AlgorithmFactory;
import tech.jamesabrowne.visualiser.util.AppContext;
import tech.jamesabrowne.visualiser.util.GraphBuilder;
import tech.jamesabrowne.visualiser.util.AppConfig;

/**
 * This class is used to place UI panes for graphs
 */
public class GraphVisualiser extends Application {

    @Override
    public void start(Stage stage) {
        AppConfig cfg = AppContext.getConfig();
        Graph graph = GraphBuilder.randomBuild();
        String algorithmName = cfg.algorithm();
        Algorithm algorithm = AlgorithmFactory.getAlgorithm(algorithmName, graph, "N2");

        GraphPane graphPane = new GraphPane();
        graphPane.setLayout(17, 1300, 900);
        graphPane.setGraph(graph);
        graphPane.drawGraph();


        DistancesPane distancesPane = new DistancesPane();
        distancesPane.setAlgorithm(algorithm);
        distancesPane.setLayout(250, 400);
        distancesPane.drawTable();

        StepButtonPane stepButtonPane = new StepButtonPane();
        stepButtonPane.setAlgorithm(algorithm);
        stepButtonPane.setGraphPane(graphPane);
        stepButtonPane.setDistancesPane(distancesPane);
        stepButtonPane.drawStepButton();
        
        javafx.scene.layout.BorderPane rightSide = new javafx.scene.layout.BorderPane();
        rightSide.setTop(stepButtonPane);
        rightSide.setCenter(distancesPane);

        javafx.scene.layout.BorderPane root = new javafx.scene.layout.BorderPane();
        root.setCenter(graphPane);
        root.setRight(rightSide);
        Scene scene = new Scene(root, 1300, 900);
        stage.setScene(scene);
        stage.show();
    }
}

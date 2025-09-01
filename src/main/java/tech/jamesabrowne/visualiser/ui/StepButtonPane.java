package tech.jamesabrowne.visualiser.ui;

import javafx.scene.control.Button;
import tech.jamesabrowne.visualiser.model.StepResult;
import tech.jamesabrowne.visualiser.algorithm.Algorithm;;


public class StepButtonPane extends javafx.scene.layout.Pane {
    private Algorithm algorithm;
    private GraphPane graphPane;
    private DistancesPane distancesPane;

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void setGraphPane(GraphPane graphPane) {
        this.graphPane = graphPane;
    }

    public void setDistancesPane(DistancesPane distancesPane) {
        this.distancesPane = distancesPane;
    }

    public void drawStepButton() {

        Button stepButton = new Button("Step");
        stepButton.setLayoutX(20);
        stepButton.setLayoutY(20);
        stepButton.setOnAction(e -> {
            StepResult result = algorithm.step();
            if (result == null) {
                System.out.println("finished");
                return;
            }
            graphPane.applyStep(result);
            distancesPane.refreshDistances();
        });
        getChildren().add(stepButton);
    }
    
}

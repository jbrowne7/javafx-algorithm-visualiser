package tech.jamesabrowne.visualiser;

import javafx.application.Application;
import tech.jamesabrowne.visualiser.ui.GraphVisualiser;
import tech.jamesabrowne.visualiser.util.AppContext;
import tech.jamesabrowne.visualiser.util.ArgsParser;
import tech.jamesabrowne.visualiser.util.AppConfig;

public class Main {
    public static void main(String[] args) {
        AppConfig cfg = ArgsParser.parse(args);
        AppContext.setConfig(cfg);
        Application.launch(GraphVisualiser.class, args);
    }
}
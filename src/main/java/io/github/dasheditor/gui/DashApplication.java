package io.github.dasheditor.gui;

import io.github.dasheditor.gui.node.NodeCanvas;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DashApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        final BorderPane root = new BorderPane();

        final NodeCanvas canvas = new NodeCanvas();
        root.setCenter(canvas);

        stage.setTitle("Dash");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }
}

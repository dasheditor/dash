package io.github.dasheditor.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DashApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        final BorderPane root = new BorderPane();

        stage.setTitle("Dash");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }
}

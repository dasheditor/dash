package io.github.dasheditor.ui;

import io.github.dasheditor.ui.node.NodeEditor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DashApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        final BorderPane root = new BorderPane();
        root.setTop(new ApplicationMenu());

        final NodeEditor editor = new NodeEditor();
        root.setCenter(editor);

        stage.setTitle("Dash");
        stage.setScene(new Scene(root, 900, 600));
        stage.setMaximized(true);
        stage.show();
    }
}

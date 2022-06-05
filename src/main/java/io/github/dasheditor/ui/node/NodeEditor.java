package io.github.dasheditor.ui.node;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Pane;

public class NodeEditor extends Pane {

    private final NodeCanvas canvas;

    public NodeEditor() {
        canvas = new NodeCanvas();
        getChildren().add(canvas);
    }

    public void draw() {
        canvas.clearBackground();
    }

    @Override
    protected void layoutChildren() {
        layoutInArea(canvas, 0, 0, getWidth(), getWidth(), 0, HPos.LEFT, VPos.TOP);
    }
}

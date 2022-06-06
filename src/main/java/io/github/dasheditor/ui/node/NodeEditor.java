package io.github.dasheditor.ui.node;

import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class NodeEditor extends Pane {

    private final NodeCanvas canvas;
    private Point2D lastMousePosition;

    public NodeEditor() {
        setFocusTraversable(true);
        setOnMousePressed(this::handleMousePressed);
        setOnMouseReleased(this::handleMouseReleased);
        setOnMouseDragged(this::handleMouseDragged);

        canvas = new NodeCanvas();
        getChildren().add(canvas);
    }

    private void handleMousePressed(MouseEvent event) {
        lastMousePosition = new Point2D(event.getX(), event.getY());
    }

    private void handleMouseReleased(MouseEvent event) {
        lastMousePosition = null;
    }

    private void handleMouseDragged(MouseEvent event) {
        Point2D mousePosition = new Point2D(event.getX(), event.getY());

        switch (event.getButton()) {
            case SECONDARY -> {
                Point2D delta = mousePosition.subtract(lastMousePosition);
                lastMousePosition = mousePosition;

                canvas.translate(delta);
            }
        }
    }

    @Override
    protected void layoutChildren() {
        layoutInArea(canvas, 0, 0, getWidth(), getHeight(), 0, HPos.LEFT, VPos.TOP);
    }
}

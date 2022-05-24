package io.github.dasheditor.gui.rendering;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Transform;

/** Helper class to draw on a canvas. */
public class ContextWrapper {

    private final GraphicsContext gc;

    public ContextWrapper(GraphicsContext gc) {
        this.gc = gc;
    }

    public void translate(Point2D delta) {
        gc.translate(delta.getX(), delta.getY());
    }

    public Point2D getTranslatedPosition(Point2D position) {
        Transform t = gc.getTransform();
        return position.subtract(t.getTx(), t.getTy());
    }

}

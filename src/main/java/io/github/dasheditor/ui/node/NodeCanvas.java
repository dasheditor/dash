package io.github.dasheditor.ui.node;

import io.github.dasheditor.ui.util.GridBackground;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class NodeCanvas extends Canvas {

    private final GraphicsContext gc;
    private final Image gridImage;

    public NodeCanvas() {
        gc = getGraphicsContext2D();
        gridImage = GridBackground.createGridImage(100);
    }

    public void clearBackground() {
        gc.setFill(new ImagePattern(gridImage, 0, 0, 100, 100, false));
        gc.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double maxWidth(double height) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double maxHeight(double width) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public void resize(double width, double height) {
        setWidth(width);
        setHeight(height);
    }
}

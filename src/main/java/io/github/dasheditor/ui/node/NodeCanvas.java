package io.github.dasheditor.ui.node;

import io.github.dasheditor.controls.Control;
import io.github.dasheditor.controls.Drawable;
import io.github.dasheditor.controls.node.NodeControl;
import io.github.dasheditor.ui.util.GridBackground;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Affine;

import java.util.ArrayList;

public class NodeCanvas extends Canvas {

    private final GraphicsContext gc;
    private final Image gridImage;
    private final Affine camera;
    private final AnimationTimer timer;
    private final ArrayList<Control> controls;

    public NodeCanvas() {
        gc = getGraphicsContext2D();
        gridImage = GridBackground.createGridImage(100);
        camera = new Affine();

        controls = new ArrayList<>();
        controls.add(new NodeControl(10, 10));
        controls.add(new NodeControl(300, 250));

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                clearBackground();

                gc.save();
                gc.setTransform(camera);
                for (Control c : controls) {
                    if (c instanceof Drawable d) {
                        d.draw(gc);
                    }
                }
                gc.restore();
            }
        };
        timer.start();
    }

    public void translate(Point2D delta) {
        camera.appendTranslation(delta.getX(), delta.getY());
    }

    public void clearBackground() {
        gc.setFill(new ImagePattern(gridImage,
                camera.getTx() + getWidth() / 2,
                camera.getTy() + getHeight() / 2,
                100, 100, false));
        gc.fillRect(0, 0, getWidth(), getHeight());
    }

    public ArrayList<Control> getControls() {
        return controls;
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

package io.github.dasheditor.gui.node;

import io.github.dasheditor.gui.rendering.ContextWrapper;
import io.github.dasheditor.gui.rendering.GridBackground;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Transform;

// TODO: Split this class up into separate components (i.e. viewport, containers, etc.)
//       The viewport will handle input + grid updates. Node container will contain node graph data.
public class NodeCanvas extends Region {

    private final Canvas canvas;
    private final ContextWrapper contextWrapper;

    private Point2D lastPosition;

    private final AnimationTimer gridUpdateTimer;
    private final int gridSize = 100;
    private final ObjectProperty<Image> gridImage = new SimpleObjectProperty<>();

    public NodeCanvas() {
        setFocusTraversable(true);
        setOnMousePressed(this::handleMousePress);
        setOnMouseReleased(this::handleMouseRelease);
        setOnMouseDragged(this::handleMouseDrag);
        setOnKeyPressed(this::handleKeyPress);

        canvas = new Canvas(900, 600);
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
        getChildren().add(canvas);

        contextWrapper = new ContextWrapper(canvas.getGraphicsContext2D());

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        gridUpdateTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Transform transform = gc.getTransform();
                double scale = transform.getMxx();

                backgroundProperty().set(new Background(new BackgroundFill(
                        new ImagePattern(gridImage.get(),
                                transform.getTx() * scale + getWidth() / 2,
                                transform.getTy() * scale + getHeight() / 2,
                                gridSize * scale,
                                gridSize * scale,
                                false),
                        null, null)));
            }
        };

        gridImage.set(GridBackground.createGridImage(gridSize));
        refresh();
    }

    private void handleMousePress(MouseEvent event) {
        lastPosition = new Point2D(event.getX(), event.getY());
    }

    private void handleMouseRelease(MouseEvent event) {}

    private void handleMouseDrag(MouseEvent event) {
        Point2D mousePosition = new Point2D(event.getX(), event.getY());

        switch (event.getButton()) {
            case PRIMARY -> {
                // TODO: Selecting nodes
            }
            case SECONDARY -> {
                Point2D delta = mousePosition.subtract(lastPosition);
                lastPosition = mousePosition;

                contextWrapper.translate(delta);
                refresh();
            }
        }
    }

    private void handleKeyPress(KeyEvent event) {}

    private void refresh() {
        // We could just start the animation time and forget about it.
        gridUpdateTimer.handle(0);
    }

    @Override
    protected void layoutChildren() {
        layoutInArea(canvas, 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, VPos.CENTER);
    }
}

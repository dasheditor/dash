package io.github.dasheditor.controls.node;

import io.github.dasheditor.controls.Container;
import io.github.dasheditor.controls.Drawable;
import io.github.dasheditor.ui.util.ColorConstants;
import javafx.scene.canvas.GraphicsContext;

public class NodeControl extends Container implements Drawable {

    public NodeControl(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext ctx) {
        ctx.setFill(ColorConstants.NODE_BACKGROUND);
        ctx.fillRect(getX(), getY(), 100, 100);
    }
}

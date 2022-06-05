package io.github.dasheditor.ui.util;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GridBackground {

    /* TODO: Possibly allow user to change grid colors? */
    private static final Color BACKGROUND_COLOR = Color.rgb(35, 35, 35);
    private static final Color PRIMARY_COLOR = Color.rgb(25, 25, 25);
    private static final Color SECONDARY_COLOR = Color.rgb(45, 45, 45);

    public static WritableImage createGridImage(int gridSize) {
        WritableImage image = new WritableImage(gridSize, gridSize);
        PixelWriter writer = image.getPixelWriter();

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                Color c;

                if (x <= 1 || y <= 1) {
                    c = PRIMARY_COLOR;
                } else if (x % (gridSize / 5) == 0 || y % (gridSize / 5) == 0) {
                    c = SECONDARY_COLOR;
                } else {
                    c = BACKGROUND_COLOR;
                }

                writer.setColor(x, y, c);
            }
        }

        return image;
    }
}

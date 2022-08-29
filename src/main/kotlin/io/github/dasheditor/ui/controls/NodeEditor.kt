package io.github.dasheditor.ui.controls

import javafx.beans.InvalidationListener
import javafx.geometry.HPos
import javafx.geometry.VPos
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.image.WritableImage
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern

class NodeEditor : Pane() {

    private val canvas = Canvas().also { c ->
        c.widthProperty().bind(widthProperty())
        c.heightProperty().bind(heightProperty())
        children.add(c)
    }

    private val gridImage = GridImage.createGridImage(100, 100)

    init {
        val listener = InvalidationListener {
            redraw()
        }
        canvas.widthProperty().addListener(listener)
        canvas.heightProperty().addListener(listener)
    }

    private fun redraw() {
        val g = canvas.graphicsContext2D
        g.fill = ImagePattern(gridImage, 0.0, 0.0, 100.0, 100.0, false)
        g.fillRect(0.0, 0.0, canvas.width, canvas.height)
    }

    override fun layoutChildren() {
        layoutInArea(canvas, 0.0, 0.0, width, height, 0.0, HPos.LEFT, VPos.TOP)
    }

    private object GridImage {
        private val bgColor = Color.rgb(35, 35, 35)
        private val macroColor = Color.rgb(25, 25, 25)
        private val microColor = Color.rgb(45, 45, 45)

        fun createGridImage(width: Int, height: Int): Image {
            return WritableImage(width, height).also { img ->
                val writer = img.pixelWriter
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        if (x <= 1 || y <= 1) {
                            writer.setColor(x, y, macroColor)
                        } else if (x % (width / 5) == 0 || y % (height / 5) == 0) {
                            writer.setColor(x, y, microColor)
                        } else {
                            writer.setColor(x, y, bgColor)
                        }
                    }
                }
            }
        }
    }
}
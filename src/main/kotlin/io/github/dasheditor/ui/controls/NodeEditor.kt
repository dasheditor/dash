package io.github.dasheditor.ui.controls

import javafx.animation.AnimationTimer
import javafx.beans.InvalidationListener
import javafx.event.EventHandler
import javafx.geometry.HPos
import javafx.geometry.Point2D
import javafx.geometry.VPos
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.image.WritableImage
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.transform.Affine

class NodeEditor : Pane() {

    private val canvas = Canvas().also { c ->
        c.widthProperty().bind(widthProperty())
        c.heightProperty().bind(heightProperty())
        children.add(c)
    }

    private val timer = object : AnimationTimer() {
        override fun handle(now: Long) {
            // Redraw canvas at fixed rate (~60 frames/sec)
            redraw()
        }
    }

    private val context = canvas.graphicsContext2D
    private val transform = Affine()
    private val gridImage = GridImage.createGridImage(100, 100)

    private lateinit var prevMousePosition: Point2D

    init {
        isFocusTraversable = true
        setOnMousePressed { mousePressed(it) }
        setOnMouseDragged { mouseDragged(it) }

        timer.start()
    }

    private fun redraw() {
        context.fill = ImagePattern(
            gridImage,
            transform.tx + canvas.width / 2, transform.ty + canvas.height / 2,
            100.0, 100.0, false)
        context.fillRect(0.0, 0.0, canvas.width, canvas.height)

//        context.save()
//        context.transform = transform
//        context.restore()
    }

    private fun mousePressed(event: MouseEvent) {
        if (event.button != MouseButton.SECONDARY)
            return

        prevMousePosition = Point2D(event.x, event.y)
    }

    private fun mouseDragged(event: MouseEvent) {
        if (event.button != MouseButton.SECONDARY)
            return

        val current = Point2D(event.x, event.y)
        val delta = current.subtract(prevMousePosition)
        prevMousePosition = current

        transform.appendTranslation(delta.x, delta.y)
        redraw()
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
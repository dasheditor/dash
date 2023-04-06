package io.github.dasheditor

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skiko.GenericSkikoView
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkikoView
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

class AppMain {
    private val skiaLayer = SkiaLayer()
    private val primaryWindow = JFrame().apply {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        preferredSize = Dimension(900, 600)
    }

    fun start() = SwingUtilities.invokeLater {
        skiaLayer.addView(GenericSkikoView(skiaLayer, object : SkikoView {
            override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
                canvas.clear(Color.RED)
            }
        }))

        skiaLayer.attachTo(primaryWindow.contentPane)
        skiaLayer.needRedraw()

        primaryWindow.pack()
        primaryWindow.isVisible = true
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val app = AppMain()
            app.start()
        }
    }
}
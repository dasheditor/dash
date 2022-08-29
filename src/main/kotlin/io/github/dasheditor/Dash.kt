package io.github.dasheditor

import io.github.dasheditor.ui.DashWindow
import javafx.application.Application

object Dash {
    @JvmStatic
    fun main(args: Array<String>) {
        Application.launch(DashWindow::class.java, *args)
    }
}
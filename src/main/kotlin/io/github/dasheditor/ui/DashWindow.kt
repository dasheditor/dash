package io.github.dasheditor.ui

import io.github.dasheditor.ui.controls.NodeEditor
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class DashWindow : Application() {

    override fun start(stage: Stage) {
        initialize(stage)

        stage.title = "Dash Editor"
        stage.show()
    }

    private fun initialize(stage: Stage) {
        val root = BorderPane()

        val nodeEditor = NodeEditor()
        root.center = nodeEditor

        stage.scene = Scene(root, 900.0, 600.0)
    }
}
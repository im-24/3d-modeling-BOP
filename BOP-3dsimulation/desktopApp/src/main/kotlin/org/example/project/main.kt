package org.example.project

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import org.example.project.Domain.model.SceneModel
import org.example.project.presentation.viewModel.view
import org.example.project.presentation.App


class BOPsimulation : Application() {
    override fun start(stage: Stage) {
        val model = SceneModel()
        val view = view(model)
        stage.scene = Scene(App(view))
        stage.title = "BOP simulation"
        stage.show()
    }
}

fun main() {
    Application.launch(BOPsimulation::class.java)
}
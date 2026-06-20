package org.example.project.presentation

import javafx.scene.layout.BorderPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import org.example.project.presentation.viewModel.view
import org.example.project.presentation.pane.BopView.BOPview
import org.example.project.presentation.pane.InnerSimView.InnerView
import org.example.project.presentation.pane.ControlViw.controlPanel
import org.example.project.presentation.Componets.BOPbuild

class App(view: view) : BorderPane() {
    init {
        val bop = BOPbuild()

        val partsMap = mapOf(
            "annular" to bop.namedParts["annular"]!!,
            "pipe"    to bop.namedParts["pipe"]!!,
            "spool"   to bop.namedParts["spool"]!!,
            "blind"   to bop.namedParts["blind"]!!
        )

        val bopView = BOPview(view, bop)
        val panel   = controlPanel(view, bop, partsMap)

        val centerWrap = javafx.scene.layout.StackPane(bopView).apply {
            bopView.widthProperty().bind(widthProperty())
            bopView.heightProperty().bind(heightProperty())
        }

        center = centerWrap
        right  = panel

        if (true) {
            left = InnerView(view)
        }

        sceneProperty().addListener { _, _, scene ->
            scene?.setOnKeyPressed { e -> panel.handleKey(e.code, view) }
        }
    }
}
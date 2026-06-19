package org.example.project.presentation

import javafx.scene.layout.BorderPane
import org.example.project.presentation.viewModel.view
import org.example.project.presentation.pane.BopView.BOPview
import org.example.project.presentation.pane.InnerSimView.InnerView
import org.example.project.presentation.pane.ControlViw.controlPanel


class App(view: view) : BorderPane() {
    init {
        center = BOPview(view)
        right = controlPanel(view)
        if (true) {
            left = InnerView(view)

        }
    }
}
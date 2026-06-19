package org.example.project.presentation.pane.ControlViw

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.control.Spinner
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import org.example.project.presentation.viewModel.view

class controlPanel(view: view) : VBox() {

    val RotationLable = Label("Rotation ")
    val xlabel = Label("X")
    val ylabel = Label("Y")
    val zlabel = Label("Z")

    val xrow = HBox()
    val yrow = HBox()
    val zrow = HBox()

    val spinnerx = Spinner<Int>(0, 100, 0, 5)
    val rotxSlider = Slider(0.0, 360.0, 0.0).apply {
        valueProperty().bindBidirectional(view.rotationx)
    }

    val spinnery = Spinner<Int>(0, 100, 0, 5)
    val rotySlider = Slider(0.0, 360.0, 0.0).apply {
        valueProperty().bindBidirectional(view.rotationy)
    }


    val spinnerz = Spinner<Int>(0, 100, 0, 5)
    val rotzSlider = Slider(0.0, 360.0, 0.0).apply {
        valueProperty().bindBidirectional(view.rotationz)
    }

    fun configureHBox(hbox: HBox) {
        hbox.alignment = Pos.CENTER
        hbox.spacing = 10.0
        hbox.padding = Insets(10.0)

    }

    init {
        configureHBox(xrow)
        configureHBox(yrow)
        configureHBox(zrow)
        xrow.children.addAll(spinnerx, rotxSlider)
        yrow.children.addAll(spinnery, rotxSlider)
        zrow.children.addAll(spinnerz, rotzSlider)
        children.addAll(RotationLable, xlabel, xrow, ylabel, yrow, zlabel, zrow)
    }
}
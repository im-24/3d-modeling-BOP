package org.example.project.presentation.viewModel

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.paint.Color
import org.example.project.Domain.model.SceneModel

class view(Scene: SceneModel) {
    val rotationx: SimpleDoubleProperty = Scene.rotationx
    val rotationy: SimpleDoubleProperty = Scene.rotationy
    val rotationz: SimpleDoubleProperty = Scene.rotationz
    val scale: SimpleDoubleProperty = Scene.scale
    val boxVisible = Scene.boxVisible

    // Part visibility toggles
    val annularVisible = SimpleBooleanProperty(true)
    val pipeVisible    = SimpleBooleanProperty(true)
    val spoolVisible   = SimpleBooleanProperty(true)
    val blindVisible   = SimpleBooleanProperty(true)

    // Background color
    val backgroundColor: SimpleObjectProperty<Color> = SimpleObjectProperty(Color.rgb(21, 45, 98))

    // Keyboard RAM control mode
    val keyboardControlEnabled = SimpleBooleanProperty(false)

    /**
     * The part currently targeted by keyboard arrow-key RAM control.
     * Cycle through parts with Tab / Shift-Tab (handled in controlPanel).
     */
    var selectedPart: PartViewModel? = null

    fun resetView() {
        rotationx.set(0.0)
        rotationy.set(0.0)
        rotationz.set(0.0)
        scale.set(1.0)
    }
}
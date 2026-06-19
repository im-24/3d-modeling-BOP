package org.example.project.presentation.viewModel

import javafx.beans.property.SimpleDoubleProperty
import org.example.project.Domain.model.SceneModel

class view(Scene: SceneModel) {
    val rotationx: SimpleDoubleProperty = Scene.rotationx
    val rotationy: SimpleDoubleProperty = Scene.rotationy
    val rotationz: SimpleDoubleProperty = Scene.rotationz
    val scale: SimpleDoubleProperty = Scene.scale
    val boxVisible = Scene.boxVisible


    fun resetView() {
        rotationx.set(0.0)
        rotationy.set(0.0)
        scale.set(1.0)
    }


}
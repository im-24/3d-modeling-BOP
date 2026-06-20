package org.example.project.Domain.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty

class SceneModel {
    val rotationx = SimpleDoubleProperty(0.0)
    val rotationy = SimpleDoubleProperty(0.0)
    val rotationz = SimpleDoubleProperty(0.0)
    val scale = SimpleDoubleProperty(1.250)
    val boxVisible = SimpleBooleanProperty(true)
}
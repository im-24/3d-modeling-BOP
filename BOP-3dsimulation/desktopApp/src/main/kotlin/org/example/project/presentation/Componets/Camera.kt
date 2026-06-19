package org.example.project.presentation.Componets

import javafx.scene.PerspectiveCamera


fun createBopCamera(): PerspectiveCamera {
    val camera = PerspectiveCamera()
    camera.translateX = -300.0
    camera.translateY = -300.0
    camera.translateZ = -1000.0
    camera.fieldOfView = 45.0
    camera.nearClip = 0.1
    camera.farClip = 2000.0
    return camera
}

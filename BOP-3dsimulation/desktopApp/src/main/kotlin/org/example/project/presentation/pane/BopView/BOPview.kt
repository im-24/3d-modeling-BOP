package org.example.project.presentation.pane.BopView

import Domain.Entities.lights
import javafx.scene.Group
import javafx.scene.SceneAntialiasing
import javafx.scene.SubScene
import javafx.scene.paint.Color
import javafx.scene.shape.Cylinder
import javafx.scene.transform.Rotate
import javafx.stage.Screen
import org.example.project.Domain.model.r
import org.example.project.presentation.Componets.BOPbuild
import org.example.project.presentation.Componets.createBopCamera
import org.example.project.presentation.viewModel.BopViewModel
import org.example.project.presentation.viewModel.view

val screenBounds = Screen.getPrimary().visualBounds

class BOPview(view: view ) :
    SubScene(Group(), screenBounds.width, screenBounds.height, true, SceneAntialiasing.BALANCED) {
    init {

        val mainBOP = BOPbuild()
        val rootGroup = root as Group
        rootGroup.children.add(mainBOP.objects3d)
        lights(rootGroup)

        //=====Rotation
        val rotateX = Rotate(0.0, Rotate.X_AXIS)
        val rotateY = Rotate(0.0, Rotate.Y_AXIS)
        val rotateZ = Rotate(0.0, Rotate.Z_AXIS)

        mainBOP.objects3d.transforms.addAll(rotateX, rotateY, rotateZ)

        rotateX.angleProperty().bindBidirectional(view.rotationx)
        rotateY.angleProperty().bindBidirectional(view.rotationy)
        rotateZ.angleProperty().bindBidirectional(view.rotationz)

        var anchorX = 0.0
        var anchorY = 0.0
        var anchorAngleX = 0.0
        var anchorAngleY = 0.0

        setOnMousePressed { event ->
            anchorX = event.sceneX
            anchorY = event.sceneY
            anchorAngleX = rotateX.angle
            anchorAngleY = rotateY.angle
        }

        setOnMouseDragged { event ->
            rotateX.angle = anchorAngleX + (event.sceneY - anchorY) * 0.5
            rotateY.angle = anchorAngleY + (event.sceneX - anchorX) * 0.5
        }

        //==========Scaling
        mainBOP.objects3d.scaleXProperty().bind(view.scale.multiply(mainBOP.scale))
        mainBOP.objects3d.scaleYProperty().bind(view.scale.multiply(mainBOP.scale))
        mainBOP.objects3d.scaleZProperty().bind(view.scale.multiply(mainBOP.scale))

        mainBOP.objects3d.visibleProperty().bind(view.boxVisible)


        camera = createBopCamera()
        fill = Color.rgb( 21,45,98)
    }
}

import Domain.Entities.createBopCamera
import Domain.Entities.lights
import Domain.builders.Bop3dmodel
import javafx.scene.Group
import javafx.scene.SceneAntialiasing
import javafx.scene.SubScene
import javafx.scene.paint.Color
import javafx.scene.transform.Rotate

class CompleteBOPStack(viewModel: SceneViewModel) :
    SubScene(Group(), 1080.0, 950.0, true, SceneAntialiasing.BALANCED) {

    init {
        // The Group passed to the SubScene constructor above IS this SubScene's
        // real root - recover it instead of building an unrelated, disconnected one.
        val rootGroup = root as Group
        val bopGroup = Group()

        val bopModel = Bop3dmodel(bopGroup)
        rootGroup.children.add(bopGroup)
        lights(rootGroup)

        // ===== ROTATION (mouse drag and ViewModel sliders stay in sync) =====
        val rotateX = Rotate(0.0, Rotate.X_AXIS)
        val rotateY = Rotate(0.0, Rotate.Y_AXIS)
        bopGroup.transforms.addAll(rotateX, rotateY)
        rotateX.angleProperty().bindBidirectional(viewModel.rotationX)
        rotateY.angleProperty().bindBidirectional(viewModel.rotationY)

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

        // ===== SCALE (slider value multiplied by the model's own base display scale) =====
        bopGroup.scaleXProperty().bind(viewModel.scale.multiply(bopModel.scale))
        bopGroup.scaleYProperty().bind(viewModel.scale.multiply(bopModel.scale))
        bopGroup.scaleZProperty().bind(viewModel.scale.multiply(bopModel.scale))

        // ===== VISIBILITY TOGGLE =====
        bopGroup.visibleProperty().bind(viewModel.boxVisible)

        // ===== CAMERA (set directly on this SubScene, not a throwaway Scene) =====
        camera = createBopCamera()
        fill = Color.rgb(25, 30, 40)
    }
}

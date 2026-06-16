import javafx.scene.AmbientLight
import javafx.scene.Group
import javafx.scene.PerspectiveCamera
import javafx.scene.PointLight
import javafx.scene.SceneAntialiasing
import javafx.scene.SubScene
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.Box
import javafx.scene.transform.Rotate

class Scene3DView(viewModel: SceneViewModel) : SubScene(Group(), 600.0, 400.0, true, SceneAntialiasing.BALANCED) {

    private var anchorX = 0.0
    private var anchorY = 0.0
    private var anchorAngleX = 0.0
    private var anchorAngleY = 0.0

    init {
        val boltMaterial = PhongMaterial(Color.rgb(120, 120, 130)).apply { specularPower = 100.0 }

        val box = Box(100.0, 100.0, 100.0)
        box.material = boltMaterial
        box.visibleProperty().bind(viewModel.boxVisible)
        box.scaleXProperty().bind(viewModel.scale)
        box.scaleYProperty().bind(viewModel.scale)
        box.scaleZProperty().bind(viewModel.scale)

        // Two separate rotation axes so X (up/down drag) and Y (left/right drag) don't conflict
        val rotateX = Rotate(0.0, Rotate.X_AXIS)
        val rotateY = Rotate(0.0, Rotate.Y_AXIS)
        rotateY.angleProperty().bindBidirectional(viewModel.rotationY) // keep your existing binding/slider in sync
        box.transforms.addAll(rotateX, rotateY)

        val pointLight = PointLight(Color.WHITE).apply {
            translateX = -200.0; translateY = -200.0; translateZ = -400.0
        }
        val ambient = AmbientLight(Color.rgb(60, 60, 60))

        (root as Group).children.addAll(box, pointLight, ambient)
        fill = Color.rgb(30, 30, 30)

        camera = PerspectiveCamera(true).apply {
            translateZ = -500.0
            nearClip = 0.1
            farClip = 2000.0
        }

        // --- Mouse drag to rotate ---
        setOnMousePressed { e: MouseEvent ->
            anchorX = e.sceneX
            anchorY = e.sceneY
            anchorAngleX = rotateX.angle
            anchorAngleY = rotateY.angle
        }

        setOnMouseDragged { e: MouseEvent ->
            rotateX.angle = anchorAngleX - (e.sceneY - anchorY)   // drag up/down → tilt X
            rotateY.angle = anchorAngleY + (e.sceneX - anchorX)   // drag left/right → spin Y
        }

        // --- Scroll to zoom (bonus, common pairing) ---
        setOnScroll { e: ScrollEvent ->
            val zoomFactor = if (e.deltaY > 0) 1.05 else 0.95
            camera.translateZ *= zoomFactor
        }
    }
}
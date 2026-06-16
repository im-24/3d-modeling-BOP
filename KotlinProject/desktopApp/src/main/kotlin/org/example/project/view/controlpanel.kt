import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.VBox

class ControlPanelView(viewModel: SceneViewModel) : VBox(10.0) {
    init {
        val rotSlider = Slider(0.0, 360.0, 0.0).apply {
            valueProperty().bindBidirectional(viewModel.rotationY)
        }
        val scaleSlider = Slider(0.1, 3.0, 1.0).apply {
            valueProperty().bindBidirectional(viewModel.scale)
        }
        val toggleBtn = Button("Toggle Box").apply {
            setOnAction { viewModel.boxVisible.set(!viewModel.boxVisible.value) }
        }
        val resetBtn = Button("Reset").apply {
            setOnAction { viewModel.resetView() }
        }
        children.addAll(Label("Rotation"), rotSlider, Label("Scale"), scaleSlider, toggleBtn, resetBtn)
    }
}
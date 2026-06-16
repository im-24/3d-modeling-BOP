import javafx.scene.layout.BorderPane

class MainView(viewModel: SceneViewModel) : BorderPane() {
    init {
        center = Scene3DView(viewModel)
        right = ControlPanelView(viewModel)
    }
}
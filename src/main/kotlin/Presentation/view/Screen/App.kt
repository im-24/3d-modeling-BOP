import Presentation.view.Ui.controlPanel
import javafx.scene.layout.BorderPane

class App(viewModel: SceneViewModel) : BorderPane() {
    init {
        center = CompleteBOPStack(viewModel)
        right = controlPanel(viewModel)
    }
}

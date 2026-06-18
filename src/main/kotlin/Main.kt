import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class CompleteBOP : Application() {

    override fun start(primaryStage: Stage) {
        val model = Scene3Dmodel()
        val viewModel = SceneViewModel(model)
        primaryStage.scene = Scene(App(viewModel), 1200.0, 950.0)
        primaryStage.title = "3D Controller"
        primaryStage.show()
    }
}

fun main() {
    Application.launch(CompleteBOP::class.java)
}

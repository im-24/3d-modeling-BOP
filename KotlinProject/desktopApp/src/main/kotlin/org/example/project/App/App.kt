import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.stage.Stage

class App : Application() {
    override fun start(stage: Stage) {
        val model = Scene3DModel()
        val viewModel = SceneViewModel(model)
        stage.scene = Scene(MainView(viewModel), 900.0, 500.0)
        stage.title = "3D Controller"
        stage.show()
    }
}

fun main() = Application.launch(App::class.java)
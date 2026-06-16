import javafx.beans.property.BooleanProperty
import javafx.beans.property.DoubleProperty

class SceneViewModel(private val model: Scene3DModel) {
    val rotationX: DoubleProperty = model.rotationX
    val rotationY: DoubleProperty = model.rotationY
    val scale: DoubleProperty = model.scale
    val boxVisible: BooleanProperty = model.boxVisible

    fun resetView() {
        rotationX.set(0.0)
        rotationY.set(0.0)
        scale.set(1.0)
    }
}
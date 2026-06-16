import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty

class Scene3DModel {
    val rotationX = SimpleDoubleProperty(0.0)
    val rotationY = SimpleDoubleProperty(0.0)
    val scale = SimpleDoubleProperty(1.0)
    val boxVisible = SimpleBooleanProperty(true)

}
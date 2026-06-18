package Domain.Entities

import javafx.scene.AmbientLight
import javafx.scene.Group
import javafx.scene.PointLight
import javafx.scene.paint.Color

fun lights(root: Group) {

    val ambientLight = AmbientLight(Color.rgb(80, 80, 80))
    root.children.add(ambientLight)

    val mainLight = PointLight(Color.rgb(255, 245, 220))
    mainLight.translateX = 300.0
    mainLight.translateY = 200.0
    mainLight.translateZ = 400.0
    root.children.add(mainLight)

    val backLight = PointLight(Color.rgb(200, 210, 255))
    backLight.translateX = -200.0
    backLight.translateY = 150.0
    backLight.translateZ = -300.0
    root.children.add(backLight)

    val fillLight = PointLight(Color.rgb(180, 180, 200))
    fillLight.translateX = 0.0
    fillLight.translateY = 300.0
    fillLight.translateZ = 0.0
    root.children.add(fillLight)
}

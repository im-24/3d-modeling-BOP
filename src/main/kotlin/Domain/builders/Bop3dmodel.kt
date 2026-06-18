package Domain.builders

import Domain.Entities.part1
import Domain.Entities.part2
import Domain.Entities.part3
import Domain.Entities.part4
import javafx.scene.Group

class Bop3dmodel(val bopGroup: Group) {
    fun r(dia: Double) = dia / 2.0
    val totalHeight = 20.0 + 10.0 + 8.0 + 18.0 + 40.0 + 40.0 + 40.0 + 35.0 + 20.0 + 18.0 + 20.0 + 12.0
    val centerOffset = totalHeight / 2.0
    val scale = 3.0
    var currentY = 0.0

    init {
        currentY += 40.0
        currentY = part1(currentY, bopGroup, centerOffset)
        currentY = part2(currentY, bopGroup, centerOffset)
        currentY = part3(currentY, bopGroup, centerOffset)
        currentY = part4(currentY, bopGroup, centerOffset)
    }
}
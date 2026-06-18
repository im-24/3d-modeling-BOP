package Domain.Entities

import Data.Material
import javafx.scene.Group
import javafx.scene.shape.Box
import javafx.scene.shape.Cylinder
import javafx.scene.transform.Rotate

fun part4 (currentY : Double , bopGroup: Group, centerOffset : Double) : Double {
    fun r(dia: Double) = dia / 2.0


    var currenty = currentY
// 33. Upper Connector
    val upperConnector = Cylinder(r(56.0), 18.0)
    val lowerRamBody = Box(78.0, 74.0, 35.0)  // Width (X), Height (Y), Depth (Z)

    val boxWidth = 78.0
    val boxHeight = 74.0
    val boxDepth = 37.0
    val margin = 12.0
    val corners2 = listOf(
        Triple(-boxWidth/2 + margin, -boxHeight/2 + margin+120, 0.0),  // Bottom-Left - centered in Z
        Triple( boxWidth/2 - margin, -boxHeight/2 + margin+120, 0.0),  // Bottom-Right - centered in Z
        Triple(-boxWidth/2 + margin,  boxHeight/2 - margin+120, 0.0),  // Top-Left - centered in Z
        Triple( boxWidth/2 - margin,  boxHeight/2 - margin+120, 0.0)   // Top-Right - centered in Z
    )

    for (corner in corners2) {
        val screw = Cylinder(2.0, boxDepth + 10.0)  // Longer than box depth to stick out both sides
        screw.material = Material.boltMaterial
        screw.translateX = corner.first
        screw.translateY = lowerRamBody.translateY + corner.second+5.0
        screw.translateZ = corner.third
        screw.rotationAxis = Rotate.X_AXIS
        screw.rotate = 90.0
        bopGroup.children.add(screw)
    }
    //
    currenty += 60.0


    // The box of the lower rams
    val lowerRamBody2 = Box(75.0, 70.0, 35.0)  // Width (X), Height (Y), Depth (Z)
    lowerRamBody2.material = Material.bodyMaterial
    lowerRamBody2.translateY = currenty + 30.0 - centerOffset
    bopGroup.children.add(lowerRamBody2)


    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************

    // 4. Lower Ram BOP Body - RECTANGULAR shape
    val lowerRamBody5 = Box(70.0, 66.0, 40.0)  // Width (X), Height (Y), Depth (Z)
    lowerRamBody5.material = Material.silverMaterial
    lowerRamBody5.translateY = currenty + 30.0 - centerOffset
    bopGroup.children.add(lowerRamBody5)

    // 4. Lower Ram BOP Body - RECTANGULAR shape
    val lowerRamBody6 = Box(66.0, 62.0, 44.0)  // Width (X), Height (Y), Depth (Z)
    lowerRamBody6.material = Material.bodyMaterial
    lowerRamBody6.translateY = currenty + 30.0 - centerOffset
    bopGroup.children.add(lowerRamBody6)

    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //                        PIPE RAMS
    val leftPipeRam3 = Box(75.0, 25.0, 15.0)
    leftPipeRam3.material = Material.silverMaterial
    leftPipeRam3.translateX = -15.0
    leftPipeRam3.translateY = centerOffset-20.0
    leftPipeRam3.translateZ = 0.0
    bopGroup.children.add(leftPipeRam3)

    val rightPipeRam4 = Box(75.0, 25.0, 15.0)
    rightPipeRam4.material = Material.silverMaterial
    rightPipeRam4.translateX = 15.0
    rightPipeRam4.translateY = centerOffset-20.0
    rightPipeRam4.translateZ = 0.0
    bopGroup.children.add(rightPipeRam4)

    val leftBonnet3 = Cylinder(r(12.0), 20.0)
    leftBonnet3.material = Material.flangeMaterial
    leftBonnet3.translateX = -60.0
    leftBonnet3.translateY = centerOffset-20.0
    leftBonnet3.rotationAxis = Rotate.Z_AXIS
    leftBonnet3.rotate = 90.0
    bopGroup.children.add(leftBonnet3)

    val rightBonnet4 = Cylinder(r(12.0), 20.0)
    rightBonnet4.material = Material.flangeMaterial
    rightBonnet4.translateX = 60.0
    rightBonnet4.translateY = centerOffset-20.0
    rightBonnet4.rotationAxis = Rotate.Z_AXIS
    rightBonnet4.rotate = 90.0
    bopGroup.children.add(rightBonnet4)

    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //                 Crossover
    currenty += 60.0
    val upperRamBody = Cylinder(r(34.0), 30.0)
    upperRamBody.material = Material.bodyMaterial
    upperRamBody.translateY = currenty + 10.0 - centerOffset
    bopGroup.children.add(upperRamBody)
    val upperRamY = currenty + 20.0 - centerOffset
    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************


    currenty += 20.0


    val chokeKillY = 00.0 - centerOffset
    val controlPodY = 160.0 - centerOffset

    // ===== 🔻 HYDRAULIC ACTUATORS =====

    // 36. Lower Actuators (Y=76)
    for (x in listOf(1, 2, 3, 4)) {
        val actuator = Cylinder(r(4.0), 59.0)
        actuator.material = Material.boltMaterial
        actuator.translateX = 0.0
        actuator.translateY = currenty-130.0//66.0 - centerOffset
        actuator.transforms.addAll(
            Rotate(90.0, Rotate.Z_AXIS),      // Make horizontal
            Rotate(x * 45.0, Rotate.X_AXIS)   // Rotate around X axis for positioning
        )
        bopGroup.children.add(actuator)
    }
    // 36. Lower Actuators (Y=76)
    for (x in listOf(1, 2, 3, 4)) {
        val actuator = Cylinder(r(4.0), 79.0)
        actuator.material = Material.boltMaterial
        actuator.translateX = 0.0
        actuator.translateY = 66.0 - centerOffset
        actuator.transforms.addAll(
            Rotate(90.0, Rotate.Z_AXIS),      // Make horizontal
            Rotate(x * 45.0, Rotate.X_AXIS)   // Rotate around X axis for positioning
        )
        bopGroup.children.add(actuator)
    }




    upperConnector.material = Material.bodyMaterial
    upperConnector.translateY = currenty + 9.0 - centerOffset
    bopGroup.children.add(upperConnector)
    currenty += 18.0

    val riserAdapter = Cylinder(r(50.0), 20.0)
    riserAdapter.material = Material.flangeMaterial
    riserAdapter.translateY = currenty + 10.0 - centerOffset
    bopGroup.children.add(riserAdapter)
    currenty += 20.0

    val flexCyl = Cylinder(r(40.0), 12.0)
    flexCyl.material = Material.silverMaterial
    flexCyl.translateY = currenty + 0.0 - centerOffset
    bopGroup.children.add(flexCyl)
    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************

    return currenty

}

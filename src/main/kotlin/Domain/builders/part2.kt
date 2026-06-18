package Domain.Entities

import Data.Material
import javafx.scene.Group
import javafx.scene.shape.Box
import javafx.scene.shape.Cylinder
import javafx.scene.transform.Rotate

fun part2 (currentY : Double , bopGroup: Group, centerOffset : Double) : Double {
    fun r(dia: Double) = dia / 2.0

    var currenty = currentY

    val lowerRamBody = Box(78.0, 74.0, 35.0)  // Width (X), Height (Y), Depth (Z)
    lowerRamBody.material = Material.bodyMaterial
    lowerRamBody.translateY = currentY + 30.0 - centerOffset
    bopGroup.children.add(lowerRamBody)

    val lowerRamY = currentY + 20.0 - centerOffset

    ///////////////////////////////////////////////



    ///////////////////////////////////////////////



    // 4. Lower Ram BOP Body - RECTANGULAR shape
    val lowerRamBody3 = Box(70.0, 66.0, 40.0)  // Width (X), Height (Y), Depth (Z)
    lowerRamBody3.material = Material.silverMaterial
    lowerRamBody3.translateY = currentY + 30.0 - centerOffset
    bopGroup.children.add(lowerRamBody3)

    // 4. Lower Ram BOP Body - RECTANGULAR shape
    val lowerRamBody4 = Box(66.0, 62.0, 44.0)  // Width (X), Height (Y), Depth (Z)
    lowerRamBody4.material = Material.bodyMaterial
    lowerRamBody4.translateY = currentY + 30.0 - centerOffset
    bopGroup.children.add(lowerRamBody4)

    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //             SHEAR RAMS  (Vasing + Pipe)
    val leftPipeRam = Box(75.0, 25.0, 15.0)
    leftPipeRam.material = Material.silverMaterial
    leftPipeRam.translateX = -15.0
    leftPipeRam.translateY = lowerRamY
    leftPipeRam.translateZ = 0.0
    bopGroup.children.add(leftPipeRam)

    val rightPipeRam = Box(75.0, 25.0, 15.0)
    rightPipeRam.material = Material.silverMaterial
    rightPipeRam.translateX = 15.0
    rightPipeRam.translateY = lowerRamY
    rightPipeRam.translateZ = 0.0
    bopGroup.children.add(rightPipeRam)


    val leftBonnet = Cylinder(r(12.0), 20.0)
    leftBonnet.material = Material.flangeMaterial
    leftBonnet.translateX = -60.0
    leftBonnet.translateY = lowerRamY
    leftBonnet.rotationAxis = Rotate.Z_AXIS
    leftBonnet.rotate = 90.0
    bopGroup.children.add(leftBonnet)

    val rightBonnet = Cylinder(r(12.0), 20.0)
    rightBonnet.material = Material.flangeMaterial
    rightBonnet.translateX = 60.0
    rightBonnet.translateY = lowerRamY
    rightBonnet.rotationAxis = Rotate.Z_AXIS
    rightBonnet.rotate = 90.0
    bopGroup.children.add(rightBonnet)

    return currenty
}

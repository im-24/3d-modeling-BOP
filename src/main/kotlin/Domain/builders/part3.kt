package Domain.Entities

import Data.Material
import javafx.scene.Group
import javafx.scene.shape.Box
import javafx.scene.shape.Cylinder
import javafx.scene.transform.Rotate

fun part3 (currentY : Double , bopGroup: Group, centerOffset : Double) : Double {
    fun r(dia: Double) = dia / 2.0

    var currenty = currentY
    currenty += 20.0   // boundary increment that belongs to this section, restored from the original

    // 5. Pipe Rams
    val lowerRamY = currenty - centerOffset

    val leftPipeRam2 = Box(75.0, 25.0, 15.0)
    leftPipeRam2.material = Material.silverMaterial
    leftPipeRam2.translateX = -15.0
    leftPipeRam2.translateY = lowerRamY+30.0
    leftPipeRam2.translateZ = 0.0
    bopGroup.children.add(leftPipeRam2)

    val rightPipeRam2 = Box(75.0, 25.0, 15.0)
    rightPipeRam2.material = Material.silverMaterial
    rightPipeRam2.translateX = 15.0
    rightPipeRam2.translateY = lowerRamY+30.0
    rightPipeRam2.translateZ = 0.0
    bopGroup.children.add(rightPipeRam2)

    val leftBonnet2 = Cylinder(r(12.0), 20.0)
    leftBonnet2.material = Material.flangeMaterial
    leftBonnet2.translateX = -60.0
    leftBonnet2.translateY = lowerRamY+30
    leftBonnet2.rotationAxis = Rotate.Z_AXIS
    leftBonnet2.rotate = 90.0
    bopGroup.children.add(leftBonnet2)

    val rightBonnet2 = Cylinder(r(12.0), 20.0)
    rightBonnet2.material = Material.flangeMaterial
    rightBonnet2.translateX = 60.0
    rightBonnet2.translateY = lowerRamY+30
    rightBonnet2.rotationAxis = Rotate.Z_AXIS
    rightBonnet2.rotate = 90.0
    bopGroup.children.add(rightBonnet2)


    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************


    currenty += 30.0



    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //                 Adapter spool

    val leftLockShaft = Cylinder(r(45.0), 5.0)
    leftLockShaft.material = Material.silverMaterial
    leftLockShaft.translateX = 0.0
    leftLockShaft.translateY = lowerRamY-40
    leftLockShaft.translateZ = 0.0
    bopGroup.children.add(leftLockShaft)


    currenty += 10.0

    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //                 Spacer spool
    val middleRamBody = Cylinder(r(34.0), 80.0)
    middleRamBody.material = Material.bodyMaterial
    middleRamBody.translateY = currenty + 15.0 - centerOffset
    bopGroup.children.add(middleRamBody)
    val middleRamY = currenty + 20.0 - centerOffset
    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //                 Adapter spool
    val middleRamBodyadapt = Cylinder(r(36.0), 8.0)
    middleRamBodyadapt.material = Material.bodyMaterial
    middleRamBodyadapt.translateY = currenty + 13.0 - centerOffset
    bopGroup.children.add(middleRamBodyadapt)
    //val middleRamY = currenty + 20.0 - centerOffset
    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************


    // 6-8. Ram Bonnets
    val killines = Cylinder(r(25.0), 80.0)
    killines.material = Material.flangeMaterial
    killines.translateX = 0.0
    killines.translateY = middleRamY+10
    killines.rotationAxis = Rotate.Z_AXIS
    killines.rotate = 90.0
    bopGroup.children.add(killines)

    val killines2 = Cylinder(r(8.0), 110.0)
    killines2.material = Material.silverMaterial
    killines2.translateX = 0.0
    killines2.translateY = middleRamY+10
    killines2.rotationAxis = Rotate.Z_AXIS
    killines2.rotate = 90.0
    bopGroup.children.add(killines2)
    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //                 Kill Line
    val killines3 = Cylinder(r(18.0), 3.0)
    killines3.material = Material.flangeMaterial
    killines3.translateX = 55.0
    killines3.translateY = middleRamY+10
    killines3.rotationAxis = Rotate.Z_AXIS
    killines3.rotate = 90.0
    bopGroup.children.add(killines3)
    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //                     CHOKE LINE
    val killines4 = Cylinder(r(18.0), 3.0)
    killines4.material = Material.flangeMaterial
    killines4.translateX = -55.0
    killines4.translateY = middleRamY+10
    killines4.rotationAxis = Rotate.Z_AXIS
    killines4.rotate = 90.0
    bopGroup.children.add(killines4)

    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //                 Bolts
    // 4 horizontal screws at the 4 corners (pointing in Y direction, through both sides)
    val boxWidth = 78.0
    val boxHeight = 74.0
    val boxDepth = 37.0
    val margin = 12.0  // Margin from edges

    val corners = listOf(
        Triple(-boxWidth/2 + margin, -boxHeight/2 + margin, 0.0),  // Bottom-Left - centered in Z
        Triple( boxWidth/2 - margin, -boxHeight/2 + margin, 0.0),  // Bottom-Right - centered in Z
        Triple(-boxWidth/2 + margin,  boxHeight/2 - margin, 0.0),  // Top-Left - centered in Z
        Triple( boxWidth/2 - margin,  boxHeight/2 - margin, 0.0)   // Top-Right - centered in Z
    )

    val lowerRamBody = Box(78.0, 74.0, 35.0)  // Width (X), Height (Y), Depth (Z)

    for (corner in corners) {
        val screw = Cylinder(2.0, boxDepth + 10.0)  // Longer than box depth to stick out both sides
        screw.material = Material.boltMaterial
        screw.translateX = corner.first
        screw.translateY = lowerRamBody.translateY + corner.second+5.0
        screw.translateZ = corner.third
        screw.rotationAxis = Rotate.X_AXIS
        screw.rotate = 90.0
        bopGroup.children.add(screw)
    }



    // ===== 🔻 CONNECTORS & RISER INTERFACE =====




    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //                       WELLHEAD CONNECTOR

    return currenty

}

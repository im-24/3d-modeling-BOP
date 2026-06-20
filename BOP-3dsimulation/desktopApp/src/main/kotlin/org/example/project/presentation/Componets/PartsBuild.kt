package org.example.project.presentation.Componets

import com.sun.java.swing.plaf.windows.TMSchema
import javafx.scene.Group
import javafx.scene.shape.Box
import javafx.scene.shape.Cylinder
import javafx.scene.shape.Shape3D
import javafx.scene.transform.Rotate
import org.example.project.Domain.model.part
import org.example.project.Domain.model.r
import org.example.project.presentation.theme.Material
import org.example.project.presentation.theme.Material.blackish
import org.example.project.presentation.theme.Material.bodyMaterial
import org.example.project.presentation.theme.Material.boltMaterial
import org.example.project.presentation.theme.Material.flangeMaterial
import org.example.project.presentation.theme.Material.silverMaterial
import org.example.project.presentation.theme.Material.test
import org.example.project.presentation.viewModel.BopViewModel
import org.example.project.presentation.viewModel.PartViewModel


var currentY = 0.0
val centerOffset = 242.0 / 2

fun PartsBuild(parent: BopViewModel) {

    val ram = Cylinder(r(23.0), 23.0)
    var annular = PartViewModel(
        parent,
        listOf(),
        0.0,

        listOf(Pair(ram,ram )),
        true,
        Group(),

        )

    createAnnular(annular)

    var blind = PartViewModel(
        parent,
        listOf(),
        0.0,

        listOf(Pair(ram,ram )),
        true,
        Group(),

        )
    createBlind(blind)
    // IMPORTANT: Do NOT translate annular.object3D - build geometry centered around origin
    parent.objects3d.translateX = 0.0
    parent.objects3d.translateY = 0.0
    parent.objects3d.translateZ = 0.0
    parent.objects3d.scaleX = parent.scale  // ← ADD THIS
    parent.objects3d.scaleY = parent.scale  //  // ← ADD THIS
    parent.objects3d.scaleZ = parent.scale  //
    var spool = PartViewModel(
        parent,
        listOf(),
        0.0,
        listOf(Pair(ram,ram )),
        true,
        Group(),

        )
    createSpool(spool)

    var pipe = PartViewModel(
        parent,
        listOf(),
        0.0,
        listOf(Pair(ram,ram )),
        true,
        Group(),

        )

    createPipe(pipe)

    // Register named parts so the control panel can access them
    parent.namedParts["annular"] = annular
    parent.namedParts["blind"]   = blind
    parent.namedParts["spool"]   = spool
    parent.namedParts["pipe"]    = pipe

    parent.objects3d.children.addAll(
        pipe.object3D,
        annular.object3D,
        spool.object3D,
        blind.object3D
    )
}

fun createAnnular(annular: PartViewModel) {



    currentY += 40.0
    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //                          ANNULAR BOP
    val wellhead = Cylinder(r(75.0), 45.0)
    wellhead.material = bodyMaterial
    wellhead.translateY = currentY + 10.0 - centerOffset
    annular.object3D.children.add(wellhead)
    currentY += 20.0

    val boltCount = 12
    val radius = 29.0   // slightly bigger than wellhead radius (75)
    val boltHeight = 18.0

    for (i in 0 until boltCount) {
        val angle = Math.toRadians((360.0 / boltCount) * i)

        val x = radius * Math.cos(angle)
        val z = radius * Math.sin(angle)

        val bolt = Cylinder(2.0, boltHeight) // small vertical bolt
        bolt.material = boltMaterial

        bolt.translateX = x
        bolt.translateZ = z
        bolt.translateY = currentY - 32.5 - centerOffset // top of wellhead

        annular.object3D.children.add(bolt)
    }

    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //                          Riser Adapter

    val annularTop = wellhead.translateY - (165.0 / 2)
    val flangeH = 6.0
    val bodyH = 20.0
    val bottomFlange = Cylinder(r(50.0), flangeH)
    bottomFlange.material = flangeMaterial
    bottomFlange.translateY = annularTop + flangeH / 2
    annular.object3D.children.add(bottomFlange)
// ===== MAIN BODY (NO GAP) =====
    val riserBody = Cylinder(r(40.0), bodyH)
    riserBody.material = bodyMaterial
    riserBody.translateY = bottomFlange.translateY + flangeH / 2 + bodyH / 2
    annular.object3D.children.add(riserBody)
// ===== TOP FLANGE (NO GAP) =====
    val topFlange = Cylinder(r(50.0), flangeH)
    topFlange.material = flangeMaterial
    topFlange.translateY = riserBody.translateY + bodyH / 2 + flangeH / 2
    annular.object3D.children.add(topFlange)

    ////////////////////////////////////////////////////////////////////////////////
    val boltCountR = 12
    val boltRadius = 18.0
    val boltH = 18.0

    val topFlangeTop = topFlange.translateY + flangeH / 2

    for (i in 0 until boltCountR) {
        val angle = Math.toRadians((360.0 / boltCountR) * i)

        val x = boltRadius * Math.cos(angle)
        val z = boltRadius * Math.sin(angle)

        val bolt = Cylinder(2.0, boltH)
        bolt.material = boltMaterial

        bolt.translateX = x
        bolt.translateZ = z
        bolt.translateY = topFlangeTop + boltH / 2-40.0

        annular.object3D.children.add(bolt)

        val casingAdapter = Cylinder(r(40.0), 14.0)
        casingAdapter.material = blackish //flangeMaterial
        casingAdapter.translateY = currentY - 30.0 - centerOffset
        annular.object3D.children.add(casingAdapter)

        for (x in listOf(1, 2, 3, 4)) {
            val actuator = Cylinder(r(4.0), 79.0)
            actuator.material = boltMaterial
            actuator.translateX = 0.0
            actuator.translateY = 66.0 - centerOffset
            actuator.transforms.addAll(
                Rotate(90.0, Rotate.Z_AXIS),      // Make horizontal
                Rotate(x * 45.0, Rotate.X_AXIS)   // Rotate around X axis for positioning
            )
            annular.object3D.children.add(actuator)
        }
    }
    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //


    ////////////////////////////////////////////////////////////////////////////////
    // 0. Wellhead Housing
    val wellhead2 = Cylinder(r(53.0), 22.0)
    wellhead2.material = bodyMaterial
    wellhead2.translateY = currentY + 20.0 - centerOffset
    annular.object3D.children.add(wellhead2)
    currentY += 20.0

    // 2. Base Flange
    val baseFlange = Cylinder(r(40.0), 47.0)
    baseFlange.material = flangeMaterial
    baseFlange.translateY = currentY + 4.0 - centerOffset
    annular.object3D.children.add(baseFlange)
    currentY += 15.0



}

fun createBlind(blind: PartViewModel) {
    // 3. Hydraulic Connector (H4)
    val h4Connector = Cylinder(r(36.0), 18.0)
    h4Connector.material = silverMaterial
    h4Connector.translateY = currentY + 9.0 - centerOffset
    blind.object3D.children.add(h4Connector)
    currentY += 20.0

    val lowerRamBody = Box(78.0, 74.0, 35.0)  // Width (X), Height (Y), Depth (Z)
    lowerRamBody.translateY = currentY + 32.0 - centerOffset
    lowerRamBody.material = flangeMaterial

    blind.object3D.children.add(lowerRamBody)



    // 4. Lower Ram BOP Body - RECTANGULAR shape
    val lowerRamBody3 = Box(70.0, 66.0, 40.0)  // Width (X), Height (Y), Depth (Z)
    lowerRamBody3.material = silverMaterial
    lowerRamBody3.translateY = currentY + 30.0 - centerOffset
    blind.object3D.children.add(lowerRamBody3)

    val lowerRamBody4 = Box(66.0, 62.0, 44.0)  // Width (X), Height (Y), Depth (Z)
    lowerRamBody4.material = bodyMaterial
    lowerRamBody4.translateY = currentY + 30.0 - centerOffset
    blind.object3D.children.add(lowerRamBody4)


    val lowerRamY = currentY + 18.0 - centerOffset

    val leftPipeRam = Box(75.0, 25.0, 15.0)
    leftPipeRam.material = silverMaterial
    leftPipeRam.translateX = -15.0
    leftPipeRam.translateY = lowerRamY
    leftPipeRam.translateZ = 0.0
    blind.object3D.children.add(leftPipeRam)

    val rightPipeRam = Box(75.0, 25.0, 15.0)
    rightPipeRam.material = silverMaterial
    rightPipeRam.translateX = 15.0
    rightPipeRam.translateY = lowerRamY
    rightPipeRam.translateZ = 0.0
    blind.object3D.children.add(rightPipeRam)


    val leftBonnet = Cylinder(r(12.0), 20.0)
    leftBonnet.material = flangeMaterial
    leftBonnet.translateX = -60.0
    leftBonnet.translateY = lowerRamY
    leftBonnet.rotationAxis = Rotate.Z_AXIS
    leftBonnet.rotate = 90.0
    blind.object3D.children.add(leftBonnet)

    val rightBonnet = Cylinder(r(12.0), 20.0)
    rightBonnet.material = flangeMaterial
    rightBonnet.translateX = 60.0
    rightBonnet.translateY = lowerRamY
    rightBonnet.rotationAxis = Rotate.Z_AXIS
    rightBonnet.rotate = 90.0
    blind.object3D.children.add(rightBonnet)



    currentY += 20.0
    // 5. Pipe Rams
    val leftPipeRam2 = Box(75.0, 25.0, 15.0)
    leftPipeRam2.material = silverMaterial
    leftPipeRam2.translateX = -15.0
    leftPipeRam2.translateY = lowerRamY+30.0
    leftPipeRam2.translateZ = 0.0
    blind.object3D.children.add(leftPipeRam2)

    val rightPipeRam2 = Box(75.0, 25.0, 15.0)
    rightPipeRam2.material = silverMaterial
    rightPipeRam2.translateX = 15.0
    rightPipeRam2.translateY = lowerRamY+30.0
    rightPipeRam2.translateZ = 0.0
    blind.object3D.children.add(rightPipeRam2)


    val leftBonnet2 = Cylinder(r(12.0), 20.0)
    leftBonnet2.material = flangeMaterial
    leftBonnet2.translateX = -60.0
    leftBonnet2.translateY = lowerRamY+30
    leftBonnet2.rotationAxis = Rotate.Z_AXIS
    leftBonnet2.rotate = 90.0
    blind.object3D.children.add(leftBonnet2)


    val rightBonnet2 = Cylinder(r(12.0), 20.0)
    rightBonnet2.material = flangeMaterial
    rightBonnet2.translateX = 60.0
    rightBonnet2.translateY = lowerRamY+30
    rightBonnet2.rotationAxis = Rotate.Z_AXIS
    rightBonnet2.rotate = 90.0
    blind.object3D.children.add(rightBonnet2)

    blind.movRAM = listOf(
        Pair( rightBonnet , leftBonnet ),
        Pair(rightBonnet2, leftBonnet2)
    )



    val leftLockShaft = Cylinder(r(45.0), 5.0)
    leftLockShaft.material = silverMaterial
    leftLockShaft.translateX = 0.0
    leftLockShaft.translateY = lowerRamY-32
    leftLockShaft.translateZ = 0.0
    blind.object3D.children.add(leftLockShaft)


    currentY +=10.0

    val middleRamBody = Cylinder(r(34.0), 80.0)
    middleRamBody.material = bodyMaterial
    middleRamBody.translateY = currentY + 15.0 - centerOffset
    blind.object3D.children.add(middleRamBody)

}
val middleRamY = currentY - 20.0 + centerOffset

fun createSpool(spool: PartViewModel) {

    val spoopofset = 40.0

    val middleRamBodyadapt = Cylinder(r(36.0), 8.0)
    middleRamBodyadapt.material = bodyMaterial
    middleRamBodyadapt.translateY = currentY + 13.0+spoopofset -centerOffset
    spool.object3D.children.add(middleRamBodyadapt)

    val middleRamBody = Cylinder(r(34.0), 80.0)
    middleRamBody.material = bodyMaterial
    middleRamBody.translateY = currentY + 15.0 -centerOffset+spoopofset
    spool.object3D.children.add(middleRamBody)
    val middleRamY = currentY + 20.0-  centerOffset+spoopofset



    val killines = Cylinder(r(25.0), 80.0)
    killines.material = flangeMaterial
    killines.translateX = 0.0
    killines.translateY = middleRamY+10
    killines.rotationAxis = Rotate.Z_AXIS
    killines.rotate = 90.0
    spool.object3D.children.add(killines)

    val killines2 = Cylinder(r(8.0), 110.0)
    killines2.material = silverMaterial
    killines2.translateX = 0.0
    killines2.translateY = middleRamY+10
    killines2.rotationAxis = Rotate.Z_AXIS
    killines2.rotate = 90.0
    spool.object3D.children.add(killines2)
    // 4 horizontal screws at the 4 corners (pointing in Y direction, through both sides)
    val boxWidth = 78.0
    val boxHeight = 74.0
    val boxDepth = 37.0
    val margin = 12.0

    val corners = listOf(
        Triple(-boxWidth/2 + margin, -boxHeight/2 + margin, 0.0),  // Bottom-Left - centered in Z
        Triple( boxWidth/2 - margin, -boxHeight/2 + margin, 0.0),  // Bottom-Right - centered in Z
        Triple(-boxWidth/2 + margin,  boxHeight/2 - margin, 0.0),  // Top-Left - centered in Z
        Triple( boxWidth/2 - margin,  boxHeight/2 - margin, 0.0)   // Top-Right - centered in Z
    )
    val lowerRamBody = Box(78.0, 74.0, 35.0)  // Width (X), Height (Y), Depth (Z)

    for (corner in corners) {
        val screw = Cylinder(2.0, boxDepth + 10.0)  // Longer than box depth to stick out both sides
        screw.material = boltMaterial
        screw.translateX = corner.first
        screw.translateY = lowerRamBody.translateY + corner.second + 25.0
        screw.translateZ = corner.third
        screw.rotationAxis = Rotate.X_AXIS
        screw.rotate = 90.0
        spool.object3D.children.add(screw)
    }

    val killines3 = Cylinder(r(18.0), 3.0)
    killines3.material = flangeMaterial
    killines3.translateX = 55.0
    killines3.translateY = middleRamY+10
    killines3.rotationAxis = Rotate.Z_AXIS
    killines3.rotate = 90.0
    spool.object3D.children.add(killines3)

    val killines4 = Cylinder(r(18.0), 3.0)
    killines4.material = flangeMaterial
    killines4.translateX = -55.0
    killines4.translateY = middleRamY+10
    killines4.rotationAxis = Rotate.Z_AXIS
    killines4.rotate = 90.0
    spool.object3D.children.add(killines4)
}

fun createPipe(pipe: PartViewModel) {
    currentY += 95.0
    // 4 horizontal screws at the 4 corners (pointing in Y direction, through both sides)
    val boxWidth = 78.0
    val boxHeight = 74.0
    val boxDepth = 37.0
    val margin = 12.0
    val lowerRamBody = Box(78.0, 74.0, 35.0)  // Width (X), Height (Y), Depth (Z)

    val lowerRamBody2 = Box(75.0, 70.0, 35.0)  // Width (X), Height (Y), Depth (Z)
    lowerRamBody2.material = bodyMaterial
    lowerRamBody2.translateY = currentY + 30.0 - centerOffset
    pipe.object3D.children.add(lowerRamBody2)

    val corners2 = listOf(
        Triple(-boxWidth/2 + margin, -boxHeight/2 + margin+120, 0.0),  // Bottom-Left - centered in Z
        Triple( boxWidth/2 - margin, -boxHeight/2 + margin+120, 0.0),  // Bottom-Right - centered in Z
        Triple(-boxWidth/2 + margin,  boxHeight/2 - margin+120, 0.0),  // Top-Left - centered in Z
        Triple( boxWidth/2 - margin,  boxHeight/2 - margin+120, 0.0)   // Top-Right - centered in Z
    )

    for (corner in corners2) {
        val screw = Cylinder(2.0, boxDepth + 10.0)  // Longer than box depth to stick out both sides
        screw.material = boltMaterial
        screw.translateX = corner.first
        screw.translateY = lowerRamBody.translateY +30.0+ corner.second
        screw.translateZ = corner.third
        screw.rotationAxis = Rotate.X_AXIS
        screw.rotate = 90.0
        pipe.object3D.children.add(screw)
    }

    val lowerRamBody5 = Box(70.0, 66.0, 40.0)  // Width (X), Height (Y), Depth (Z)
    lowerRamBody5.material = silverMaterial
    lowerRamBody5.translateY = currentY + 30.0 - centerOffset
    pipe.object3D.children.add(lowerRamBody5)

    val lowerRamBody6 = Box(66.0, 62.0, 44.0)  // Width (X), Height (Y), Depth (Z)
    lowerRamBody6.material = bodyMaterial
    lowerRamBody6.translateY = currentY + 30.0 - centerOffset
    pipe.object3D.children.add(lowerRamBody6)

    val leftPipeRam3 = Box(75.0, 25.0, 15.0)
    leftPipeRam3.material = silverMaterial
    leftPipeRam3.translateX = -15.0
    leftPipeRam3.translateY = centerOffset+30.0
    leftPipeRam3.translateZ = 0.0
    pipe.object3D.children.add(leftPipeRam3)


    val rightPipeRam4 = Box(75.0, 25.0, 15.0)
    rightPipeRam4.material = silverMaterial
    rightPipeRam4.translateX = 15.0
    rightPipeRam4.translateY = centerOffset+30.0
    rightPipeRam4.translateZ = 0.0
    pipe.object3D.children.add(rightPipeRam4)


    val leftBonnet3 = Cylinder(r(12.0), 20.0)
    leftBonnet3.material = flangeMaterial
    leftBonnet3.translateX = -60.0
    leftBonnet3.translateY = centerOffset+30.0
    leftBonnet3.rotationAxis = Rotate.Z_AXIS
    leftBonnet3.rotate = 90.0
    pipe.object3D.children.add(leftBonnet3)


    val rightBonnet4 = Cylinder(r(12.0), 20.0)
    rightBonnet4.material = flangeMaterial
    rightBonnet4.translateX = 60.0
    rightBonnet4.translateY = centerOffset+30.0
    rightBonnet4.rotationAxis = Rotate.Z_AXIS
    rightBonnet4.rotate = 90.0
    pipe.object3D.children.add(rightBonnet4)

    pipe.movRAM = listOf(Pair(rightBonnet4 ,leftBonnet3))

    currentY += 60.0
    val upperRamBody = Cylinder(r(34.0), 30.0)
    upperRamBody.material = bodyMaterial
    upperRamBody.translateY = currentY + 10.0 - centerOffset
    pipe.object3D.children.add(upperRamBody)
    val upperRamY = currentY + 20.0 - centerOffset

    currentY += 20.0


    for (x in listOf(1, 2, 3, 4)) {
        val actuator = Cylinder(r(4.0), 59.0)
        actuator.material = boltMaterial
        actuator.translateX = 0.0
        actuator.translateY = currentY-110.0//66.0 - centerOffset
        actuator.transforms.addAll(
            Rotate(90.0, Rotate.Z_AXIS),      // Make horizontal
            Rotate(x * 45.0, Rotate.X_AXIS)   // Rotate around X axis for positioning
        )

        pipe.object3D.children.add(actuator)

    }
    val upperConnector = Cylinder(r(56.0), 18.0)
    upperConnector.material = bodyMaterial
    upperConnector.translateY = currentY + 9.0 - centerOffset
    pipe.object3D.children.add(upperConnector)
    currentY += 18.0


    val riserAdapter = Cylinder(r(50.0), 20.0)
    riserAdapter.material = flangeMaterial
    riserAdapter.translateY = currentY + 10.0 - centerOffset
    pipe.object3D.children.add(riserAdapter)
    currentY += 20.0

    val flexCyl = Cylinder(r(40.0), 12.0)
    flexCyl.material = silverMaterial
    flexCyl.translateY = currentY + 0.0 - centerOffset
    pipe.object3D.children.add(flexCyl)





}
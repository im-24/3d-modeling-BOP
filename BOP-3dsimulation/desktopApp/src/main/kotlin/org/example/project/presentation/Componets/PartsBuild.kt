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
import org.example.project.presentation.theme.Material.bodyMaterial
import org.example.project.presentation.theme.Material.boltMaterial
import org.example.project.presentation.theme.Material.flangeMaterial
import org.example.project.presentation.theme.Material.silverMaterial
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
    }
    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //


    ////////////////////////////////////////////////////////////////////////////////
    // 0. Wellhead Housing
    val wellhead2 = Cylinder(r(53.0), 20.0)
    wellhead2.material = bodyMaterial
    wellhead2.translateY = currentY + 20.0 - centerOffset
    annular.object3D.children.add(wellhead2)
    currentY += 20.0

    // 2. Base Flange
    val baseFlange = Cylinder(r(40.0), 20.0)
    baseFlange.material = flangeMaterial
    baseFlange.translateY = currentY + 4.0 - centerOffset
    annular.object3D.children.add(baseFlange)
    currentY += 8.0



}

fun createBlind(blind: PartViewModel) {
    // 3. Hydraulic Connector (H4)
    val h4Connector = Cylinder(r(36.0), 18.0)
    h4Connector.material = flangeMaterial
    h4Connector.translateY = currentY + 9.0 - centerOffset
    blind.object3D.children.add(h4Connector)
    currentY += 18.0

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


    val lowerRamY = currentY + 20.0 - centerOffset

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
        Pair( leftBonnet , rightBonnet ),
        Pair(rightPipeRam2, leftPipeRam2)
    )

    currentY += 30.0



}

fun createSpool(spool: PartViewModel) {


}

fun createPipe(pipe: PartViewModel) {

}
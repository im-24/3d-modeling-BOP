package org.example.project.presentation.Componets

import com.sun.java.swing.plaf.windows.TMSchema
import javafx.scene.Group
import javafx.scene.shape.Cylinder
import org.example.project.Domain.model.part
import org.example.project.Domain.model.r
import org.example.project.presentation.theme.Material
import org.example.project.presentation.theme.Material.bodyMaterial
import org.example.project.presentation.theme.Material.boltMaterial
import org.example.project.presentation.theme.Material.flangeMaterial
import org.example.project.presentation.viewModel.BopViewModel
import org.example.project.presentation.viewModel.PartViewModel


var currentY = 0.0
val centerOffset = 242.0/2

fun PartsBuild(parent: BopViewModel ) {

    var annular = PartViewModel(
        parent ,
        listOf() ,
        0.0 ,
        Pair( Group(), Group()),
        true ,
        Group(),

    )

    createAnnular(annular)

    var blind = PartViewModel(
        parent ,
        listOf() ,
        0.0 ,
        Pair( Group(), Group()),
        true ,
        Group(),

        )
    createBlind(blind)

    var spool = PartViewModel(
        parent ,
        listOf() ,
        0.0 ,
        Pair( Group(), Group()),
        true ,
        Group(),

        )
    createSpool(spool)

    var pipe = PartViewModel(
        parent ,
        listOf() ,
        0.0 ,
        Pair( Group(), Group()),
        true ,
        Group(),

        )

    createPipe(pipe)

    parent.objects3d.children.addAll(pipe.object3D,
        annular.object3D,
        spool.object3D,
        blind.object3D )

}

fun createAnnular(annular : PartViewModel ) {

    val wellhead = Cylinder(r(75.0), 45.0)
    wellhead.material = bodyMaterial
    wellhead.translateY = currentY + 10.0 - centerOffset
    annular.object3D.children.add(wellhead)

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
        val annularTop = wellhead.translateY - (165.0 / 2)
        val flangeH = 6.0
        val bodyH = 20.0
        val bottomFlange = Cylinder(r(50.0), flangeH)
        bottomFlange.material = flangeMaterial
        bottomFlange.translateY = annularTop + flangeH / 2
        annular.object3D.children.add(bottomFlange)

        val riserBody = Cylinder(r(40.0), bodyH)
        riserBody.material = bodyMaterial
        riserBody.translateY = bottomFlange.translateY + flangeH / 2 + bodyH / 2
        annular.object3D.children.add(riserBody)

        val topFlange = Cylinder(r(50.0), flangeH)
        topFlange.material = flangeMaterial
        topFlange.translateY = riserBody.translateY + bodyH / 2 + flangeH / 2
        annular.object3D.children.add(topFlange)

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
            val wellhead2 = Cylinder(r(53.0), 20.0)
            wellhead2.material = bodyMaterial
            wellhead2.translateY = currentY + 20.0 - centerOffset
            annular.object3D.children.add(wellhead2)
            currentY += 20.0






}

fun createBlind(blind: PartViewModel){

}

fun createSpool(spool: PartViewModel){


}

fun createPipe(pipe:  PartViewModel){

}
package Domain.Entities

import Data.Material
import javafx.scene.Group
import javafx.scene.shape.Cylinder

fun part1 (currentY : Double , bopGroup: Group, centerOffset : Double) : Double {
    fun r(dia: Double) = dia / 2.0

    var currenty = currentY

    val wellhead = Cylinder(r(75.0), 45.0)
    wellhead.material = Material.bodyMaterial
    wellhead.translateY = currenty + 10.0 - centerOffset
    bopGroup.children.add(wellhead)
    currenty += 20.0

    val boltCount = 12
    val radius = 29.0   // slightly bigger than wellhead radius (75)
    val boltHeight = 18.0

    for (i in 0 until boltCount) {
        val angle = Math.toRadians((360.0 / boltCount) * i)

        val x = radius * Math.cos(angle)
        val z = radius * Math.sin(angle)

        val bolt = Cylinder(2.0, boltHeight) // small vertical bolt
        bolt.material = Material.boltMaterial

        bolt.translateX = x
        bolt.translateZ = z
        bolt.translateY = currenty - 32.5 - centerOffset // top of wellhead

        bopGroup.children.add(bolt)

    }
    val annularTop = wellhead.translateY - (165.0 / 2)

    val flangeH = 6.0
    val bodyH = 20.0
    val bottomFlange = Cylinder(r(50.0), flangeH)
    bottomFlange.material =  Material.flangeMaterial
    bottomFlange.translateY = annularTop + flangeH / 2
    bopGroup.children.add(bottomFlange)
    // ===== MAIN BODY (NO GAP) =====
    val riserBody = Cylinder(r(40.0), bodyH)
    riserBody.material =  Material.bodyMaterial
    riserBody.translateY = bottomFlange.translateY + flangeH / 2 + bodyH / 2
    bopGroup.children.add(riserBody)
    // ===== TOP FLANGE (NO GAP) =====
    val topFlange = Cylinder(r(50.0), flangeH)
    topFlange.material = Material.flangeMaterial
    topFlange.translateY = riserBody.translateY + bodyH / 2 + flangeH / 2
    bopGroup.children.add(topFlange)

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
        bolt.material = Material.boltMaterial

        bolt.translateX = x
        bolt.translateZ = z
        bolt.translateY = topFlangeTop + boltH / 2-40.0

        bopGroup.children.add(bolt)
    }
    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //


    ////////////////////////////////////////////////////////////////////////////////
    // 0. Wellhead Housing
    val wellhead2 = Cylinder(r(53.0), 20.0)
    wellhead2.material =  Material.bodyMaterial
    wellhead2.translateY = currenty + 20.0 - centerOffset
    bopGroup.children.add(wellhead2)
    currenty += 20.0

    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************
    //                        "Flex Joint"
    val casingAdapter = Cylinder(r(40.0), 14.0)
    casingAdapter.material =  Material.blackish //flangeMaterial
    casingAdapter.translateY = currenty - 50.0 - centerOffset
    bopGroup.children.add(casingAdapter)
    currenty += 10.0
    //**************************************************************************************************************
    //**************************************************************************************************************
    //**************************************************************************************************************


    // 2. Base Flange
    val baseFlange = Cylinder(r(40.0), 20.0)
    baseFlange.material =  Material.flangeMaterial
    baseFlange.translateY = currenty + 4.0 - centerOffset
    bopGroup.children.add(baseFlange)
    currenty += 8.0

    // 3. Hydraulic Connector (H4)
    val h4Connector = Cylinder(r(36.0), 18.0)
    h4Connector.material =  Material.bodyMaterial
    h4Connector.translateY = currenty + 9.0 - centerOffset
    bopGroup.children.add(h4Connector)

    currenty += 18.0
    return currenty

}

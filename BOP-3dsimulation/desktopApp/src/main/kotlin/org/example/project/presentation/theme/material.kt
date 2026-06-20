package org.example.project.presentation.theme


import javafx.scene.paint.Color
import javafx.scene.paint.Material
import javafx.scene.paint.PhongMaterial


object Material {
    val bodyMaterial:Material = PhongMaterial(Color.rgb(180, 70, 50)).apply { specularPower=100.0 }    // Industrial red
    val flangeMaterial:Material = PhongMaterial(Color.rgb(200, 90, 65)).apply { specularPower=100.0 }    // Lighter red
    val actuatorMaterial:Material = PhongMaterial(Color.rgb(70, 120, 180)).apply { specularPower=100.0 } // Blue hydraulics
    val boltMaterial:Material = PhongMaterial(Color.rgb(120, 120, 130)).apply { specularPower=100.0 }
    val silverMaterial:Material = PhongMaterial(Color.rgb(190, 190, 210)).apply { specularPower=100.0 }  // Silver/chrome
    val valveMaterial :Material = PhongMaterial(Color.rgb(100, 150, 100))   // Green valves
    val controlMaterial:Material  = PhongMaterial(Color.rgb(80, 100, 120)) // Control pod
    val yellowish :Material = PhongMaterial(Color.rgb(255, 255, 0))  // Yellow for body detection
    val blackish :Material = PhongMaterial(Color.rgb(50, 35, 25))
    val tubeMaterial:Material = PhongMaterial().apply {
        diffuseColor = Color.DARKGRAY
        specularColor = Color.WHITE
        specularPower = 100.0
    }

    /** Violet highlight — used when a RAM shape is selected for keyboard control */
    val selected       = PhongMaterial(Color.rgb(198, 120, 221))  // #c678dd
    val test = PhongMaterial(Color.rgb(0, 255, 0)).apply {specularPower=100.0 }

}

package org.example.project.presentation.viewModel

import javafx.scene.Group
import javafx.scene.shape.Shape3D
import org.example.project.presentation.theme.Material

class PartViewModel(
    val parent: BopViewModel,
    val children: List<Shape3D>,
    val height: Double,
    var movRAM: List<Pair<Shape3D, Shape3D>>? = null,
    var visible: Boolean = true,
    val object3D: Group,
    var selected: Boolean = false,
) {
    fun select() {
        selected = true
        // Apply selection highlight to all children
        object3D.children.forEach { node ->
            if (node is Shape3D) {
                // Store original material if needed
                node.material = Material.selected
            }
        }
    }

    fun deselect() {
        selected = false
        // Restore materials - you'd need to store original materials
    }

    fun open() {
        movRAM?.forEach { (right, left) ->
            // Move outward to simulate opening
            right.translateX = 15.0
            left.translateX = -15.0
        }
    }

    fun close() {
        movRAM?.forEach { (right, left) ->
            // Move inward to simulate closing
            right.translateX = 60.0
            left.translateX = -60.0
        }
    }
}
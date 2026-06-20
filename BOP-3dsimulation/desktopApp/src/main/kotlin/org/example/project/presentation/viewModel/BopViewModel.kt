package org.example.project.presentation.viewModel

import javafx.scene.Group
import org.example.project.Domain.model.Rotation
import org.example.project.Domain.model.part
import org.example.project.Domain.model.position
import org.example.project.Domain.model.scale

class BopViewModel(
    id: Int,
    totalheight: Double,
    position: position = position(0.0, 0.0, 0.0),
    scale: Double = 3.0,
    rotation: Rotation = Rotation(0.0, 0.0, 0.0),
    parts: List<part>? = null,
    objects3d: Group,
    isvisible: Boolean = true,
    selected: Boolean = true,
) {
    val id          = id
    var totalheight = totalheight
    var position    = position
    var scale       = scale
    var rotation    = rotation
    val parts       = parts
    var isvisible   = isvisible
    var selected    = selected
    var objects3d   = objects3d

    /** Named parts registered by PartsBuild so the control panel can bind to them. */
    val namedParts: MutableMap<String, PartViewModel> = mutableMapOf()

    fun visible(visible: Boolean) {
        this.isvisible = visible
        this.parts?.forEach { it.visible = visible }
    }
}
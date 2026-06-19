package org.example.project.Domain.model

import javafx.scene.Group
import javafx.scene.shape.Shape3D
import org.example.project.presentation.viewModel.BopViewModel

data class BOPobject(
    var totalheight: Double,
    var position: position = position(0.0, 0.0, 0.0),
    var scale: scale = scale(1.0),
    var rotation: Rotation = Rotation(0.0, 0.0, 0.0),
    val parts: List<part>,
    var objects3D : Group ,
    var isvisible: Boolean = true,
    var selected: Boolean = true,

)

data class part(
    val parent: BopViewModel,
    val height: Double,
    val movRAM: Pair<Group,Group>,
    var visible: Boolean = true,
    var object3D: Group,
    var selected: Boolean = false,
    )



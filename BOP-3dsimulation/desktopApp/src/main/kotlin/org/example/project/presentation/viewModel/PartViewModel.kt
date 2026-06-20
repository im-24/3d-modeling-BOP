package org.example.project.presentation.viewModel

import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.Shape3D
import org.example.project.Domain.model.BOPobject
import org.example.project.presentation.theme.Material

class PartViewModel(
    parent: BopViewModel,
    children: List<Shape3D> ?= null,
    height: Double,
    movRAM: List<Pair<Shape3D, Shape3D> >,
    visible: Boolean = true,
    object3D : Group,
    selected: Boolean = false,
  ){
    val parent= parent
    val height= height
    var movRAM= movRAM
    var visible= visible
    var selected= selected
    val children= children
    var object3D= object3D


    fun select(){
        selected=true
        this.children?.forEach {
            it.material = Material.selected
        }
    }

}

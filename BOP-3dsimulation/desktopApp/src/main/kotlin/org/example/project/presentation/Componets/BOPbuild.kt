package org.example.project.presentation.Componets

import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.shape.Cylinder
import org.example.project.Domain.model.r
import org.example.project.presentation.viewModel.BopViewModel
import org.example.project.presentation.viewModel.PartViewModel
import org.example.project.presentation.viewModel.view
import java.awt.Component

fun BOPbuild() : BopViewModel {
    var BOP = BopViewModel(
        1,
        241.0 ,
        parts = listOf(),
        objects3d = Group(),
    )
    PartsBuild(BOP)

    return BOP

}
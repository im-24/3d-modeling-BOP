package org.example.project.presentation.Componets

import javafx.scene.Group
import org.example.project.presentation.viewModel.BopViewModel
import org.example.project.presentation.viewModel.PartViewModel

fun BOPbuild(): BopViewModel {
    val bop = BopViewModel(
        id        = 1,
        totalheight = 241.0,
        parts     = listOf(),
        objects3d = Group(),
    )
    PartsBuild(bop)
    return bop
}
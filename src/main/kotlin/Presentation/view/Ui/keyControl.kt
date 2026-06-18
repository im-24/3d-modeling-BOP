package Domain.Entities

import SceneViewModel
import javafx.animation.TranslateTransition
import javafx.scene.Node
import javafx.scene.input.KeyCode
import javafx.util.Duration

fun keyControlRAM (nodes: List<Node> , viewModel: SceneViewModel) {
  val transitions = listOf<Triple<Double, Double, Double>>(
  )

    
    nodes.zip(transitions).forEach { (node, translation) ->
        node.translateX += translation.first
    }

}

package org.example.project.presentation.pane.ControlViw

import javafx.animation.TranslateTransition
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Shape3D
import javafx.util.Duration
import org.example.project.presentation.viewModel.BopViewModel
import org.example.project.presentation.viewModel.PartViewModel
import org.example.project.presentation.viewModel.view
import org.example.project.presentation.theme.Material

// ─────────────────────────────────────────────────────────────────
//  Colours
// ─────────────────────────────────────────────────────────────────
private const val BG       = "#12131a"
private const val SURFACE  = "#1c1d2b"
private const val SURFACE2 = "#252638"
private const val BORDER   = "#2e3050"
private const val TEXT     = "white"
private const val MUTED    = "#8888aa"
private const val ACCENT_X = "#e06c75"
private const val ACCENT_Y = "#98c379"
private const val ACCENT_Z = "#61afef"
private const val ACCENT_S = "#c678dd"

// ─────────────────────────────────────────────────────────────────
//  Small helpers
// ─────────────────────────────────────────────────────────────────
private fun sectionLabel(text: String) = Label(text).apply {
    style = "-fx-text-fill: $MUTED; -fx-font-size: 10px; -fx-font-weight: bold;"
    padding = Insets(6.0, 0.0, 2.0, 0.0)
}

private fun axisLabel(text: String, color: String) = Label(text).apply {
    style = "-fx-text-fill: $color; -fx-font-size: 11px; -fx-font-weight: bold;"
    minWidth = 14.0
}

private fun panelSeparator() = Separator().apply {
    padding = Insets(4.0, 0.0, 4.0, 0.0)
    style   = "-fx-background-color: $BORDER;"
}

/**
 * One axis row: label | spinner | slider (slider stretches to fill remaining width).
 * The HBox is set to grow so the slider always fills available space.
 */
private fun axisRow(
    label: String,
    accent: String,
    prop: SimpleDoubleProperty,
    min: Double = 0.0,
    max: Double = 360.0
): HBox {
    val step = if (max <= 5.0) 0.05 else 1.0

    val spinner = Spinner<Double>(min, max, prop.get(), step).apply {
        isEditable = true
        prefWidth  = 72.0
        minWidth   = 72.0
        maxWidth   = 72.0
        style      = "-fx-background-color: $SURFACE2; -fx-text-fill: $TEXT; -fx-font-size: 11px;"
        valueFactory.valueProperty().bindBidirectional(prop.asObject())
    }

    val slider = Slider(min, max, prop.get()).apply {
        majorTickUnit  = if (max <= 5.0) 1.0 else 90.0
        minorTickCount = if (max <= 5.0) 1   else 2
        isShowTickMarks   = true
        isShowTickLabels  = true
        blockIncrement = if (max <= 5.0) 0.1 else 5.0
        style = "-fx-control-inner-background: $SURFACE2; -fx-accent: $accent;"
        valueProperty().bindBidirectional(prop)
        // let the slider stretch
        HBox.setHgrow(this, Priority.ALWAYS)
        maxWidth = Double.MAX_VALUE
    }

    return HBox(8.0, axisLabel(label, accent), spinner, slider).apply {
        alignment = Pos.CENTER_LEFT
        padding   = Insets(2.0, 0.0, 2.0, 0.0)
        // make the whole row stretch too
        maxWidth  = Double.MAX_VALUE
    }
}

// ─────────────────────────────────────────────────────────────────
//  Keyboard RAM controller  —  selection is per PAIR
// ─────────────────────────────────────────────────────────────────
class RamController(namedParts: Map<String, PartViewModel>) {

    /**
     * One selectable unit = one pair of opposing RAM halves.
     * [shapeA] moves to -openOffset, [shapeB] moves to +openOffset.
     * Parts with an empty movRAM list (e.g. spool) are skipped entirely.
     */
    data class RamPair(
        val key: String,                     // unique id, e.g. "blind-0"
        val label: String,                   // display name, e.g. "Blind RAM pair 1"
        val shapeA: Shape3D,                 // left / top half
        val shapeB: Shape3D,                 // right / bottom half
        val openOffset: Double  = 60.0,
        val closedOffset: Double = 25.0,
    )

    val pairs: MutableList<RamPair> = mutableListOf()
    internal var selectedIndex = -1
    internal val pairState: MutableMap<String, Boolean> = mutableMapOf()
    /** Called after every selection or open/close action — wire to your UI refresh. */
    var onChanged: (() -> Unit)? = null // key → isOpen

    init {
        // Only parts that actually have movRAM entries are included.
        // "spool" has none so it is naturally skipped by the ?: continue guard.
        val order = listOf("annular", "blind", "pipe")
        for (name in order) {
            val part = namedParts[name] ?: continue
            val movRAM = part.movRAM?.takeIf { it.isNotEmpty() } ?: continue
            val display = name.replaceFirstChar { it.uppercaseChar() }
            movRAM.forEachIndexed { idx, (a, b) ->
                val key   = "$name-$idx"
                val label = if (movRAM.size == 1) "$display RAM" else "$display RAM ${idx + 1}"
                pairState[key] = false
                pairs.add(RamPair(key, label, a, b))
            }
        }
    }

    val hasEntries get() = pairs.isNotEmpty()

    // ── Selection ──────────────────────────────────────────────────

    fun select(index: Int) {
        // Deselect old pair — restore both shapes
        if (selectedIndex in pairs.indices) {
            val old = pairs[selectedIndex]
            old.shapeA.material = Material.flangeMaterial
            old.shapeB.material = Material.flangeMaterial
        }
        selectedIndex = index.coerceIn(pairs.indices)
        // Highlight new pair — both shapes
        if (selectedIndex in pairs.indices) {
            val p = pairs[selectedIndex]
            p.shapeA.material = Material.selected
            p.shapeB.material = Material.selected
        }
        onChanged?.invoke()
    }

    fun selectNext() { if (hasEntries) select((selectedIndex + 1) % pairs.size) }
    fun selectPrev() { if (hasEntries) select((selectedIndex - 1 + pairs.size) % pairs.size) }

    // ── Open / close / toggle ──────────────────────────────────────

    fun toggleSelected() {
        val p = currentPair() ?: return
        animatePair(p, open = !(pairState[p.key] ?: false))
    }

    fun openSelected() {
        val p = currentPair() ?: return
        if (pairState[p.key] == true) return
        animatePair(p, open = true)
    }
    val offsetitem = 0.0

    fun closeSelected() {
        val p = currentPair() ?: return
        if (pairState[p.key] == false) return
        animatePair(p, open = false)
    }
    private fun animatePair(pair: RamPair, open: Boolean) {
        pairState[pair.key] = open
        val targetA = if (open) -pair.openOffset -offsetitem else pair.closedOffset -offsetitem
        val targetB = if (open)  pair.openOffset - offsetitem else pair.closedOffset -offsetitem

        // Set the final position immediately
        // Then animate from current to target
        val startA = pair.shapeA.translateX - offsetitem
        val startB = pair.shapeB.translateX -offsetitem

        // Calculate the difference
        val deltaA = targetA - startA
        val deltaB = targetB - startB

        // Animate using a timeline for more control
        val anim = javafx.animation.Timeline(
            javafx.animation.KeyFrame(
                javafx.util.Duration.seconds(0.8),
                javafx.animation.KeyValue(pair.shapeA.translateXProperty(), targetA),
                javafx.animation.KeyValue(pair.shapeB.translateXProperty(), targetB)
            )
        )
        anim.play()

        onChanged?.invoke()
    }

    // ── Status label ───────────────────────────────────────────────

    fun currentLabel(): String {
        val p  = currentPair() ?: return "None selected"
        val st = if (pairState[p.key] == true) "OPEN" else "CLOSED"
        return "${p.label}  [$st]"
    }

    private fun currentPair(): RamPair? =
        if (selectedIndex in pairs.indices) pairs[selectedIndex] else null
}

// ─────────────────────────────────────────────────────────────────
//  Main control panel
// ─────────────────────────────────────────────────────────────────
class controlPanel(
    view: view,
    bop: BopViewModel,
    namedParts: Map<String, PartViewModel> = emptyMap()
) : VBox() {

    private val ramCtrl = RamController(namedParts)

    init {
        spacing  = 0.0
        padding  = Insets(0.0)
        // No fixed pixel width — let BorderPane drive the width.
        // minWidth prevents it collapsing to zero when the window is too narrow.
        minWidth  = 220.0
        style     = "-fx-background-color: $BG;"
        // Panel always fills the height given by BorderPane
        VBox.setVgrow(this, Priority.ALWAYS)

        val tabControls = makeTabButton("Controls")
        val tabSettings = makeTabButton("Settings")
        val tabBar = HBox(tabControls, tabSettings).apply {
            style    = "-fx-background-color: $SURFACE; -fx-border-color: $BORDER; -fx-border-width: 0 0 1 0;"
            minWidth = 0.0
        }
        HBox.setHgrow(tabControls, Priority.ALWAYS)
        HBox.setHgrow(tabSettings, Priority.ALWAYS)

        // ── Scrollable content areas
        val controlsContent  = buildControlsSection(view)
        val settingsContent  = buildSettingsSection(view, namedParts)

        val controlsScroll = scrollPane(controlsContent)
        val settingsScroll = scrollPane(settingsContent).also {
            it.isVisible  = false
            it.isManaged  = false
        }

        // Each scroll pane grows to fill remaining height
        VBox.setVgrow(controlsScroll, Priority.ALWAYS)
        VBox.setVgrow(settingsScroll, Priority.ALWAYS)

        tabControls.setOnAction {
            controlsScroll.isVisible = true;  controlsScroll.isManaged = true
            settingsScroll.isVisible = false; settingsScroll.isManaged = false
            tabControls.style = activeTabStyle()
            tabSettings.style = inactiveTabStyle()
        }
        tabSettings.setOnAction {
            controlsScroll.isVisible = false; controlsScroll.isManaged = false
            settingsScroll.isVisible = true;  settingsScroll.isManaged = true
            tabControls.style = inactiveTabStyle()
            tabSettings.style = activeTabStyle()
        }
        tabControls.style = activeTabStyle()
        tabSettings.style = inactiveTabStyle()

        children.addAll(tabBar, controlsScroll, settingsScroll)
    }

    // ── Wrap a VBox in a scroll pane that fits the panel width
    private fun scrollPane(content: VBox) = ScrollPane(content).apply {
        isFitToWidth  = true   // content width = scroll pane width (no horizontal scroll)
        isFitToHeight = false  // vertical scroll when needed
        hbarPolicy    = ScrollPane.ScrollBarPolicy.NEVER
        vbarPolicy    = ScrollPane.ScrollBarPolicy.AS_NEEDED
        style         = "-fx-background: $BG; -fx-background-color: $BG; -fx-border-color: transparent;"
        maxWidth      = Double.MAX_VALUE
        maxHeight     = Double.MAX_VALUE
    }

    // ─────────────────────────────────────────────────────────────
    //  Tab button styles
    // ─────────────────────────────────────────────────────────────
    private fun makeTabButton(text: String) = Button(text).apply {
        maxWidth           = Double.MAX_VALUE
        isFocusTraversable = false
        style              = inactiveTabStyle()
    }
    private fun activeTabStyle()   =
        "-fx-background-color: $BG; -fx-text-fill: $TEXT; -fx-font-weight: bold; " +
                "-fx-font-size: 12px; -fx-border-color: transparent; -fx-cursor: default;"
    private fun inactiveTabStyle() =
        "-fx-background-color: $SURFACE; -fx-text-fill: $MUTED; " +
                "-fx-border-color: transparent; -fx-font-size: 12px; -fx-cursor: hand;"

    // ─────────────────────────────────────────────────────────────
    //  CONTROLS section
    // ─────────────────────────────────────────────────────────────
    private fun buildControlsSection(view: view) = VBox(6.0).apply {
        padding  = Insets(14.0)
        maxWidth = Double.MAX_VALUE
        style    = "-fx-background-color: $BG;"

        children += sectionLabel("ROTATION")
        children += axisRow("X", ACCENT_X, view.rotationx)
        children += axisRow("Y", ACCENT_Y, view.rotationy)
        children += axisRow("Z", ACCENT_Z, view.rotationz)
        children += panelSeparator()
        children += sectionLabel("ZOOM")
        children += axisRow("∑", ACCENT_S, view.scale, 0.1, 5.0)
        children += panelSeparator()

        children += Button("Reset view").apply {
            maxWidth = Double.MAX_VALUE
            style    = "-fx-background-color: $SURFACE2; -fx-text-fill: $TEXT; " +
                    "-fx-border-color: $BORDER; -fx-border-radius: 4; -fx-background-radius: 4; " +
                    "-fx-font-size: 11px; -fx-cursor: hand;"
            setOnAction { view.resetView() }
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  SETTINGS section
    // ─────────────────────────────────────────────────────────────
    private fun buildSettingsSection(view: view, namedParts: Map<String, PartViewModel>) =
        VBox(6.0).apply {
            padding  = Insets(14.0)
            maxWidth = Double.MAX_VALUE
            style    = "-fx-background-color: $BG;"

            // Part toggles
            children += sectionLabel("PART VISIBILITY")
            listOf(
                Triple("Annular",   view.annularVisible, namedParts["annular"]),
                Triple("Pipe RAM",  view.pipeVisible,    namedParts["pipe"]),
                Triple("Spool",     view.spoolVisible,   namedParts["spool"]),
                Triple("Blind RAM", view.blindVisible,   namedParts["blind"])
            ).forEach { (name, prop, part) ->
                children += partToggleRow(name, prop, part)
            }

            children += panelSeparator()

            // Background colour
            children += sectionLabel("BACKGROUND COLOR")
            children += ColorPicker(view.backgroundColor.get()).apply {
                maxWidth = Double.MAX_VALUE
                style    = "-fx-background-color: $SURFACE2; -fx-border-color: $BORDER; " +
                        "-fx-border-radius: 4; -fx-background-radius: 4;"
                setOnAction { view.backgroundColor.set(value) }
            }

            children += panelSeparator()

            // Keyboard RAM control
            children += sectionLabel("KEYBOARD RAM CONTROL")
            children += CheckBox("Enable keyboard control").apply {
                style = "-fx-text-fill: $TEXT; -fx-font-size: 11px;"
                selectedProperty().bindBidirectional(view.keyboardControlEnabled)
            }

            if (ramCtrl.hasEntries) {
                // Show one row per selectable pair so the user sees the full list
                val pairLabels = ramCtrl.pairs.mapIndexed { idx, pair ->
                    Label("${idx + 1}.  ${pair.label}").apply {
                        style      = "-fx-text-fill: $MUTED; -fx-font-size: 10px;"
                        maxWidth   = Double.MAX_VALUE
                        isWrapText = true
                    }
                }
                fun refreshLabels() {
                    // Bold + accent the currently selected pair label
                    pairLabels.forEachIndexed { idx, lbl ->
                        val isSelected = idx == ramCtrl.selectedIndex
                        val state      = if (ramCtrl.pairs[idx].let { ramCtrl.pairState[it.key] } == true) "  [OPEN]" else "  [CLOSED]"
                        lbl.text  = "${idx + 1}.  ${ramCtrl.pairs[idx].label}${ if (isSelected) state else "" }"
                        lbl.style = if (isSelected)
                            "-fx-text-fill: #61afef; -fx-font-size: 10px; -fx-font-weight: bold;"
                        else
                            "-fx-text-fill: $MUTED; -fx-font-size: 10px;"
                    }
                }
                refreshLabels()
                ramCtrl.onChanged = { refreshLabels() }

                val pairList = VBox(3.0, *pairLabels.toTypedArray()).apply { maxWidth = Double.MAX_VALUE }

                val prevBtn = Button("▲ Prev pair").apply {
                    HBox.setHgrow(this, Priority.ALWAYS); maxWidth = Double.MAX_VALUE
                    style = "-fx-background-color: $SURFACE2; -fx-text-fill: $MUTED; " +
                            "-fx-border-color: $BORDER; -fx-border-radius: 4; -fx-background-radius: 4; " +
                            "-fx-font-size: 11px; -fx-cursor: hand;"
                    setOnAction { if (view.keyboardControlEnabled.get()) { ramCtrl.selectPrev(); refreshLabels() } }
                }
                val nextBtn = Button("▼ Next pair").apply {
                    HBox.setHgrow(this, Priority.ALWAYS); maxWidth = Double.MAX_VALUE
                    style = prevBtn.style
                    setOnAction { if (view.keyboardControlEnabled.get()) { ramCtrl.selectNext(); refreshLabels() } }
                }
                val toggleBtn = Button("Toggle open / closed  [Enter]").apply {
                    maxWidth = Double.MAX_VALUE
                    style    = "-fx-background-color: $SURFACE2; -fx-text-fill: #c678dd; " +
                            "-fx-border-color: #5a3070; -fx-border-radius: 4; -fx-background-radius: 4; " +
                            "-fx-font-size: 11px; -fx-cursor: hand;"
                    setOnAction { if (view.keyboardControlEnabled.get()) { ramCtrl.toggleSelected(); refreshLabels() } }
                }

                val btnGroup = VBox(6.0, pairList, panelSeparator(),
                    HBox(6.0, prevBtn, nextBtn), toggleBtn).apply {
                    maxWidth = Double.MAX_VALUE
                    disableProperty().bind(view.keyboardControlEnabled.not())
                }
                children += btnGroup
            } else {
                children += Label("No movable RAMs found.").apply {
                    style      = "-fx-text-fill: $MUTED; -fx-font-size: 10px;"
                    isWrapText = true
                }
            }
        }

    // ─────────────────────────────────────────────────────────────
    //  Part toggle row
    // ─────────────────────────────────────────────────────────────
    private fun partToggleRow(
        name: String,
        prop: javafx.beans.property.SimpleBooleanProperty,
        part: PartViewModel?
    ) = ToggleButton(name).apply {
        isSelected = true
        maxWidth   = Double.MAX_VALUE
        style      = toggleStyle(true)
        selectedProperty().addListener { _, _, on ->
            style = toggleStyle(on)
            prop.set(on)
            part?.object3D?.isVisible = on
        }
        prop.addListener { _, _, v -> isSelected = v }
    }

    private fun toggleStyle(on: Boolean) =
        if (on) "-fx-background-color: #1e3a5f; -fx-text-fill: #61afef; " +
                "-fx-border-color: #2a5080; -fx-border-radius: 4; -fx-background-radius: 4; " +
                "-fx-font-size: 11px; -fx-cursor: hand;"
        else    "-fx-background-color: $SURFACE2; -fx-text-fill: $MUTED; " +
                "-fx-border-color: $BORDER; -fx-border-radius: 4; -fx-background-radius: 4; " +
                "-fx-font-size: 11px; -fx-cursor: hand;"

    // ─────────────────────────────────────────────────────────────
    //  Keyboard handler — wire from App.kt scene.setOnKeyPressed
    // ─────────────────────────────────────────────────────────────
    fun handleKey(code: javafx.scene.input.KeyCode, view: view) {
        if (!view.keyboardControlEnabled.get()) return
        when (code) {
            javafx.scene.input.KeyCode.ENTER -> ramCtrl.toggleSelected()
            javafx.scene.input.KeyCode.RIGHT -> ramCtrl.openSelected()
            javafx.scene.input.KeyCode.LEFT  -> ramCtrl.closeSelected()
            javafx.scene.input.KeyCode.UP    -> ramCtrl.selectPrev()
            javafx.scene.input.KeyCode.DOWN  -> ramCtrl.selectNext()
            else -> {}
        }
    }
}
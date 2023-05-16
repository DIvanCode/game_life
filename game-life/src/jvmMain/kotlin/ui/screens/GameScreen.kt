package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import common.Cell
import common.CellState
import common.Game
import common.JsonHandler
import common.interaction.Request
import common.interaction.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import server.controllers.RequestController
import ui.components.ColorPicker
import ui.components.ManageDialog
import ui.components.ManageButton
import ui.components.Screen
import ui.displays.CellDisplay
import ui.displays.FieldDisplay
import ui.system.DirectoryPicker

class GameScreen: Screen() {
    @Composable
    override fun LazyItemScope.draw() {
        fun requestSave() {
            RequestController.handleRequest(Request(
                route = "/save",
                method = Request.POST
            ))
        }

        val gameResponse: Response = RequestController.handleRequest(Request(
            route = "/game",
            method = Request.GET
        ))
        val game = Json.decodeFromString<Game>(gameResponse.body)

        requestSave()

        val colorPicker = ColorPicker(game.settings.cellColors)

        val cellDisplays: MutableList<CellDisplay> = mutableListOf()
        for (i in game.field.cells.indices) {
            val cell = game.field.cells[i]
            cellDisplays.add(CellDisplay(
                color = mutableStateOf(colorPicker.colorById(cell.color)),
                tooltip = mutableStateOf("Nothing")
            ))
        }

        val makeStepButton = ManageButton(text = "Сделать 1 ход")
        val autoStepButton = ManageButton(text = "Автовыполнение")
        val stopButton = ManageButton(text = "Закончить")
        val clearButton = ManageButton(text = "Очистить")
        val fillRandomButton = ManageButton(text = "Заполнить случайно")
        val saveAsButton = ManageButton(text = "Сохранить в папку")
        val saveCurrentAsButton = ManageButton(text = "Сохранить текущее состояние в папку")

        val saveDialog = ManageDialog()

        val saveCurrentStateManager = DirectoryPicker()
        saveCurrentStateManager { path ->
            JsonHandler.writeGame(game, "$path\\game-${java.util.Date().time}.json")
            if (saveDialog.show.value) {
                saveDialog.show.value = false
            }
        }

        saveDialog {
            Dialog(
                title = "Сохранить?",
                onCloseRequest = { saveDialog.show.value = false }
            ) {
                saveCurrentAsButton {
                    saveCurrentStateManager.open()
                }
            }
        }

        val fieldDisplay = FieldDisplay(
            game.settings,
            cellDisplays,
            colorPicker
        )

        var running = false
        val coroutineScope = rememberCoroutineScope()

        fun updateUI(changedCells: MutableList<*>) {
            if (changedCells.isEmpty()) {
                running = false
            }

            for (i in changedCells.indices) {
                val cell = changedCells[i] as Cell
                val index = game.field.index(cell.row, cell.col)
                cellDisplays[index].color.value = colorPicker.colorById(cell.state.color)
                game.field.setState(cell.row, cell.col, cell.state)
            }
        }

        fun requestActivity(route: String) {
            val response = RequestController.handleRequest(Request(
                route = route,
                method = Request.GET
            ))

            updateUI(Json.decodeFromString<MutableList<Cell>>(response.body))
        }

        fun makeStep() {
            requestActivity("/game/step")
        }

        LazyRow {
            item {
                makeStepButton(modifier = Modifier.width(200.dp)) {
                    if (!running) {
                        makeStep()
                        requestSave()
                    }
                }
            }

            item {
                autoStepButton(modifier = Modifier.width(200.dp)) {
                    if (!running) {
                        running = true
                        coroutineScope.launch {
                            requestSave()

                            while (running) {
                                delay(50)
                                makeStep()
                            }

                            requestSave()

                            saveDialog.show.value = true
                        }
                    }
                }
            }

            item {
                stopButton(modifier = Modifier.width(200.dp)) {
                    running = false
                }
            }

            item {
                clearButton(modifier = Modifier.width(200.dp)) {
                    if (!running) {
                        requestActivity("/game/clear")
                        requestSave()
                    }
                }
            }

            item {
                fillRandomButton(modifier = Modifier.width(200.dp)) {
                    if (!running) {
                        requestActivity("/game/random")
                        requestSave()
                    }
                }
            }

            item {
                saveAsButton(modifier = Modifier.width(200.dp)) {
                    if (!running) {
                        requestSave()
                        saveCurrentStateManager.open()
                    }
                }
            }
        }

        colorPicker()

        fieldDisplay { row: Int, col: Int, color: Int ->
            game.field.setState(row, col, CellState(color))
        }
    }
}
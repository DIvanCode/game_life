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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import server.controllers.RequestController
import ui.components.*
import ui.displays.CellDisplay
import ui.displays.FieldDisplay
import ui.system.DirectoryPicker

class GameScreen(
    val onClose: () -> Unit
): Screen() {
    private var game: Game? = null
    private var colorPicker: ColorPicker? = null
    private var cellDisplays: MutableList<CellDisplay>? = null
    private var fieldDisplay: FieldDisplay? = null

    private val makeStepButton = ManageButton(text = "Сделать 1 ход")
    private val autoStepButton = ManageButton(text = "Автозапуск")
    private val stopButton = ManageButton(text = "Закончить")
    private val clearButton = ManageButton(text = "Очистить")
    private val fillRandomButton = ManageButton(text = "Заполнить случайно")
    private val saveGameButton = ManageButton(text = "Сохранить в папку")
    private val closeGameButton = ManageButton(text = "Выйти из этой игры")

    private val autoStepDialog = ManageDialog()
    private val saveGameDialog = ManageDialog()
    private val saveAndCloseGameDialog = ManageDialog()

    private val saveGameManager = DirectoryPicker()
    private val saveAndCloseGameManager = DirectoryPicker()

    private var exactSteps = mutableStateOf(5)
    private var running = false


    @Composable
    override fun LazyItemScope.draw() {
        val coroutineScope = rememberCoroutineScope()

        requestGame()
        requestSave()

        createColorPicker()
        createCellDisplays()

        saveGameManager { path -> saveGame(path) }
        saveAndCloseGameManager { path ->
            saveGame(path)
            requestClose()
            onClose()
        }

        fieldDisplay = FieldDisplay(
            game!!.settings,
            cellDisplays!!,
            colorPicker!!
        )

        manageButtons()
        colorPicker!!()

        fieldDisplay!! { row: Int, col: Int, color: Int ->
            game!!.field.setState(row, col, CellState(color))
        }

        autoStepDialog {
            Dialog(
                title = "Настройка автозапуска",
                onCloseRequest = { autoStepDialog.show.value = false }
            ) {
                val infStepsButton = ManageButton(text = "Играть до остановки")
                val exactStepsButton = ManageButton(text = "Играть ${exactSteps.value} ходов")

                val exactStepsInput = OneRowTextField(
                    text = mutableStateOf(exactSteps.value.toString()),
                    placeholder = "Определённое количество ходов"
                )

                Column {
                    infStepsButton {
                        autoStepDialog.show.value = false
                        autoStep(coroutineScope = coroutineScope)
                    }

                    exactStepsInput {
                        try {
                            exactStepsInput.text.value.toInt()
                            exactSteps.value = exactStepsInput.text.value.toInt()
                        } catch (e: NumberFormatException) {
                            exactStepsInput.errorMessage.value = "Некорректное значение"
                        }
                    }

                    exactStepsButton {
                        autoStepDialog.show.value = false
                        autoStep(
                            exactSteps.value,
                            coroutineScope
                        )
                    }
                }
            }
        }

        saveGameDialog {
            Dialog(
                title = "Сохранить игру",
                onCloseRequest = { autoStepDialog.show.value = false }
            ) {
                val saveButton = ManageButton(text = "Сохранить в папку")
                val withoutSaveButton = ManageButton(text = "Не нужно сохранять")

                Column {
                    saveButton {
                        requestSave()
                        saveGameManager.open()
                        saveGameDialog.show.value = false
                    }

                    withoutSaveButton {
                        requestSave()
                        saveGameDialog.show.value = false
                    }
                }
            }
        }

        saveAndCloseGameDialog {
            Dialog(
                title = "Сохранить игру",
                onCloseRequest = { autoStepDialog.show.value = false }
            ) {
                val saveButton = ManageButton(text = "Сохранить в папку")
                val withoutSaveButton = ManageButton(text = "Не нужно сохранять")

                Column {
                    saveButton {
                        requestSave()
                        saveAndCloseGameManager.open()
                        saveGameDialog.show.value = false
                    }

                    withoutSaveButton {
                        requestSave()
                        saveGameDialog.show.value = false
                        requestClose()
                        onClose()
                    }
                }
            }
        }
    }

    private fun requestGame() {
        val gameResponse: Response = RequestController.handleRequest(Request(
            route = "/game",
            method = Request.GET
        ))

        game = Json.decodeFromString<Game>(gameResponse.body)
    }

    private fun requestSave() {
        RequestController.handleRequest(Request(
            route = "/save",
            method = Request.POST
        ))
    }

    private fun createColorPicker() {
        colorPicker = ColorPicker(game!!.settings.cellColors)
    }

    private fun createCellDisplays() {
        cellDisplays = mutableListOf()
        for (i in game!!.field.cells.indices) {
            val cell = game!!.field.cells[i]
            cellDisplays!!.add(CellDisplay(
                color = mutableStateOf(colorPicker!!.colorById(cell.color)),
                tooltip = mutableStateOf(cell.aliveGenerations.toString())
            ))
        }
    }

    private fun saveGame(path: String) {
        JsonHandler.writeGame(game!!, "$path\\game-${java.util.Date().time}.json")
    }

    private fun autoStep(steps: Int = -1, coroutineScope: CoroutineScope) {
        if (running) {
            return
        }

        requestNewGeneration()
        running = true
        coroutineScope.launch {
            requestSave()

            var done = 0
            while (running && done != steps) {
                delay(50)
                makeStep()
                ++done
            }

            requestSave()
            saveGameDialog.show.value = true
        }
    }

    private fun requestAction(route: String) {
        val response = RequestController.handleRequest(Request(
            route = route,
            method = Request.GET
        ))

        updateUI(Json.decodeFromString<MutableList<Cell>>(response.body))
    }

    private fun makeStep() {
        requestAction("/game/step")
    }

    private fun requestNewGeneration() {
        val response = RequestController.handleRequest(Request(
            route = "/generation",
            method = Request.GET
        ))
    }

    private fun updateUI(changedCells: MutableList<*>) {
        if (changedCells.isEmpty()) {
            running = false
        }

        for (i in changedCells.indices) {
            val cell = changedCells[i] as Cell
            val index = game!!.field.index(cell.row, cell.col)
            cellDisplays!![index].changeColor(colorPicker!!.colorById(cell.state.color))
            cellDisplays!![index].changeTooltip(cell.state.aliveGenerations.toString())
            game!!.field.setState(cell.row, cell.col, cell.state)
        }
    }

    private fun requestClose() {
        RequestController.handleRequest(Request(
            route = "/close",
            method = Request.POST
        ))
    }

    @Composable
    private fun manageButtons() {
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
                    autoStepDialog.show.value = true
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
                        requestAction("/game/clear")
                        requestSave()
                    }
                }
            }

            item {
                fillRandomButton(modifier = Modifier.width(200.dp)) {
                    if (!running) {
                        requestAction("/game/random")
                        requestSave()
                    }
                }
            }

            item {
                saveGameButton(modifier = Modifier.width(200.dp)) {
                    if (!running) {
                        requestSave()
                        saveGameManager.open()
                    }
                }
            }

            item {
                closeGameButton(modifier = Modifier.width(200.dp)) {
                    saveAndCloseGameDialog.show.value = true
                }
            }
        }
    }
}
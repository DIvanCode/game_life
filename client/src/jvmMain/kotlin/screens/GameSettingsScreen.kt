package client.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import client.models.GameSettings
import client.models.interaction.Response
import client.controllers.Controller
import client.components.ManageButton
import client.components.OneRowTextField
import client.components.Screen
import client.displays.GameSettingsTextDisplay
import client.models.MyJson

class GameSettingsScreen(
    val onStartGame: () -> Unit
): Screen() {
    @Composable
    override fun LazyItemScope.draw() {
        val gameSettingsResponse: Response = Controller.getSettings()
        if (gameSettingsResponse.status == Response.ERROR) {
            return
        }

        var gameSettings = gameSettingsResponse.body["settings"] as GameSettings

        val gameFieldHeightInput = OneRowTextField(
            placeholder = "Высота поля"
        )
        val gameFieldWidthInput = OneRowTextField(
            placeholder = "Ширина поля"
        )
        val gameCellColorsInput = OneRowTextField(
            placeholder = "Количество цветов"
        )
        val gameAliveLeftBorderInput = OneRowTextField(
            placeholder = "Минимальное для продолжения жизни количество соседей с таким же цветом"
        )
        val gameAliveRightBorderInput = OneRowTextField(
            placeholder = "Максимальное для продолжения жизни количество соседей с таким же цветом"
        )
        val gameBirthAmountInput = OneRowTextField(
            placeholder = "Необходимое для зарождения жизни количество живых соседей одного цвета"
        )

        gameFieldHeightInput.text.value = gameSettings.fieldHeight.toString()
        gameFieldWidthInput.text.value = gameSettings.fieldWidth.toString()
        gameCellColorsInput.text.value = gameSettings.cellColors.toString()
        gameAliveLeftBorderInput.text.value = gameSettings.aliveLeftBorder.toString()
        gameAliveRightBorderInput.text.value = gameSettings.aliveRightBorder.toString()
        gameBirthAmountInput.text.value = gameSettings.birthAmount.toString()

        val oneRowTextFields: List<OneRowTextField> = listOf(
            gameFieldHeightInput,
            gameFieldWidthInput,
            gameCellColorsInput,
            gameAliveLeftBorderInput,
            gameAliveRightBorderInput,
            gameBirthAmountInput
        )

        val startGameButton = ManageButton(text = "Начать игру!")

        Column(modifier = Modifier.width(600.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            GameSettingsTextDisplay(
                gameFieldHeightInput.text,
                gameFieldWidthInput.text,
                gameCellColorsInput.text,
                gameAliveLeftBorderInput.text,
                gameAliveRightBorderInput.text,
                gameBirthAmountInput.text
            )

            for (oneRowTextField in oneRowTextFields) {
                oneRowTextField {
                    try {
                        if (gameAliveLeftBorderInput.text.value.toInt() > gameAliveRightBorderInput.text.value.toInt()) {
                            gameAliveLeftBorderInput.errorMessage.value = "Left border can't be greater than right"
                            gameAliveRightBorderInput.errorMessage.value = "Right border can't be less than left"
                        } else {
                            gameAliveLeftBorderInput.errorMessage.value = ""
                            gameAliveRightBorderInput.errorMessage.value = ""
                        }
                    } catch (_: NumberFormatException) {
                    }

                    try {
                        oneRowTextField.text.value.toInt()
                    } catch (e: NumberFormatException) {
                        oneRowTextField.errorMessage.value = "Incorrect value"
                    }
                }
            }

            startGameButton {
                var hasError = false
                for (oneRowTextField in oneRowTextFields) {
                    if (oneRowTextField.errorMessage.value.isNotEmpty()) {
                        hasError = true
                    }
                    if (oneRowTextField.text.value.isEmpty()) {
                        oneRowTextField.errorMessage.value = "Fill this field"
                        hasError = true
                    }
                }

                if (hasError) return@startGameButton

                gameSettings = GameSettings(
                    oneRowTextFields[0].text.value.toInt(),
                    oneRowTextFields[1].text.value.toInt(),
                    oneRowTextFields[2].text.value.toInt(),
                    oneRowTextFields[3].text.value.toInt(),
                    oneRowTextFields[4].text.value.toInt(),
                    oneRowTextFields[5].text.value.toInt(),
                )

                val response = Controller.postSettings(gameSettings)
                if (response.status == Response.ERROR) {
                    val error = MyJson.decodeGameSettingsErrorMessage(response.message)
                    oneRowTextFields[error.first].errorMessage.value = error.second
                    hasError = true
                }

                if (hasError) return@startGameButton

                this@GameSettingsScreen.startGame()
            }
        }
    }

    private fun startGame() {
        val response = Controller.start()
        if (response.status == Response.OK) {
            onStartGame()
        }
    }
}
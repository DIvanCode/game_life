package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.GameSettings
import common.interaction.Request
import common.interaction.Response
import server.controllers.RequestController
import ui.components.ManageButton
import ui.components.OneRowTextField
import ui.components.Screen
import ui.displays.GameSettingsTextDisplay

class GameSettingsScreen(val startGame: () -> Unit): Screen() {
    @Composable
    override fun LazyItemScope.draw() {
        val gameSettingsResponse: Response = RequestController.handleRequest(Request(
            route = "/settings",
            method = Request.GET
        ))
        val gameSettings = gameSettingsResponse.responseBody["settings"] as GameSettings

        val gameFieldHeightInput = OneRowTextField(placeholder = "Высота поля")
        val gameFieldWidthInput = OneRowTextField(placeholder = "Ширина поля")
        val gameCellColorsInput = OneRowTextField(placeholder = "Количество цветов")
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
        val requestKeys: List<String> = listOf(
            "fieldHeight",
            "fieldWidth",
            "cellColors",
            "aliveLeftBorder",
            "aliveRightBorder",
            "birthAmount"
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
                            gameAliveLeftBorderInput.errorMessage.value = "Левая граница не может быть больше правой"
                            gameAliveRightBorderInput.errorMessage.value = "Правая граница не может быть меньше левой"
                        } else {
                            gameAliveLeftBorderInput.errorMessage.value = ""
                            gameAliveRightBorderInput.errorMessage.value = ""
                        }
                    } catch (_: NumberFormatException) {
                    }

                    try {
                        oneRowTextField.text.value.toInt()
                    } catch (e: NumberFormatException) {
                        oneRowTextField.errorMessage.value = "Некорректное значение"
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
                        oneRowTextField.errorMessage.value = "Заполните это поле"
                        hasError = true
                    }
                }

                if (hasError) return@startGameButton

                fun makeRequest(
                    oneRowTextField: OneRowTextField,
                    requestKey: String
                ): Boolean {
                    val response = RequestController.handleRequest(Request(
                        route = "/settings",
                        method = Request.POST,
                        mapOf(
                            requestKey to oneRowTextField.text.value
                        )
                    ))
                    if (response.status != Response.OK) {
                        oneRowTextField.errorMessage.value = response.message
                        return false
                    }

                    return true
                }

                for (i in oneRowTextFields.indices) {
                    val oneRowTextField = oneRowTextFields[i]
                    val requestKey = requestKeys[i]

                    if (!makeRequest(oneRowTextField, requestKey)) {
                        println(requestKey)
                        hasError = true
                    }
                }

                if (hasError) return@startGameButton

                println("START")

                startGame()
            }
        }
    }
}
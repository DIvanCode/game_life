package server.controllers

import common.GameSettings
import common.interaction.Response
import common.interaction.ResponseError
import common.interaction.ResponseOK

class GameSettingsController(
    val gameSettings: GameSettings = GameSettings()
) {
    fun setFieldHeight(height: Int): Response {
        if (height < 1) {
            return ResponseError("Высота поля должна быть как минимум 1")
        }
        if (height > GameSettings.MAX_FIELD_SIZE) {
            return ResponseError("Высота поля должна быть не более ${GameSettings.MAX_FIELD_SIZE}")
        }

        gameSettings.fieldHeight = height

        return ResponseOK()
    }

    fun setFieldWidth(width: Int): Response {
        if (width < 1) {
            return ResponseError("Ширина поля должна быть как минимум 1")
        }
        if (width > GameSettings.MAX_FIELD_SIZE) {
            return ResponseError("Ширина поля должна быть не более ${GameSettings.MAX_FIELD_SIZE}")
        }

        gameSettings.fieldWidth = width

        return ResponseOK()
    }

    fun setCellColors(colors: Int): Response {
        if (colors < 2) {
            return ResponseError("Количество цветов должно быть как минимум 2")
        }
        if (colors > GameSettings.MAX_COLORS) {
            return ResponseError("Количество цветов должно быть не более ${GameSettings.MAX_COLORS}")
        }

        gameSettings.cellColors = colors

        return ResponseOK()
    }

    fun setAliveLeftBorder(border: Int): Response {
        if (border < 1) {
            return ResponseError("Количество необходимых соседей должно быть не менее 1")
        }
        if (border > GameSettings.MAX_ALIVE_BORDER) {
            return ResponseError("Количество необходимых соседей должно быть не более ${GameSettings.MAX_ALIVE_BORDER}")
        }

        gameSettings.aliveLeftBorder = border

        return ResponseOK()
    }

    fun setAliveRightBorder(border: Int): Response {
        if (border < 1) {
            return ResponseError("Количество необходимых соседей должно быть не менее 1")
        }
        if (border > GameSettings.MAX_ALIVE_BORDER) {
            return ResponseError("Количество необходимых соседей должно быть не более ${GameSettings.MAX_ALIVE_BORDER}")
        }

        gameSettings.aliveRightBorder = border

        return ResponseOK()
    }

    fun setBirthAmount(amount: Int): Response {
        if (amount < 1) {
            return ResponseError("Количество необходимых соседей должно быть не менее 1")
        }
        if (amount > GameSettings.MAX_BIRTH_AMOUNT) {
            return ResponseError("Количество необходимых соседей должно быть не более ${GameSettings.MAX_BIRTH_AMOUNT}")
        }

        gameSettings.birthAmount = amount

        return ResponseOK()
    }

}
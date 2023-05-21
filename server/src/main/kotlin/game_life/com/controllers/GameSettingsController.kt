package game_life.com.controllers

import game_life.com.models.GameSettings

class GameSettingsController(
    val gameSettings: GameSettings = GameSettings()
) {
    fun setFieldHeight(height: Int): Pair<Int, String>? {
        if (height < 1) {
            return 0 to "Field height must be at least 1"
        }
        if (height > GameSettings.MAX_FIELD_SIZE) {
            return 0 to "Field height must be no more than ${GameSettings.MAX_FIELD_SIZE}"
        }

        gameSettings.fieldHeight = height

        return null
    }

    fun setFieldWidth(width: Int): Pair<Int, String>? {
        if (width < 1) {
            return 1 to "Field width must be at least 1"
        }
        if (width > GameSettings.MAX_FIELD_SIZE) {
            return 1 to "Field width must be no more than ${GameSettings.MAX_FIELD_SIZE}"
        }

        gameSettings.fieldWidth = width

        return null
    }

    fun setCellColors(colors: Int): Pair<Int, String>? {
        if (colors < 2) {
            return 2 to "Amount of colors must be at least 2"
        }
        if (colors > GameSettings.MAX_COLORS) {
            return 2 to "Amount of colors must be no more than ${GameSettings.MAX_COLORS}"
        }

        gameSettings.cellColors = colors

        return null
    }

    fun setAliveLeftBorder(border: Int): Pair<Int, String>? {
        if (border < 1) {
            return 3 to "Amount of necessary for life neighbours must be at least 1"
        }
        if (border > GameSettings.MAX_ALIVE_BORDER) {
            return 3 to "Amount of necessary for life neighbours must be no more than ${GameSettings.MAX_ALIVE_BORDER}"
        }

        gameSettings.aliveLeftBorder = border

        return null
    }

    fun setAliveRightBorder(border: Int): Pair<Int, String>? {
        if (border < 1) {
            return 4 to "Amount of necessary for life neighbours must be at least 1"
        }
        if (border > GameSettings.MAX_ALIVE_BORDER) {
            return 4 to "Amount of necessary for life neighbours must be no more than ${GameSettings.MAX_ALIVE_BORDER}"
        }

        gameSettings.aliveRightBorder = border

        return null
    }

    fun setBirthAmount(amount: Int): Pair<Int, String>? {
        if (amount < 1) {
            return 5 to "Amount of necessary for birth neighbours must be at least 1"
        }
        if (amount > GameSettings.MAX_BIRTH_AMOUNT) {
            return 5 to "Amount of necessary for birth neighbours must be no more than ${GameSettings.MAX_ALIVE_BORDER}"
        }

        gameSettings.birthAmount = amount

        return null
    }
}
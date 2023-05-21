package game_life.com.controllers

import game_life.com.models.Cell
import game_life.com.models.Game
import game_life.com.models.GameSettings
import game_life.com.models.JsonHandler

object Controller {
    private var gameSettingsController: GameSettingsController? = null
    private var gameController: GameController? = null

    fun createNewGame() {
        gameSettingsController = GameSettingsController()
    }

    fun getGameSettings(): GameSettings {
        return gameSettingsController!!.gameSettings
    }

    fun setGameSettings(gameSettings: GameSettings): Pair<Int, String>? {
        var res = gameSettingsController!!.setFieldHeight(gameSettings.fieldHeight)
        if (res != null) return res

        res = gameSettingsController!!.setFieldWidth(gameSettings.fieldWidth)
        if (res != null) return res

        res = gameSettingsController!!.setCellColors(gameSettings.cellColors)
        if (res != null) return res

        res = gameSettingsController!!.setAliveLeftBorder(gameSettings.aliveLeftBorder)
        if (res != null) return res

        res = gameSettingsController!!.setAliveRightBorder(gameSettings.aliveRightBorder)
        if (res != null) return res

        res = gameSettingsController!!.setBirthAmount(gameSettings.birthAmount)
        if (res != null) return res

        return null
    }

    fun startGame() {
        gameController = GameController(gameSettingsController!!.gameSettings)
    }

    fun getGame(): Game {
        return gameController!!.game
    }

    fun changeCell(cell: Cell) {
        gameController!!.changeCell(cell)
    }

    fun step(): MutableList<Cell> {
        return gameController!!.step()
    }

    fun clear(): MutableList<Cell> {
        return gameController!!.clear()
    }

    fun random(): MutableList<Cell> {
        return gameController!!.random()
    }

    fun gen() {
        gameController!!.gen()
    }

    fun close() {
        gameSettingsController = null
        gameController = null
    }

    fun save() {
        JsonHandler.save(gameController!!.game)
    }

    fun load(game: Game = JsonHandler.load()) {
        gameSettingsController = GameSettingsController(game.settings)
        gameController = GameController(game.settings, game)
    }
}
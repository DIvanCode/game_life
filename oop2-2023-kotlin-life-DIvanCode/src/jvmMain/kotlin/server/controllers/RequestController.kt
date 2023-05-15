package server.controllers

import common.Cell
import common.interaction.Request
import common.interaction.Response
import common.interaction.ResponseError
import common.interaction.ResponseOK
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object RequestController {
    var gameSettingsController: GameSettingsController? = null
    var gameController: GameController? = null

    fun handleRequest(request: Request): Response {
        if (request.route == "/") {
            if (request.method == Request.POST) {
                if (gameSettingsController != null) {
                    return ResponseError("GameSettings is not null")
                }

                gameSettingsController = GameSettingsController()
                gameController = null

                return ResponseOK()
            }
        }
        if (request.route == "/settings") {
            if (request.method == Request.GET) {
                if (gameSettingsController == null) {
                    return ResponseError("GameSettings is null")
                }

                return ResponseOK(Json.encodeToString(gameSettingsController!!.gameSettings))
            }
            if (request.method == Request.POST) {
                val requestBody = Json.decodeFromString<Map<String, Int>>(request.body)

                if (requestBody.containsKey("fieldHeight")) {
                    return gameSettingsController!!.setFieldHeight(requestBody["fieldHeight"]!!)
                }
                if (requestBody.containsKey("fieldWidth")) {
                    return gameSettingsController!!.setFieldWidth(requestBody["fieldWidth"]!!)
                }
                if (requestBody.containsKey("cellColors")) {
                    return gameSettingsController!!.setCellColors(requestBody["cellColors"]!!)
                }
                if (requestBody.containsKey("aliveLeftBorder")) {
                    return gameSettingsController!!.setAliveLeftBorder(requestBody["aliveLeftBorder"]!!)
                }
                if (requestBody.containsKey("aliveRightBorder")) {
                    return gameSettingsController!!.setAliveRightBorder(requestBody["aliveRightBorder"]!!)
                }
                if (requestBody.containsKey("birthAmount")) {
                    return gameSettingsController!!.setBirthAmount(requestBody["birthAmount"]!!)
                }

                return ResponseError("Unhandled request")
            }
        }
        if (request.route == "/game") {
            if (request.method == Request.GET) {
                if (gameSettingsController == null) {
                    return ResponseError("GameSettings is null")
                }

                if (gameController == null) {
                    return ResponseError("GameController is null")
                }

                return ResponseOK(Json.encodeToString((gameController!!.game)))
            }
            if (request.method == Request.POST) {
                if (gameSettingsController == null) {
                    return ResponseError("GameSettings is null")
                }

                if (gameController != null) {
                    return ResponseError("GameController is not null")
                }

                gameController = GameController(gameSettingsController!!.gameSettings)

                return ResponseOK()
            }
        }
        if (request.route == "/game/step") {
            if (request.method == Request.GET) {
                if (gameController == null) {
                    return ResponseError("Game is null")
                }

                val diff = gameController!!.makeStep()

                return ResponseOK(Json.encodeToString(diff))
            }
        }
        if (request.route == "/game/clear") {
            if (request.method == Request.GET) {
                if (gameController == null) {
                    return ResponseError("Game is null")
                }

                val diff = gameController!!.clear()

                return ResponseOK(Json.encodeToString(diff))
            }
        }
        if (request.route == "/game/random") {
            if (request.method == Request.GET) {
                if (gameController == null) {
                    return ResponseError("Game is null")
                }

                val diff = gameController!!.fillRandom()

                return ResponseOK(Json.encodeToString(diff))
            }
        }
        if (request.route == "/game/cell/change") {
            if (request.method == Request.POST) {
                if (gameController == null) {
                    return ResponseError("Game is null")
                }

                val cell = Json.decodeFromString<Cell>(request.body)

                gameController!!.game.field.setState(cell.row, cell.col, cell.state)

                return ResponseOK()
            }
        }

        return ResponseError("Unhandled request")
    }
}
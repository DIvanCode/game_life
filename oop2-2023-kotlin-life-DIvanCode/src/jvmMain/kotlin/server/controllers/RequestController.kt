package server.controllers

import common.CellState
import common.interaction.Request
import common.interaction.Response
import common.interaction.ResponseError
import common.interaction.ResponseOK

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

                return ResponseOK()
            }
        } else if (request.route == "/settings") {
            if (request.method == Request.GET) {
                if (gameSettingsController == null) {
                    return ResponseError("GameSettings is null")
                }

                return ResponseOK(mapOf(
                    "settings" to gameSettingsController!!.gameSettings
                ))
            } else {
                if (request.requestBody.containsKey("fieldHeight")) {
                    return gameSettingsController!!.setFieldHeight(request.requestBody["fieldHeight"].toString().toInt())
                }
                if (request.requestBody.containsKey("fieldWidth")) {
                    return gameSettingsController!!.setFieldWidth(request.requestBody["fieldWidth"].toString().toInt())
                }
                if (request.requestBody.containsKey("cellColors")) {
                    return gameSettingsController!!.setCellColors(request.requestBody["cellColors"].toString().toInt())
                }
                if (request.requestBody.containsKey("aliveLeftBorder")) {
                    return gameSettingsController!!.setAliveLeftBorder(request.requestBody["aliveLeftBorder"].toString().toInt())
                }
                if (request.requestBody.containsKey("aliveRightBorder")) {
                    return gameSettingsController!!.setAliveRightBorder(request.requestBody["aliveRightBorder"].toString().toInt())
                }
                if (request.requestBody.containsKey("birthAmount")) {
                    return gameSettingsController!!.setBirthAmount(request.requestBody["birthAmount"].toString().toInt())
                }

                return ResponseError("Unhandled request")
            }
        } else if (request.route == "/game") {
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
        } else if (request.route == "/game/field") {
            if (request.method == Request.GET) {
                if (gameController == null) {
                    return ResponseError("Game is null")
                }

                return ResponseOK(mapOf(
                    "field" to gameController!!.fieldController.field
                ))
            }
        } else if (request.route == "/game/cell/state") {
            if (request.method == Request.GET) {
                if (gameController == null) {
                    return ResponseError("Game is null")
                }

                if (!request.requestBody.containsKey("row") ||
                    !request.requestBody.containsKey("col")) {
                    return ResponseError("Unhandled request")
                }

                val row = request.requestBody["row"].toString().toInt()
                val col = request.requestBody["col"].toString().toInt()

                return ResponseOK(mapOf(
                    "field" to gameController!!.fieldController.getState(row, col)
                ))
            }
        } else if (request.route == "/game/cell/color") {
            if (request.method == Request.GET) {
                if (gameController == null) {
                    return ResponseError("Game is null")
                }

                if (!request.requestBody.containsKey("row") ||
                    !request.requestBody.containsKey("col") ||
                    !request.requestBody.containsKey("color")) {
                    return ResponseError("Unhandled request")
                }

                val row = request.requestBody["row"].toString().toInt()
                val col = request.requestBody["col"].toString().toInt()
                val color = request.requestBody["color"].toString().toInt()

                gameController!!.fieldController.setState(row, col, CellState(color = color))

                return ResponseOK()
            }
        }

        return ResponseError("Unhandled request")
    }
}
package client.controllers

import client.models.Cell
import client.models.Game
import client.models.GameSettings
import client.models.MyJson
import client.models.interaction.Response
import client.models.interaction.ResponseError
import client.models.interaction.ResponseOK
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

object Controller {
    private const val http = "http://0.0.0.0:8080"

    val client = HttpClient(CIO) {
        Charsets {
            register(Charsets.UTF_8)
        }
    }

    fun new(): Response {
        return emptyPostRequest("new")
    }

    fun getSettings(): Response = runBlocking {
        val response = async {
            client.get("$http/settings")
        }.await()

        println("get: $response")

        if (response.status.value in 200..299) {
            println(response.body<String>())
            return@runBlocking ResponseOK(mapOf(
                "settings" to MyJson.decodeGameSettings(response.body())
            ))
        }

        return@runBlocking ResponseError(response.status.description)
    }

    fun postSettings(gameSettings: GameSettings): Response = runBlocking {
        val response = async {
            client.post("$http/settings") {
                setBody(MyJson.encodeGameSettings(gameSettings))
            }
        }.await()

        println("post: $response")

        if (response.status.value in 200..299) {
            return@runBlocking ResponseOK()
        }

        return@runBlocking ResponseError(response.status.description)
    }

    fun start(): Response {
        return emptyPostRequest("start")
    }

    fun getGame(): Response = runBlocking {
        val response = async {
            client.get("$http/game")
        }.await()

        println("get: $response")

        if (response.status.value in 200..299) {
            println(response.body<String>())
            return@runBlocking ResponseOK(mapOf(
                "game" to MyJson.decodeGame(response.body())
            ))
        }

        return@runBlocking ResponseError(response.status.description)
    }

    fun postGame(game: Game): Response = runBlocking {
        println("postGame(${MyJson.encodeGame(game)}")
        val response = async {
            client.post("$http/game") {
                setBody(MyJson.encodeGame(game))
            }
        }.await()

        println("post: $response")

        if (response.status.value in 200..299) {
            return@runBlocking ResponseOK()
        }

        return@runBlocking ResponseError(response.status.description)
    }

    fun change(cell: Cell): Response = runBlocking {
        val response = async {
            client.post("$http/change") {
                setBody(MyJson.encodeCell(cell))
            }
        }.await()

        println("post: $response")

        if (response.status.value in 200..299) {
            return@runBlocking ResponseOK()
        }

        return@runBlocking ResponseError(response.status.description)
    }

    fun step(): Response {
        return action("step")
    }

    fun clear(): Response {
        return action("clear")
    }

    fun random(): Response {
        return action("random")
    }

    fun gen(): Response {
        return emptyPostRequest("gen")
    }

    fun close(): Response {
        return emptyPostRequest("close")
    }

    fun save(): Response {
        return emptyPostRequest("save")
    }

    fun load(): Response {
        return emptyPostRequest("load")
    }

    private fun emptyPostRequest(code: String): Response = runBlocking {
        val response = async {
            client.post("$http/$code")
        }.await()

        println("post: $response")

        if (response.status.value in 200..299) {
            return@runBlocking ResponseOK()
        }

        return@runBlocking ResponseError(response.status.description)
    }

    private fun action(code: String): Response = runBlocking {
        val response = async {
            client.get("$http/$code")
        }.await()

        println("get: $response")

        if (response.status.value in 200..299) {
            return@runBlocking ResponseOK(mapOf(
                "difference" to MyJson.decodeDifference(response.body())
            ))
        }

        return@runBlocking ResponseError(response.status.description)
    }
}
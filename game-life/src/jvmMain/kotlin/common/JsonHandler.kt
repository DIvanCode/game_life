package common

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object JsonHandler {
    fun readGame(path: String = DEFAULT_FILE_PATH): Game {
        val jsonString: String = File(path).readText(Charsets.UTF_8)
        return Json.decodeFromString(jsonString)
    }

    fun writeGame(game: Game, path: String = DEFAULT_FILE_PATH) {
        val jsonString: String = Json.encodeToString(game)
        if (!File(path).exists()) {
            File(path).createNewFile()
        }
        File(path).writeText(jsonString, Charsets.UTF_8)
    }

    private const val DEFAULT_FILE_PATH = "game.json"
}
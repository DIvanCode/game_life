package game_life.com.models

import java.io.File

object JsonHandler {
    fun load(path: String = DEFAULT_FILE_PATH): Game {
        val jsonString: String = File(path).readText(Charsets.UTF_8)
        return MyJson.decodeGame(jsonString)
    }

    fun save(game: Game, path: String = DEFAULT_FILE_PATH) {
        val jsonString: String = MyJson.encodeGame(game)
        if (!File(path).exists()) {
            File(path).createNewFile()
        }
        File(path).writeText(jsonString, Charsets.UTF_8)
    }

    private const val DEFAULT_FILE_PATH = "game.txt"
}
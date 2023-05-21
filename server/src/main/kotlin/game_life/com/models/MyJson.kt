package game_life.com.models

object MyJson {
    fun encodeGameSettings(gameSettings: GameSettings): String {
        return "${gameSettings.fieldHeight}," +
                "${gameSettings.fieldWidth}," +
                "${gameSettings.cellColors}," +
                "${gameSettings.aliveLeftBorder}," +
                "${gameSettings.aliveRightBorder}," +
                "${gameSettings.birthAmount}"
    }

    fun decodeGameSettings(s: String): GameSettings {
        val listSettings = s.split(",").map { it.toInt() }
        return GameSettings(
            listSettings[0],
            listSettings[1],
            listSettings[2],
            listSettings[3],
            listSettings[4],
            listSettings[5]
        )
    }

    fun encodeGameSettingsErrorMessage(error: Pair<Int, String>): String {
        return "${error.first}:${error.second}"
    }

    fun decodeGameSettingsErrorMessage(s: String): Pair<Int, String> {
        val message = s.split(":")
        return message[0].toInt() to message[1]
    }

    fun encodeCellState(cellState: CellState): String {
        return "${cellState.color},${cellState.isAlive},${cellState.aliveGenerations}"
    }

    fun decodeCellState(s: String): CellState {
        val t = s.split(",")
        return CellState(
            t[0].toInt(),
            t[1].toBoolean(),
            t[2].toInt()
        )
    }

    fun encodeField(field: Field): String {
        var s = ""
        var isFirst = true
        for (cellState in field.cells) {
            if (!isFirst) {
                s += ";"
            }
            isFirst = false
            s += encodeCellState(cellState)
        }
        return s
    }

    fun decodeField(s: String, height: Int, width: Int): Field {
        val t = s.split(";")
        val cells: MutableList<CellState> = mutableListOf()
        t.forEach {
            cells.add(decodeCellState(it))
        }
        return Field(height, width, cells)
    }

    fun encodeGame(game: Game): String {
        return encodeGameSettings(game.settings) + ":" + encodeField(game.field)
    }

    fun decodeGame(s: String): Game {
        val t = s.split(":")
        val settings = decodeGameSettings(t[0])
        val field = decodeField(t[1], settings.fieldHeight, settings.fieldWidth)
        return Game(settings, field)
    }

    fun decodeCell(s: String): Cell {
        val t = s.split(":")
        val u = t[0].split(",")
        return Cell(u[0].toInt(), u[1].toInt(), decodeCellState(t[1]))
    }

    fun encodeCell(cell: Cell): String {
        return "${cell.row},${cell.col}:${encodeCellState(cell.state)}"
    }

    fun encodeDifference(difference: MutableList<Cell>): String {
        var s = ""
        var isFirst = true
        difference.forEach {
            if (!isFirst) {
                s += ";"
            }
            isFirst = false
            s += encodeCell(it)
        }
        return s
    }

    fun decodeDifference(s: String): MutableList<Cell> {
        val difference: MutableList<Cell> = mutableListOf()
        s.split(";").forEach {
            difference.add(decodeCell(it))
        }
        return difference
    }
}
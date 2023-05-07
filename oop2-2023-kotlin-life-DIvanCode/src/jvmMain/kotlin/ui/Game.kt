package ui

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import server.Cell

class MyClass {
    val myInt = mutableStateOf(0)
    suspend fun run(){
        for (i in 0..10){
            myInt.value = i
            delay(500)
        }
    }
}

// Main.kt:
//@Composable
//fun App2() {
//    var myClass = remember { MyClass() }
//    val scope = rememberCoroutineScope()
//    MaterialTheme {
//        Column() {
//            Text(text="${myClass.myInt.value}")
//            Button(onClick = { scope.launch { myClass.run() }}) {
//                Text(text = "RUN")
//            }
//        }
//    }
//}

object Game {
    private var running: Boolean = false
    private var coroutineScope: CoroutineScope? = null

    fun init(height: Int, width: Int) {
        server.Game.init(height, width)
    }

    @Composable
    fun draw() {
        println("draw")

        var myClass = remember { MyClass() }

        coroutineScope = rememberCoroutineScope()

        val mutableCellsListState = mutableStateListOf<Cell>()
        for (cell in server.Game.field.cells) {
            mutableCellsListState.add(cell)
        }

        FieldDisplay(mutableCellsListState) { row: Int, col: Int ->
            server.Game.field.setCellState(row, col, ColorPicker.pickedState())
        }

        Row {
            ManageButton("makeStep") {
                println("try makeStep")
                if (!running) {
                    coroutineScope!!.launch {
                        server.Game.makeStep()

                        for (row in 0 until server.Game.field.height) {
                            for (col in 0 until server.Game.field.width) {
                                val index = server.Game.field.height * row + col

                                if (mutableCellsListState[index] != server.Game.field.getCell(row, col)) {
                                    mutableCellsListState[index] = server.Game.field.getCell(row, col)
                                }
                            }
                        }
                    }
                }
            }

            ManageButton("run") {
                println("try run")
                if (!running) {
                    println("RUN")
                    running = true

                    coroutineScope!!.launch {
                        while (running) {
                            delay(100L)

                            server.Game.makeStep()

                            var changed = false

                            for (row in 0 until server.Game.field.height) {
                                for (col in 0 until server.Game.field.width) {
                                    val index = server.Game.field.height * row + col

                                    if (mutableCellsListState[index] != server.Game.field.getCell(row, col)) {
                                        changed = true
                                        mutableCellsListState[index] = server.Game.field.getCell(row, col)
                                    }
                                }
                            }

                            if (!changed) {
                                running = false
                            }
                        }
                    }
                }
            }

            ManageButton("stop") {
                println("try stop")
                if (running) {
                    println("STOP")
                    running = false
                }
            }

            ManageButton("clear") {
                println("try clear")
                if (!running) {
                    println("CLEAR")

                    server.Game.clear()

                    for (row in 0 until server.Game.field.height) {
                        for (col in 0 until server.Game.field.width) {
                            val index = server.Game.field.height * row + col

                            if (mutableCellsListState[index] != server.Game.field.getCell(row, col)) {
                                mutableCellsListState[index] = server.Game.field.getCell(row, col)
                            }
                        }
                    }
                }
            }
        }
    }
}
package ui

//data class Blocker(var block: Boolean = false);
//
//object GameDisplay {
//    private var running: Boolean = false
//    private var coroutineScope: CoroutineScope? = null
//
//    fun init(height: Int, width: Int) {
////        common.Game.init(height, width)
//    }
//
//    @Composable
//    fun drawOverlay() {
//        Row(modifier = Modifier.fillMaxWidth().background(Color.White),
//            horizontalArrangement = Arrangement.Center) {
//            ManageButton("makeStep") {
//                if (!running) {
////                    Game.makeStep()
//                }
//            }
//
//            ManageButton("run") {
//                if (!running) {
//                    running = true
//                    coroutineScope!!.launch {
//                        while (running) {
//                            delay(100L)
//
//                            val changed = Game.makeStep()
//                            if (!changed) {
//                                running = false
//                                break
//                            }
//                        }
//                    }
//                }
//            }
//
//            ManageButton("stop") {
//                if (running) {
//                    running = false
//                }
//            }
//
//            ManageButton("clear") {
//                if (!running) {
////                    Game.clear()
//                }
//            }
//
//            ManageButton("random") {
//                if (!running) {
////                    Game.fillRandom()
//                }
//            }
//        }
//    }
//
//    @Composable
//    fun draw() {
//        println("draw")
//
//        coroutineScope = rememberCoroutineScope()
////        val field = remember { mutableStateOf(common.Game.field) }
//
////        FieldDisplay(field)
//    }
//}
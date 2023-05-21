package game_life.com.plugins

import game_life.com.controllers.Controller
import game_life.com.models.MyJson
import io.ktor.http.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.request.*
import io.ktor.server.resources.Resources
import io.ktor.server.application.*

fun Application.configureRouting() {
    install(AutoHeadResponse)
    install(DoubleReceive)
    install(Resources)

    routing {
        post("/") {
            call.response.status(HttpStatusCode.OK)
        }
        post("/new") {
            Controller.createNewGame()
            call.response.status(HttpStatusCode.OK)
        }
        get("/settings") {
            call.respondText(
                MyJson.encodeGameSettings(Controller.getGameSettings()),
                ContentType.Text.Plain.withCharset(Charsets.UTF_8),
                HttpStatusCode.OK
            )
        }
        post("/settings") {
            val error = Controller.setGameSettings(MyJson.decodeGameSettings(call.receiveText()))
            if (error == null) {
                call.response.status(HttpStatusCode.OK)
            } else {
                call.response.status(HttpStatusCode(400, MyJson.encodeGameSettingsErrorMessage(error)))
            }
        }
        post("/start") {
            Controller.startGame()
            call.response.status(HttpStatusCode.OK)
        }
        get("/game") {
            call.respondText(MyJson.encodeGame(Controller.getGame()))
        }
        post("/game") {
            val game = MyJson.decodeGame(call.receiveText())
            Controller.load(game)
            call.response.status(HttpStatusCode.OK)
        }
        post("/change") {
            val cell = MyJson.decodeCell(call.receiveText())
            Controller.changeCell(cell)
            call.response.status(HttpStatusCode.OK)
        }
        get("/step") {
            val difference = Controller.step()
            call.respondText(MyJson.encodeDifference(difference))
        }
        get("/clear") {
            val difference = Controller.clear()
            call.respondText(MyJson.encodeDifference(difference))
        }
        get("/random") {
            val difference = Controller.random()
            call.respondText(MyJson.encodeDifference(difference))
        }
        post("/gen") {
            Controller.gen()
            call.response.status(HttpStatusCode.OK)
        }
        post("/close") {
            Controller.close()
            call.response.status(HttpStatusCode.OK)
        }
        post("/save") {
            Controller.save()
            call.response.status(HttpStatusCode.OK)
        }
        post("/load") {
            Controller.load()
            call.response.status(HttpStatusCode.OK)
        }
    }
}

package common.interaction

class ResponseOK(override val responseBody: Body = Body()):
    Response(status = OK, responseBody = responseBody)

class ResponseError(override val message: String = ""):
    Response(status = ERROR, message = message)

abstract class Response(open val status: Int,
                        open val message: String = "",
                        open val responseBody: Body = Body()) {
    companion object {
        const val OK: Int = 200
        const val ERROR: Int = 500
    }
}
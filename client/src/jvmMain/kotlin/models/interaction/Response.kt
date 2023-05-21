package client.models.interaction

typealias Body = Map<String, Any>

fun Body(): Body {
    return mapOf()
}

class ResponseOK(
    override val body: Body = Body()
): Response(status = OK, body = body)

class ResponseError(
    override val message: String = ""
): Response(status = ERROR, message = message)

abstract class Response(
    open val status: Int,
    open val message: String = "",
    open val body: Body = Body()
) {
    companion object {
        const val OK: Int = 200
        const val ERROR: Int = 500
    }
}
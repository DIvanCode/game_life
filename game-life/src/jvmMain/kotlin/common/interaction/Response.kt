package common.interaction

class ResponseOK(
    override val body: String = String()
): Response(status = OK, body = body)

class ResponseError(
    override val message: String = ""
): Response(status = ERROR, message = message)

abstract class Response(
    open val status: Int,
    open val message: String = "",
    open val body: String = String()
) {
    companion object {
        const val OK: Int = 200
        const val ERROR: Int = 500
    }
}
package common.interaction

data class Request(
    val route: String,
    val method: String,
    val body: String = String()
) {
    companion object {
        const val POST = "POST"
        const val GET = "GET"
    }
}
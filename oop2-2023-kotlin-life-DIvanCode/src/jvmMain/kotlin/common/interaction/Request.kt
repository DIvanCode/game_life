package common.interaction

data class Request(val route: String,
                   val method: String,
                   val requestBody: Body = Body()) {
    companion object {
        const val POST = "POST"
        const val GET = "GET"
    }
}
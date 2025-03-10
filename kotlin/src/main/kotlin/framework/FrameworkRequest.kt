package framework

data class FrameworkRequest(val method: String, val path: String, val content: Map<String, String>, val headers: Map<String, String>)
{
    companion object {
        const val METHOD_GET = "GET"
        const val METHOD_POST = "POST"
        const val METHOD_PUT = "PUT"
        const val METHOD_PATCH = "PATCH"
        const val METHOD_DELETE = "DELETE"
    }

    fun pathStart(): String {
        return path.substringBefore("/")
    }

    fun getIdPath(): String {
        return path.substringAfter("/")
    }
}

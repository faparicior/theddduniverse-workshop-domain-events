package framework

object ThreadContext {
    private val threadLocal: ThreadLocal<MutableMap<String, Any>> = ThreadLocal.withInitial { mutableMapOf() }

    fun put(key: String, value: Any) {
        threadLocal.get()[key] = value
    }

    fun get(key: String): Any? {
        return threadLocal.get()[key]
    }

    fun remove(key: String) {
        threadLocal.get().remove(key)
    }

    fun clear() {
        threadLocal.get().clear()
    }
}

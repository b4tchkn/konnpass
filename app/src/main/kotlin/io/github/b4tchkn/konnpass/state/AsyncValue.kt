package io.github.b4tchkn.konnpass.state

data class AsyncValue<T>(
    val value: T? = null,
    val error: Any? = null,
    val loading: Boolean = false,
)
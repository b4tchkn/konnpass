package io.github.b4tchkn.konnpass.state

data class AsyncValue<T>(
    val data: T? = null,
    val error: Any? = null,
    val loading: LoadingStatus = LoadingStatus.Idling,
)

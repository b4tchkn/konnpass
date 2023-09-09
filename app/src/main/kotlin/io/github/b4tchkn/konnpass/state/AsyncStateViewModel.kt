package io.github.b4tchkn.konnpass.state

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class AsyncStateViewModel<T> : ViewModel() {
    @VisibleForTesting
    val _state = MutableStateFlow<AsyncValue<T>>(AsyncValue())
    val state: StateFlow<AsyncValue<T>>
        get() = _state

    abstract suspend fun fetch(): T

    suspend fun refresh() = runAsync {
        fetch()
    }

    suspend fun runAsync(block: suspend () -> T) {
        viewModelScope.launch {
            kotlin.runCatching {
                _state.value = _state.value.copy(loading = true)
                block()
            }.onSuccess {
                _state.value = _state.value.copy(value = it)
            }.onFailure {
                _state.value = _state.value.copy(error = it)
            }.also {
                _state.value = _state.value.copy(loading = false)
            }
        }
    }
}

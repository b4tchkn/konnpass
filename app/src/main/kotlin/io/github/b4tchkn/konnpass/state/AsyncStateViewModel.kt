package io.github.b4tchkn.konnpass.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class AsyncStateViewModel<S> : ViewModel() {
    private val _state = MutableStateFlow<AsyncValue<S>>(AsyncValue())

    val state: StateFlow<AsyncValue<S>>
        get() = _state

    init {
        viewModelScope.launch {
            refresh()
        }
    }

    abstract suspend fun fetch(): S

    private suspend fun refresh() = runAsync {
        fetch()
    }

    suspend fun runAsync(block: suspend () -> S) {
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

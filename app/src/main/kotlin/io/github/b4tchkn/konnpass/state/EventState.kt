package io.github.b4tchkn.konnpass.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.b4tchkn.konnpass.model.EventResponseModel
import io.github.b4tchkn.konnpass.usecase.GetEventsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventState : ViewModel() {
    private val _event = MutableStateFlow<EventResponseModel?>(null)
    val event: StateFlow<EventResponseModel?>
        get() = _event

    val useCase = GetEventsUseCase()

    suspend fun fetch() {
        viewModelScope.launch {
            val result = useCase()
            println(result)
            _event.value = result
        }
    }
}

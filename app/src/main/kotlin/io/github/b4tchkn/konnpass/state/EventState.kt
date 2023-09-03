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

    suspend fun fetch(
        eventId: Int? = null,
        keyword: String? = null,
        yearMonth: Int? = null,
        yearMonthDay: Int? = null,
        nickname: String? = null,
        ownerNickname: String? = null,
        seriesId: Int? = null,
        start: Int? = null,
        order: Int? = null,
        count: Int? = null,
    ) {
        viewModelScope.launch {
            _event.value = useCase(
                eventId,
                keyword,
                yearMonth,
                yearMonthDay,
                nickname,
                ownerNickname,
                seriesId,
                start,
                order,
                count,
            )
        }
    }
}

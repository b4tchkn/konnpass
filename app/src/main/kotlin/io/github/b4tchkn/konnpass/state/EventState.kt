package io.github.b4tchkn.konnpass.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.b4tchkn.konnpass.model.EventResponseModel
import io.github.b4tchkn.konnpass.usecase.GetEventsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventState : ViewModel() {

    private val _event =
        MutableStateFlow<AsyncValue<EventResponseModel>>(AsyncValue())
    val event: StateFlow<AsyncValue<EventResponseModel>>
        get() = _event

    val useCase = GetEventsUseCase()

    init {
        viewModelScope.launch {
            refresh(count = 50)
        }
    }

    suspend fun refresh(
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
            kotlin.runCatching {
                _event.value = _event.value.copy(loading = true)
                delay(1000L)
                useCase(
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
            }.onSuccess {
                _event.value = _event.value.copy(value = it)
            }.onFailure {
                _event.value = _event.value.copy(error = it)
            }.also {
                _event.value = _event.value.copy(loading = false)
            }
        }
    }
}

package io.github.b4tchkn.konnpass.state

import androidx.lifecycle.viewModelScope
import io.github.b4tchkn.konnpass.model.EventResponseModel
import io.github.b4tchkn.konnpass.usecase.GetEventsUseCase
import kotlinx.coroutines.launch

data class EventStatePram(
    val eventId: Int? = null,
    val keyword: String? = null,
    val yearMonth: Int? = null,
    val yearMonthDay: Int? = null,
    val nickName: String? = null,
    val ownerNickName: String? = null,
    val seriesId: Int? = null,
    val start: Int? = null,
    val order: Int? = null,
    val count: Int? = null,
)

class EventState : AsyncStateViewModel<EventResponseModel>() {

    init {
        viewModelScope.launch {
            refresh()
        }
    }

    override suspend fun fetch(): EventResponseModel {
        val useCase = GetEventsUseCase()
        val param = EventStatePram(
            count = LIMIT_LOAD_COUNT,
        )
        return useCase(
            eventId = param.eventId,
            keyword = param.keyword,
            yearMonth = param.yearMonth,
            yearMonthDay = param.yearMonthDay,
            nickname = param.nickName,
            ownerNickname = param.ownerNickName,
            seriesId = param.seriesId,
            start = param.start,
            order = param.order,
            count = param.count,
        )
    }

    companion object {
        private const val LIMIT_LOAD_COUNT = 10
    }
}

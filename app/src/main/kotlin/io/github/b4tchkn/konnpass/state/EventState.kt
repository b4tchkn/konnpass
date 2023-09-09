package io.github.b4tchkn.konnpass.state

import io.github.b4tchkn.konnpass.model.EventResponseModel
import io.github.b4tchkn.konnpass.usecase.GetEventsUseCase

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

    override suspend fun fetch(): EventResponseModel {
        val useCase = GetEventsUseCase()
        val param = EventStatePram(
            count = 10,
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
            count = 10,
        )
    }
}

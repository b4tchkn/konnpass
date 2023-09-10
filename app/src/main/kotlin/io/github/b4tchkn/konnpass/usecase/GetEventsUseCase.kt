package io.github.b4tchkn.konnpass.usecase

import io.github.b4tchkn.konnpass.data.ConnpassDataSource
import io.github.b4tchkn.konnpass.model.EventResponseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetEventsUseCase @Inject constructor(
    private val connpassDataSource: ConnpassDataSource,
) {

    suspend operator fun invoke(
        eventId: Int?,
        keyword: String?,
        yearMonth: Int?,
        yearMonthDay: Int?,
        nickname: String?,
        ownerNickname: String?,
        seriesId: Int?,
        start: Int?,
        order: Int?,
        count: Int?,
    ): EventResponseModel {
        return connpassDataSource.getEvents(
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

package io.github.b4tchkn.konnpass.usecase

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.b4tchkn.konnpass.data.ConnpassDataSource
import io.github.b4tchkn.konnpass.model.EventResponseModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class GetEventsUseCase {

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
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://connpass.com/api/v1/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
        val connpassDataSource = retrofit.create(ConnpassDataSource::class.java)
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

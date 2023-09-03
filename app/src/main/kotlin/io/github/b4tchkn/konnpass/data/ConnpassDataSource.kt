package io.github.b4tchkn.konnpass.data

import io.github.b4tchkn.konnpass.model.EventResponseModel
import retrofit2.http.GET

interface ConnpassDataSource {
    @GET("event")
    suspend fun getEvents(): EventResponseModel
}
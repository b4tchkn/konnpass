package io.github.b4tchkn.konnpass.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Suppress("PLUGIN_IS_NOT_ENABLED")
@OptIn(ExperimentalSerializationApi::class)
@Serializable
class EventModel(
    @JsonNames("event_id")
    val eventId: Int,

    @JsonNames("title")
    val title: String,

    @JsonNames("catch")
    val catch: String,

    @JsonNames("description")
    val description: String,

    @JsonNames("event_url")
    val eventUrl: String,

    @JsonNames("started_at")
    val startedAt: String,

    @JsonNames("ended_at")
    val endedAt: String,

    @JsonNames("limit")
    val limit: Int?,

    @JsonNames("hash_tag")
    val hashTag: String,

    @JsonNames("event_type")
    val eventType: String,

    @JsonNames("accepted")
    val accepted: Int,

    @JsonNames("waiting")
    val waiting: Int,

    @JsonNames("updated_at")
    val updatedAt: String,

    @JsonNames("owner_id")
    val ownerId: Int,

    @JsonNames("owner_nickname")
    val ownerNickname: String,

    @JsonNames("owner_display_name")
    val ownerDisplayNames: String,

    @JsonNames("place")
    val place: String?,

    @JsonNames("address")
    val address: String?,

    @JsonNames("lat")
    val latitude: String?,

    @JsonNames("lon")
    val longitude: String?,

    @JsonNames("series")
    val series: Series?,
) {
    @Serializable
    data class Series(
        @JsonNames("id")
        val id: Int,

        @JsonNames("title")
        val title: String,

        @JsonNames("url")
        val url: String,
    )
}

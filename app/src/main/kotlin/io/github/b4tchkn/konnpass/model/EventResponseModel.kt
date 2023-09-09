package io.github.b4tchkn.konnpass.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Suppress("PLUGIN_IS_NOT_ENABLED")
@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class EventResponseModel(
    @JsonNames("results_start")
    val resultsStart: Int,

    @JsonNames("results_returned")
    val resultsReturned: Int,

    @JsonNames("results_available")
    val resultsAvailable: Int,

    @JsonNames("events")
    val events: List<EventModel>,
)

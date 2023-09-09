package io.github.b4tchkn.konnpass.state.event

data class EventStateViewModelParam(
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

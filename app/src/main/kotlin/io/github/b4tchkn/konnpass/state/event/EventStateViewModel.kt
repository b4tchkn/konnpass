package io.github.b4tchkn.konnpass.state.event

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.b4tchkn.konnpass.model.EventResponseModel
import io.github.b4tchkn.konnpass.state.AsyncStateViewModel
import io.github.b4tchkn.konnpass.usecase.GetEventsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventStateViewModel @Inject constructor(
    private val useCase: GetEventsUseCase,
) : AsyncStateViewModel<EventResponseModel>() {

    init {
        viewModelScope.launch {
            refresh()
        }
    }

    override suspend fun fetch(): EventResponseModel {
        val param = EventStateViewModelParam(
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

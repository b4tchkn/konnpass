package io.github.b4tchkn.konnpass.state.event

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.b4tchkn.konnpass.model.EventResponseModel
import io.github.b4tchkn.konnpass.state.AsyncStateViewModel
import io.github.b4tchkn.konnpass.usecase.GetEventsUseCase
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = EventStateViewModelFactory::class)
class EventStateViewModel @AssistedInject constructor(
    private val useCase: GetEventsUseCase,
    @Assisted private val param: EventStateViewModelParam,
) : AsyncStateViewModel<EventResponseModel>() {

    init {
        viewModelScope.launch {
            refresh()
        }
    }

    override suspend fun fetch(): EventResponseModel {
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
            count = param.count ?: LIMIT_LOAD_COUNT,
        )
    }

    companion object {
        private const val LIMIT_LOAD_COUNT = 50
    }
}

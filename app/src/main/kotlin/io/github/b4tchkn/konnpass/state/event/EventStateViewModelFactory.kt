package io.github.b4tchkn.konnpass.state.event

import dagger.assisted.AssistedFactory

@AssistedFactory
interface EventStateViewModelFactory {
    fun create(param: EventStateViewModelParam): EventStateViewModel
}
package io.github.b4tchkn.konnpass.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.b4tchkn.konnpass.model.EventModel
import io.github.b4tchkn.konnpass.state.LoadingStatus
import io.github.b4tchkn.konnpass.state.event.EventStateViewModel
import io.github.b4tchkn.konnpass.state.event.EventStateViewModelFactory
import io.github.b4tchkn.konnpass.state.event.EventStateViewModelParam

@Composable
fun HomeScreen(
    eventStateViewModel: EventStateViewModel = hiltViewModel(
        creationCallback = { factory: EventStateViewModelFactory ->
            factory.create(EventStateViewModelParam())
        },
    ),
    onEventPressed: (event: EventModel) -> Unit,
) {
    val eventState by eventStateViewModel.state.collectAsState()

    Scaffold { padding ->
        ScreenCoordinator(
            modifier = Modifier.padding(padding),
            states = listOf(eventStateViewModel),
            pullToRefresh = {
                eventStateViewModel.refresh(loading = LoadingStatus.Refreshing)
            },
        ) {
            HomeScreen(
                events = eventState.data!!.events,
                onEventPressed = onEventPressed,
            )
        }
    }
}

@Composable
fun HomeScreen(
    events: List<EventModel>,
    onEventPressed: (event: EventModel) -> Unit,
) {
    LazyColumn {
        items(events.size) { index ->
            Column(
                modifier = Modifier.clickable {
                    onEventPressed(events[index])
                },
            ) {
                Text(text = events[index].title)
                HorizontalDivider()
            }
        }
    }
}

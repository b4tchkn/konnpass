package io.github.b4tchkn.konnpass.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.b4tchkn.konnpass.model.EventModel
import io.github.b4tchkn.konnpass.state.event.EventStateViewModel

@Composable
fun HomeScreen(
    eventStateViewModel: EventStateViewModel = hiltViewModel(),
) {
    val eventState by eventStateViewModel.state.collectAsState()

    Scaffold { padding ->
        ScreenCoordinator(
            modifier = Modifier.padding(padding),
            states = listOf(eventStateViewModel),
        ) {
            HomeScreen(events = eventState.data!!.events)
        }
    }
}

@Composable
fun HomeScreen(
    events: List<EventModel>,
) {
    LazyColumn {
        items(events.size) { index ->
            Text(text = events[index].title)
            Divider()
        }
    }
}

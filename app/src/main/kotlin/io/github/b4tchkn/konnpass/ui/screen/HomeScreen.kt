package io.github.b4tchkn.konnpass.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.b4tchkn.konnpass.state.EventState

@Composable
fun HomeScreen(
    eventState: EventState = hiltViewModel(),
) {
    val event by eventState.event.collectAsState()


    LaunchedEffect(key1 = Unit) {
        eventState.fetch()
    }

    Scaffold { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(event?.events?.size ?: 0) { index ->
                Text(text = event!!.events[index].title)
                Divider()
            }
        }
    }
}

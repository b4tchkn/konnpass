package io.github.b4tchkn.konnpass.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.b4tchkn.konnpass.model.EventModel
import io.github.b4tchkn.konnpass.state.event.EventStateViewModel
import io.github.b4tchkn.konnpass.state.event.EventStateViewModelFactory
import io.github.b4tchkn.konnpass.state.event.EventStateViewModelParam

const val eventDetailScreenParamId = "eventId"
const val eventDetailScreenRoute = "event_detail/{$eventDetailScreenParamId}"

fun NavController.navigateToEventDetailScreen(
    event: EventModel,
) {
    navigate(
        eventDetailScreenRoute.replace(
            "{$eventDetailScreenParamId}",
            "${event.eventId}",
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    eventId: Int?,
    eventViewModel: EventStateViewModel = hiltViewModel(
        creationCallback = { factory: EventStateViewModelFactory ->
            factory.create(
                EventStateViewModelParam(
                    eventId = eventId,
                ),
            )
        },
    ),
    onBackPressed: () -> Unit,
) {
    val eventState by eventViewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = eventState.data?.events?.first()?.title ?: "イベント",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "もどる",
                        )
                    }
                },
            )
        },
    ) {
        ScreenCoordinator(
            modifier = Modifier.padding(it),
            states = listOf(eventViewModel),
        ) {
            EventDetailScreen(event = eventState.data!!.events.first())
        }
    }
}

@Composable
fun EventDetailScreen(
    event: EventModel,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = event.title,
        )
        Text(text = event.hashTag)
        Text(text = event.ownerNickname)
        Text(text = event.ownerDisplayNames)
        Text(text = event.description)
        Text(text = event.address ?: "")
    }
}

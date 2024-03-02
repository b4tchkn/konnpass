package io.github.b4tchkn.konnpass.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.github.b4tchkn.konnpass.model.EventModel

const val eventDetailScreenParamId = "eventId"
const val eventDetailScreenRoute = "event_detail/{$eventDetailScreenParamId}"

fun NavController.navigateToEventDetailScreen(
    event: EventModel,
) {
    navigate(
        eventDetailScreenRoute.replace(
            "{$eventDetailScreenParamId}",
            event.eventId.toString(),
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    eventId: String?,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = eventId ?: "") },
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
        Text(
            modifier = Modifier.padding(it),
            text = eventId ?: "None",
        )
    }
}

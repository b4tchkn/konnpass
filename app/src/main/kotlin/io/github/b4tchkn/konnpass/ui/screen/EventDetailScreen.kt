package io.github.b4tchkn.konnpass.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EventDetailScreen(data: String?) {
    Scaffold {
        Text(
            modifier = Modifier.padding(it),
            text = data ?: "None",
        )
    }
}
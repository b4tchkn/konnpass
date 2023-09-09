package io.github.b4tchkn.konnpass.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.b4tchkn.konnpass.R
import io.github.b4tchkn.konnpass.state.AsyncStateViewModel
import io.github.b4tchkn.konnpass.state.AsyncValue
import io.github.b4tchkn.konnpass.ui.component.Gap
import kotlinx.coroutines.launch

@Composable
fun <V, T : AsyncStateViewModel<V>> ScreenCoordinator(
    modifier: Modifier = Modifier,
    states: List<T>,
    content: @Composable () -> Unit,
) {
    val collectedStates = states.map { it.state.collectAsState() }

    val loading = collectedStates.map { it.value.loading }.any { it }
    val hasAllValues = collectedStates.map { it.value.data }.all { it != null }
    val hasError = collectedStates.map { it.value.error }.any { it != null }

    val composableScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray),
    ) {
        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
        if (hasError) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.error_title),
                    textAlign = TextAlign.Center,
                )
                Gap(height = 16.dp)
                Button(
                    onClick = {
                        composableScope.launch {
                            states.forEach {
                                it.refresh()
                            }
                        }
                    },
                ) {
                    Text(text = "リトライする")
                }
            }
        }
        if (!loading && hasAllValues) content()
    }
}

@SuppressLint("VisibleForTests")
class FakeAsyncStateViewModel(asyncValue: AsyncValue<Unit>) : AsyncStateViewModel<Unit>() {
    init {
        _state.value = asyncValue
    }

    override suspend fun fetch() {
        TODO("Not yet implemented")
    }
}

@Preview
@Composable
fun PreviewSuccess() {
    ScreenCoordinator(
        states = listOf(FakeAsyncStateViewModel(AsyncValue(data = Unit))),
    ) {
        Text(text = "This is content")
    }
}

@Preview
@Composable
fun PreviewLoading() {
    ScreenCoordinator(
        states = listOf(FakeAsyncStateViewModel(AsyncValue(loading = true))),
    ) {
    }
}

@Preview
@Composable
fun PreviewHasError() {
    ScreenCoordinator(
        states = listOf(FakeAsyncStateViewModel(AsyncValue(error = ""))),
    ) {
    }
}

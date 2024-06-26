package me.ks.chan.pica.plus.ui.screen.greeting

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.composable.shimmer.shimmer
import me.ks.chan.pica.plus.ui.screen.greeting.composable.GreetingAsyncAvatar
import me.ks.chan.pica.plus.ui.screen.greeting.composable.GreetingErrorDialog
import me.ks.chan.pica.plus.ui.screen.greeting.viewmodel.GreetingState
import me.ks.chan.pica.plus.ui.theme.Avatar_48
import me.ks.chan.pica.plus.ui.theme.Spacing_8
import me.ks.chan.pica.plus.util.androidx.compose.HalfSize
import me.ks.chan.pica.plus.util.kotlin.Blank

@Composable
fun GreetingScreen(
    onSuccess: () -> Unit,
    onError: () -> Unit,
    viewModel: GreetingViewModel = greetingViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    GreetingContent(
        state = state,
        updateProfile = viewModel::updateProfile,
        onSuccess = onSuccess,
        onError = onError,
    )
}

private const val StateCrossFade = "State"
private const val NicknameCrossFade = "Nickname"
private const val SuccessDelay = 2500L

@Composable
private fun GreetingContent(
    state: GreetingState,
    updateProfile: () -> Unit,
    onSuccess: () -> Unit,
    onError: () -> Unit,
) {
    LaunchedEffect(key1 = state) {
        if (state is GreetingState.Success) {
            delay(SuccessDelay)
            onSuccess()
        }
    }

    if (state is GreetingState.Error) {
        GreetingErrorDialog(
            state = state,
            onCancel = onError,
            onRetry = updateProfile,
        )
    }

    Column {

        Spacer(modifier = Modifier.weight(1F))

        Column {
            Crossfade(
                targetState = state,
                label = StateCrossFade,
            ) { state ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(
                        id = when (state) {
                            is GreetingState.Success -> R.string.screen_greeting_text_state_success
                            else -> R.string.screen_greeting_text_state_loading
                        }
                    ),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                )
            }

            Row(
                modifier = Modifier.padding(top = Spacing_8),
            ) {
                Spacer(modifier = Modifier.weight(1F))

                when (state) {
                    is GreetingState.Success -> {
                        GreetingAsyncAvatar(avatar = state.avatar)
                    }
                    else -> {
                        Box(
                            modifier = Modifier
                                .size(Avatar_48)
                                .shimmer(
                                    loading = true,
                                    cornerRadius = Avatar_48 / 2
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1F))
            }

            Row(
                modifier = Modifier.padding(top = Spacing_8),
            ) {
                Spacer(modifier = Modifier.weight(1F))

                Crossfade(
                    modifier = Modifier
                        .fillMaxWidth(HalfSize),
                    targetState = state,
                    label = NicknameCrossFade,
                ) { state ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shimmer(loading = state is GreetingState.Loading),
                        style = MaterialTheme.typography.titleMedium,
                        text = when (state) {
                            is GreetingState.Success -> state.nickname
                            else -> Blank
                        },
                        textAlign = TextAlign.Center,
                    )
                }

                Spacer(modifier = Modifier.weight(1F))
            }
        }

        Spacer(modifier = Modifier.weight(1F))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GreetingPreview(
    onSuccess: () -> Unit = {},
    onError: () -> Unit = {},
) {
    val state by flow {
        while (true) {
            delay(3000)
            emit(
                GreetingState.Success(
                    nickname = "ks.chan",
                    avatar = "https://avatars.githubusercontent.com/1552980358",
                )
            )
        }
    }.collectAsStateWithLifecycle(initialValue = GreetingState.Loading)

    GreetingContent(
        state = state,
        updateProfile = {},
        onSuccess = onSuccess,
        onError = onError,
    )
}
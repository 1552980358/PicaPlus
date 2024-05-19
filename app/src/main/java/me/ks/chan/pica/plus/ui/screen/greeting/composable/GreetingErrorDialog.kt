package me.ks.chan.pica.plus.ui.screen.greeting.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.screen.greeting.viewmodel.GreetingState

@Composable
fun GreetingErrorDialog(
    state: GreetingState,
    onCancel: () -> Unit,
    onRetry: () -> Unit,
) {
    if (state is GreetingState.Error) {
        AlertDialog(
            onDismissRequest = onCancel,
            confirmButton = {
                ConfirmButtonRow(
                    errorState = state,
                    onCancel = onCancel,
                    onRetry = onRetry,
                )
            },
            title = {
                Text(
                    text = stringResource(
                        id = R.string.screen_greeting_dialog_error_title
                    )
                )
            },
            text = {
                Text(text = stringResource(id = state.messageResId))
            }
        )
    }
}

@Composable
private fun ConfirmButtonRow(
    errorState: GreetingState.Error,
    onCancel: () -> Unit,
    onRetry: () -> Unit,
) {
    Row {
        TextButton(onClick = onCancel) {
            Text(
                text = stringResource(
                    id = when (errorState) {
                        is GreetingState.Error.Unauthorized -> R.string.action_ok
                        else -> R.string.action_cancel
                    }
                )
            )
        }

        if (errorState !is GreetingState.Error.Unauthorized) {
            TextButton(onClick = onRetry) {
                Text(
                    text = stringResource(
                        id = R.string.action_retry
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        GreetingErrorDialog(
            state = GreetingState.Error.Connection,
            onCancel = {},
            onRetry = {},
        )
    }
}
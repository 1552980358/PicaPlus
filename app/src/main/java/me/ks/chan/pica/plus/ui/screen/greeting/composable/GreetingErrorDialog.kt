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
                    errorType = state.type,
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
                Text(text = stringResource(id = state.type.resId))
            }
        )
    }
}

@Composable
private fun ConfirmButtonRow(
    errorType: GreetingState.Error.Type,
    onCancel: () -> Unit,
    onRetry: () -> Unit,
) {
    Row {
        TextButton(onClick = onCancel) {
            Text(
                text = stringResource(
                    id = when (errorType) {
                        GreetingState.Error.Type.Unauthorized -> R.string.action_ok
                        else -> R.string.action_cancel
                    }
                )
            )
        }

        if (errorType != GreetingState.Error.Type.Unauthorized) {
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
            state = GreetingState.Error(GreetingState.Error.Type.Connection),
            onCancel = {},
            onRetry = {},
        )
    }
}
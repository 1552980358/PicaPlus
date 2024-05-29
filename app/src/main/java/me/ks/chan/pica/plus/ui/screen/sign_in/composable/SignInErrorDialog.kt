package me.ks.chan.pica.plus.ui.screen.sign_in.composable

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.util.androidx.compose.TrueState

@Composable
fun SignInErrorDialog(
    errorText: String
) {
    var isShow by remember(::TrueState)

    AnimatedVisibility(visible = isShow) {
        if (isShow) {
            AlertDialog(
                onDismissRequest = { isShow = false },
                confirmButton = {
                    TextButton(onClick = { isShow = false }) {
                        Text(text = stringResource(id = R.string.action_ok))
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.screen_sign_in_dialog_text_title))
                },
                text = {
                    Text(text = errorText)
                }
            )
        }
    }
}

@Composable
fun SignInErrorDialog(
    @StringRes errorTextResId: Int
) {
    SignInErrorDialog(stringResource(id = errorTextResId))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignInErrorDialogPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        SignInErrorDialog("Sign in error occur dialog preview")
    }
}
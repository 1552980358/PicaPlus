package me.ks.chan.pica.plus.ui.screen.sign_in.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.icon.round.Done
import me.ks.chan.pica.plus.ui.screen.sign_in.viewmodel.SignInFields
import me.ks.chan.pica.plus.ui.screen.sign_in.viewmodel.SignInState
import me.ks.chan.pica.plus.ui.theme.Icon_24
import me.ks.chan.pica.plus.util.androidx.compose.FillSpace
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob

private const val ButtonLoadingAnimation = "Loading"
private const val ButtonColorChangeAnimation = "ButtonColorChange"

@Composable
fun SignInButtons(
    state: SignInState,
    startSignIn: () -> Unit,
    inputFields: SignInFields,
    onForgotPassword: () -> Unit,
    onCreateAccount: () -> Unit,
) {
    Row {

        Spacer(modifier = Modifier.weight(FillSpace))

        TextButton(onClick = onForgotPassword) {
            Text(text = stringResource(id = R.string.screen_sign_in_button_forgot_text))
        }

    }

    Row {

        TextButton(onClick = onCreateAccount) {
            Text(text = stringResource(id = R.string.screen_sign_in_button_create_text))
        }

        Spacer(modifier = Modifier.weight(FillSpace))

        val signInButtonContainerColors = animateColorAsState(
            targetValue = ButtonDefaults.buttonColors().run {
                when {
                    inputFields.username.isBlank() || inputFields.password.isBlank() || state.isLoading -> {
                        disabledContainerColor
                    }
                    else -> { containerColor }
                }
            },
            label = ButtonColorChangeAnimation
        )

        Button(
            enabled = (
                inputFields.username.isNotBlank() &&
                    inputFields.password.isNotBlank() &&
                    !state.isLoading &&
                    !state.isSuccess
                ),
            colors = ButtonDefaults.buttonColors()
                .copy(
                    containerColor = signInButtonContainerColors.value,
                    disabledContainerColor = signInButtonContainerColors.value
                ),
            onClick = startSignIn,
        ) {
            AnimatedContent(
                targetState = state,
                label = ButtonLoadingAnimation
            ) { state ->
                when (state) {
                    SignInState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.size(Icon_24))
                    }
                    is SignInState.Success -> {
                        Icon(
                            imageVector = Done,
                            contentDescription = null,
                            tint = ButtonDefaults.buttonColors().contentColor
                        )
                    }
                    else -> {
                        Text(text = stringResource(id = R.string.screen_sign_in_button_sign_in_text))
                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun Preview() {
    var state by remember { mutableStateOf<SignInState>(SignInState.Pending) }
    var fields by remember { mutableStateOf(SignInFields()) }

    val coroutineScope = rememberCoroutineScope()

    Column {
        SignInButtons(
            state = state,
            startSignIn = {
                coroutineScope.defaultJob {
                    fields = SignInFields("username", "password")
                    delay(1000)
                    state = SignInState.Loading
                    delay(2000)
                    state = SignInState.Success("")
                }
            },
            inputFields = fields,
            onForgotPassword = {},
            onCreateAccount = {},
        )
    }
}
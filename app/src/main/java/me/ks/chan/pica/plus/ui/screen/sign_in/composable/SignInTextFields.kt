package me.ks.chan.pica.plus.ui.screen.sign_in.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.icon.round.Visibility
import me.ks.chan.pica.plus.ui.icon.round.VisibilityOff
import me.ks.chan.pica.plus.ui.screen.sign_in.model.SignInInputFields
import me.ks.chan.pica.plus.ui.screen.sign_in.model.SignInState
import me.ks.chan.pica.plus.ui.screen.sign_in.model.SignInUiState
import me.ks.chan.pica.plus.ui.theme.Spacing_8
import me.ks.chan.pica.plus.util.compose.FalseState
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob

@Composable
fun SignInFields(
    uiState: SignInUiState,
    startSignIn: () -> Unit,
    inputFields: SignInInputFields,
    updateUsernameField: (String) -> Unit,
    updatePasswordField: (String) -> Unit,
) {
    // Let focus stay inside `Column` composition scope
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Spacing_8),
        value = inputFields.username,
        onValueChange = updateUsernameField,
        enabled = uiState.editable,
        isError = uiState.signInState == SignInState.InvalidCredentialError,
        label = {
            Text(text = stringResource(id = R.string.screen_sign_in_field_username_label))
        },
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
    )

    // Let visible recomposition occur affection
    // inside `Column` composition scope
    var passwordVisible by remember(::FalseState)

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Spacing_8),
        value = inputFields.password,
        onValueChange = updatePasswordField,
        enabled = uiState.editable,
        isError = uiState.signInState == SignInState.InvalidCredentialError,
        label = {
            Text(text = stringResource(id = R.string.screen_sign_in_field_password_label))
        },
        keyboardActions = KeyboardActions(
            onDone = { startSignIn() }
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        singleLine = true,
        supportingText = {
            Text(
                text = stringResource(
                    id = when (uiState.signInState) {
                        SignInState.InvalidCredentialError -> {
                            R.string.screen_sign_in_field_password_error
                        }
                        else -> {
                            R.string.screen_sign_in_field_password_supporting
                        }
                    }
                )
            )
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = when {
                        passwordVisible -> VisibilityOff
                        else -> Visibility
                    },
                    contentDescription = null
                )
            }
        },
        visualTransformation = when {
            passwordVisible -> VisualTransformation.None
            else -> PasswordVisualTransformation()
        }
    )
}

@Preview
@Composable
private fun Preview() {
    var uiState by remember { mutableStateOf(SignInUiState()) }
    var inputFields by remember { mutableStateOf(SignInInputFields()) }

    val coroutineScope = rememberCoroutineScope()
    Column {
        SignInFields(
            uiState = uiState,
            startSignIn = {
                coroutineScope.defaultJob {
                    if (inputFields.username.isNotBlank() && inputFields.password.isNotBlank()) {
                        uiState = SignInUiState(signInState = SignInState.Loading)
                        delay(2000)
                        uiState = SignInUiState(signInState = SignInState.InvalidCredentialError)
                        delay(2000)
                        uiState = SignInUiState(signInState = SignInState.Pending)
                    }
                }
            },
            inputFields = inputFields,
            updateUsernameField = { username ->
                inputFields = inputFields.copy(username = username)
            },
            updatePasswordField = { password ->
                inputFields = inputFields.copy(password = password)
            }
        )
    }
}
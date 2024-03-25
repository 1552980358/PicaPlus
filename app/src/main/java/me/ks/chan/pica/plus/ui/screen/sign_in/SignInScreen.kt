package me.ks.chan.pica.plus.ui.screen.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.storage.protobuf.AccountStore
import me.ks.chan.pica.plus.storage.protobuf.AddressProto.Account
import me.ks.chan.pica.plus.ui.screen.sign_in.composable.SignInButtons
import me.ks.chan.pica.plus.ui.screen.sign_in.composable.SignInErrorDialog
import me.ks.chan.pica.plus.ui.screen.sign_in.composable.SignInFields
import me.ks.chan.pica.plus.ui.screen.sign_in.model.SignInInputFields
import me.ks.chan.pica.plus.ui.screen.sign_in.model.SignInState
import me.ks.chan.pica.plus.ui.screen.sign_in.model.SignInUiState
import me.ks.chan.pica.plus.ui.theme.Spacing_16
import me.ks.chan.pica.plus.ui.theme.Spacing_8
import me.ks.chan.pica.plus.util.compose.QuarterSize
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob

@Composable
fun SignInScreen(
    onSignInSuccess: () -> Unit,
    onForgotPassword: () -> Unit,
    onCreateAccount: () -> Unit,
) {
    val viewModel = signInViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val fields by viewModel.inputFields.collectAsStateWithLifecycle()

    SignInContent(
        uiState,
        viewModel::startSignIn,
        fields,
        viewModel::updateUsernameField,
        viewModel::updatePasswordField,
        onSignInSuccess,
        onForgotPassword,
        onCreateAccount,
    )
}

@Composable
private fun SignInContent(
    uiState: SignInUiState,
    startSignIn: () -> Unit,
    inputFields: SignInInputFields,
    updateUsernameField: (String) -> Unit,
    updatePasswordField: (String) -> Unit,
    onSignInSuccess: () -> Unit,
    onForgotPassword: () -> Unit,
    onCreateAccount: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = uiState.signInState) {
        if (uiState.signInState is SignInState.Success) {
            // Store data
            context.AccountStore
                .updateData {
                    Account.newBuilder()
                        .setUsername(inputFields.username)
                        .setPassword(inputFields.password)
                        .setToken(uiState.signInState.token)
                        .build()
                }
            // Save token to repository holder
            PicaRepository.authorization(token = uiState.signInState.token)

            // Complete
            onSignInSuccess()
        }
    }

    if (uiState.signInState is SignInState.DialogError) {
        SignInErrorDialog(errorTextResId = uiState.signInState.stringResId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .statusBarsPadding(),
    ) {

        Column(modifier = Modifier.fillMaxHeight(QuarterSize)) {
            /*TODO*/
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = Spacing_16)
            ) {

                Text(
                    modifier = Modifier.padding(top = Spacing_8),
                    text = stringResource(id = R.string.screen_sign_in_text_title),
                    style = MaterialTheme.typography.headlineMedium
                )

                SignInFields(
                    uiState = uiState,
                    startSignIn = startSignIn,
                    inputFields = inputFields,
                    updateUsernameField = updateUsernameField,
                    updatePasswordField = updatePasswordField,
                )

                SignInButtons(
                    uiState = uiState,
                    startSignIn = startSignIn,
                    inputFields = inputFields,
                    onForgotPassword = onForgotPassword,
                    onCreateAccount = onCreateAccount,
                )

            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignInPreview(
    onSignInSuccess: () -> Unit = {},
    onForgotPassword: () -> Unit = {},
    onCreateAccount: () -> Unit = {},
) {
    var uiState by remember { mutableStateOf(SignInUiState()) }
    var inputFields by remember { mutableStateOf(SignInInputFields()) }

    val coroutineScope = rememberCoroutineScope()

    SignInContent(
        uiState = uiState,
        inputFields = inputFields,
        startSignIn = {
            if (inputFields.username.isNotBlank() && inputFields.password.isNotBlank()) {
                uiState = uiState.copy(signInState = SignInState.Loading)
                coroutineScope.defaultJob {
                    delay(2000)
                    uiState = uiState.copy(
                        signInState = SignInState.InvalidCredentialError,
                    )
                    delay(2000)
                    uiState = uiState.copy(
                        signInState = SignInState.UnexpectedExceptionError,
                    )
                    delay(2000)
                    uiState = uiState.copy(
                        signInState = SignInState.Success(""),
                    )
                }
            }
        },
        updateUsernameField = { username ->
            inputFields = inputFields.copy(username = username)
        },
        updatePasswordField = { password ->
            inputFields = inputFields.copy(password = password)
        },
        onSignInSuccess = onSignInSuccess,
        onForgotPassword = onForgotPassword,
        onCreateAccount = onCreateAccount,
    )
}
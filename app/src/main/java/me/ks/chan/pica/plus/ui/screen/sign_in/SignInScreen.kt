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
import me.ks.chan.pica.plus.ui.screen.sign_in.composable.SignInTextFields
import me.ks.chan.pica.plus.ui.screen.sign_in.viewmodel.SignInFields
import me.ks.chan.pica.plus.ui.screen.sign_in.viewmodel.SignInState
import me.ks.chan.pica.plus.ui.theme.Spacing_16
import me.ks.chan.pica.plus.ui.theme.Spacing_8
import me.ks.chan.pica.plus.util.androidx.compose.QuarterSize
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob

@Composable
fun SignInScreen(
    onSignInSuccess: () -> Unit,
    onForgotPassword: () -> Unit,
    onCreateAccount: () -> Unit,
) {
    val viewModel = signInViewModel
    val state by viewModel.state.collectAsStateWithLifecycle()
    val fields by viewModel.fields.collectAsStateWithLifecycle()

    SignInContent(
        state = state,
        startSignIn = viewModel::signInAccount,
        fields = fields,
        updateFields = viewModel::updateFields,
        onSignInSuccess = onSignInSuccess,
        onForgotPassword = onForgotPassword,
        onCreateAccount = onCreateAccount,
    )
}

@Composable
private fun SignInContent(
    state: SignInState,
    startSignIn: () -> Unit,
    fields: SignInFields,
    updateFields: (SignInFields) -> Unit,
    onSignInSuccess: () -> Unit,
    onForgotPassword: () -> Unit,
    onCreateAccount: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state) {
        if (state is SignInState.Success) {
            // Store data
            context.AccountStore
                .updateData {
                    Account.newBuilder()
                        .setUsername(fields.username)
                        .setPassword(fields.password)
                        .setToken(state.token)
                        .build()
                }
            // Save token to repository holder
            PicaRepository.authorization(token = state.token)

            // Complete
            onSignInSuccess()
        }
    }

    if (state is SignInState.Error.Dialog) {
        SignInErrorDialog(errorTextResId = state.stringResId)
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

                SignInTextFields(
                    state = state,
                    startSignIn = startSignIn,
                    inputFields = fields,
                    updateUsernameField = { username ->
                        updateFields(fields.copy(username = username))
                    },
                    updatePasswordField = { password ->
                        updateFields(fields.copy(password = password))
                    },
                )

                SignInButtons(
                    state = state,
                    startSignIn = startSignIn,
                    inputFields = fields,
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
    var state by remember { mutableStateOf<SignInState>(SignInState.Pending) }
    var inputFields by remember { mutableStateOf(SignInFields()) }

    val coroutineScope = rememberCoroutineScope()

    SignInContent(
        state = state,
        fields = inputFields,
        startSignIn = {
            if (inputFields.username.isNotBlank() && inputFields.password.isNotBlank()) {
                state = SignInState.Loading
                coroutineScope.defaultJob {
                    delay(2000)
                    state = SignInState.Error.InvalidCredential
                    delay(2000)
                    state = SignInState.Error.UnknownException
                    delay(2000)
                    state = SignInState.Success("")
                }
            }
        },
        updateFields = { fields ->
            inputFields = fields
        },
        onSignInSuccess = onSignInSuccess,
        onForgotPassword = onForgotPassword,
        onCreateAccount = onCreateAccount,
    )
}
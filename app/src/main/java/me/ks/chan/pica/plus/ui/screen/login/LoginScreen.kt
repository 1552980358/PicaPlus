package me.ks.chan.pica.plus.ui.screen.login

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.ks.chan.material.symbols.MaterialSymbols
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.resources.dimen.Sizing_12
import me.ks.chan.pica.plus.resources.dimen.Sizing_20
import me.ks.chan.pica.plus.resources.dimen.Spacing_16
import me.ks.chan.pica.plus.resources.dimen.Spacing_8
import me.ks.chan.pica.plus.resources.dimen.Width_2
import me.ks.chan.pica.plus.resources.icon.AccountCircle
import me.ks.chan.pica.plus.resources.icon.Check
import me.ks.chan.pica.plus.resources.icon.Close
import me.ks.chan.pica.plus.resources.icon.Key
import me.ks.chan.pica.plus.resources.icon.Visibility
import me.ks.chan.pica.plus.resources.icon.VisibilityOff
import me.ks.chan.pica.plus.resources.icon.WavingHand

@Composable
fun LoginScreen(
    loginSuccess: () -> Unit,
) {
    val loginViewModel = LoginViewModel.Scoped

    val accountState = loginViewModel.account.collectAsStateWithLifecycle()
    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = loginState) {
        if (loginState is LoginViewModel.LoginState.Success) {
            loginSuccess()
        }
    }

    Layout(
        accountState = accountState,
        updateAccount = loginViewModel::updateAccount,
        loginState = loginState,
        startLogin = loginViewModel::login,
    )
}

@Composable
private fun Layout(
    accountState: State<LoginViewModel.Account>,
    updateAccount: (LoginViewModel.Account) -> Unit,
    loginState: LoginViewModel.LoginState,
    startLogin: () -> Unit,
) {
    Scaffold(
        topBar = {
            var exitConfirm by remember { mutableStateOf(false) }
            ExitConfirmDialog(
                exitConfirm = exitConfirm,
                onDismissRequest = { exitConfirm = false },
            )

            @OptIn(ExperimentalMaterial3Api::class)
            LargeTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { exitConfirm = true }) {
                        MaterialSymbols.Close.RoundedIcon()
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.LoginScreen_Appbar_Title))
                },
            )
        }
    ) { innerPaddings ->
        Card(
            modifier = Modifier
                .padding(top = innerPaddings.calculateTopPadding()),
            shape = RoundedCornerShape(topStart = Sizing_12, topEnd = Sizing_12)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = Spacing_16, vertical = Spacing_8),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MaterialSymbols.WavingHand.RoundedIcon(
                        modifier = Modifier.padding(all = Spacing_8)
                    )

                    Text(
                        text = stringResource(id = R.string.LoginScreen_GreetingText),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                AccountTextFields(
                    accountState = accountState,
                    updateAccount = updateAccount,
                    loginState = loginState,
                )

                AccountButtonRow(
                    accountState = accountState,
                    loginState = loginState,
                    startLogin = startLogin,
                )
            }

            Spacer(modifier = Modifier.weight(1F))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = remember {
                            innerPaddings.calculateBottomPadding() + Spacing_16
                        }
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = stringResource(id = R.string.LoginScreen_UnofficialText))
                Text(
                    modifier = Modifier.padding(top = Spacing_8),
                    text = stringResource(id = R.string.LoginScreen_LicenseText)
                )
            }
        }
    }
}

@Composable
private fun ExitConfirmDialog(
    exitConfirm: Boolean,
    onDismissRequest: () -> Unit,
) {
    if (exitConfirm) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                val context = LocalContext.current
                TextButton(
                    onClick = {
                        (context as? Activity)?.finishAffinity()
                    }
                ) {
                    Text(text = stringResource(id = R.string.LoginScreen_ExitConfirmDialog_ConfirmButton))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = stringResource(id = R.string.LoginScreen_ExitConfirmDialog_CancelButton))
                }
            },
            title = {
                Text(text = stringResource(id = R.string.LoginScreen_ExitConfirmDialog_Title))
            },
            text = {
                Text(text = stringResource(id = R.string.LoginScreen_ExitConfirmDialog_Content))
            },
        )
    }
}

@Composable
private fun AccountTextFields(
    accountState: State<LoginViewModel.Account>,
    updateAccount: (LoginViewModel.Account) -> Unit,
    loginState: LoginViewModel.LoginState,
) {
    val account by accountState

    val isLoading = (loginState is LoginViewModel.LoginState.Loading).not()
    val isError = (loginState is LoginViewModel.LoginState.Failure)

    val username by remember { derivedStateOf { account.username } }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = username,
        onValueChange = {
            updateAccount(account.copy(username = it))
        },
        label = {
            Text(text = stringResource(id = R.string.LoginScreen_UsernameTextField_Label))
        },
        leadingIcon = {
            MaterialSymbols.AccountCircle.RoundedIcon()
        },
        enabled = isLoading,
        isError = isError,
    )

    val password by remember { derivedStateOf { account.password } }

    var showPasswordInput by remember { mutableStateOf(false) }
    val passwordVisualTransformation = remember(::PasswordVisualTransformation)
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Spacing_8),
        value = password,
        onValueChange = {
            updateAccount(account.copy(password = it))
        },
        label = {
            Text(text = stringResource(id = R.string.LoginScreen_PasswordTextField_Label))
        },
        leadingIcon = {
            MaterialSymbols.Key.RoundedIcon()
        },
        trailingIcon = {
            val showVisibilityButton by remember {
                derivedStateOf { account.password.isNotEmpty() }
            }
            AnimatedVisibility(
                visible = showVisibilityButton,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                IconButton(onClick = { showPasswordInput = !showPasswordInput }) {
                    Icon(
                        imageVector = when {
                            showPasswordInput -> { MaterialSymbols.VisibilityOff.rounded }
                            else -> { MaterialSymbols.Visibility.rounded }
                        },
                        contentDescription = "",
                    )
                }
            }
        },
        visualTransformation = when {
            showPasswordInput -> { VisualTransformation.None }
            else -> { passwordVisualTransformation }
        },
        supportingText = {
            if (isError) {
                Text(
                    text = (loginState as LoginViewModel.LoginState.Failure).snackbarMessage
                )
            }
        },
        enabled = isLoading,
        isError = isError,
    )
}

@Composable
fun AccountButtonRow(
    accountState: State<LoginViewModel.Account>,
    loginState: LoginViewModel.LoginState,
    startLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = stringResource(id = R.string.LoginScreen_ForgotButton_Text))
        }

        Spacer(modifier = Modifier.weight(1F))

        val allowLogin by remember {
            val account by accountState
            derivedStateOf { account.username.isNotBlank() && account.password.isNotBlank() }
        }

        Button(
            onClick = startLogin,
            enabled = allowLogin,
            colors = when (loginState) {
                is LoginViewModel.LoginState.Loading -> {
                    ButtonDefaults.elevatedButtonColors()
                }
                else -> {
                    ButtonDefaults.buttonColors()
                }
            }
        ) {
            when (loginState) {
                is LoginViewModel.LoginState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(size = Sizing_20),
                        strokeWidth = Width_2
                    )
                }
                is LoginViewModel.LoginState.Success -> {
                    MaterialSymbols.Check.RoundedIcon()
                }
                else -> {
                    Text(text = stringResource(id = R.string.LoginScreen_GoButton_Text))
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewLoading() {
    val accountState = remember { mutableStateOf(LoginViewModel.Account()) }
    var account by accountState

    var loginState by remember {
        mutableStateOf<LoginViewModel.LoginState>(
            LoginViewModel.LoginState.Pending
        )
    }

    Layout(
        accountState = accountState,
        updateAccount = { account = it },
        loginState = loginState,
        startLogin = {
            loginState = LoginViewModel.LoginState.Loading
        }
    )
}

@Composable
@Preview
private fun PreviewSuccess() {
    val accountState = remember { mutableStateOf(LoginViewModel.Account()) }
    var account by accountState

    var loginState by remember {
        mutableStateOf<LoginViewModel.LoginState>(
            LoginViewModel.LoginState.Pending
        )
    }

    Layout(
        accountState = accountState,
        updateAccount = { account = it },
        loginState = loginState,
        startLogin = {
            loginState = LoginViewModel.LoginState.Success("")
        }
    )
}
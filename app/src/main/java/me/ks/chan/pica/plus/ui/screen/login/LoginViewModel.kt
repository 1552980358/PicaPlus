package me.ks.chan.pica.plus.ui.screen.login

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.auth.login.LoginRepository
import me.ks.chan.pica.plus.storage.datastore.accountDatastore
import me.ks.chan.pica.plus.storage.proto.accountProto

class LoginViewModel(context: Context): ViewModel() {

    companion object Factory {
        inline val Scoped: LoginViewModel
            @Composable get() {
                val context = LocalContext.current
                return viewModel(
                    factory = viewModelFactory {
                        initializer { LoginViewModel(context) }
                    }
                )
            }
    }

    private val accountDatastore = context.accountDatastore

    data class Account(
        val username: String = "",
        val password: String = "",
    )

    val account: StateFlow<Account>
        field = MutableStateFlow(Account())

    fun updateAccount(new: Account) {
        account.value = new
    }

    sealed interface LoginState {

        data object Pending: LoginState
        data object Loading: LoginState
        sealed interface End: LoginState

        data class Success(val token: String): End

        sealed interface Failure: End {
            val snackbarMessage: String
                @Composable get

            data object InvalidCredentials: Failure {
                override val snackbarMessage: String
                    @Composable get() = stringResource(
                        id = R.string.LoginScreen_PasswordTextField_LoginFailureSupportingText_InvalidCredentials
                    )
            }

            data object Network: Failure {
                override val snackbarMessage: String
                    @Composable get() = stringResource(
                        id = R.string.LoginScreen_PasswordTextField_LoginFailureSupportingText_Network
                    )
            }

            data class UnexpectedErrorCode(
                private val code: Int,
                private val error: String?,
                private val message: String?
            ): Failure {
                override val snackbarMessage: String
                    @Composable get() = stringResource(
                        id = R.string.LoginScreen_PasswordTextField_LoginFailureSupportingText_UnexpectedErrorCode,
                        code, error.toString(), message.toString()
                    )
            }
            data class UnknownResponse(private val code: Int, private val response: String?): Failure {
                override val snackbarMessage: String
                    @Composable get() = stringResource(
                        id = R.string.LoginScreen_PasswordTextField_LoginFailureSupportingText_UnknownResponse,
                        code, response.toString()
                    )
            }
            data class UnexpectedException(private val throwable: Throwable): Failure {
                override val snackbarMessage: String
                    @Composable get() = stringResource(
                        id = R.string.LoginScreen_PasswordTextField_LoginFailureSupportingText_UnexpectedException,
                        throwable.toString()
                    )
            }
        }

    }

    val loginState: StateFlow<LoginState>
        field = MutableStateFlow<LoginState>(LoginState.Pending)

    fun login(coroutineScope: CoroutineScope = viewModelScope) {
        if (loginState.value != LoginState.Loading) {
            loginState.value = LoginState.Loading

            coroutineScope.launch {
                val (username, password) = account.value

                LoginRepository(username, password).flow
                    .catch { throwable: Throwable ->
                        loginState.value = when (throwable) {
                            is IOException -> LoginState.Failure.Network
                            else -> LoginState.Failure.UnexpectedException(throwable)
                        }
                    }
                    .collect { result: LoginRepository.Result ->
                        loginState.value = when (result) {
                            is LoginRepository.Result.Success -> {
                                // Save the token to the PicaRepository
                                PicaRepository.token = result.token
                                storeCredential(username, password)

                                // Return login state
                                LoginState.Success(result.token)
                            }
                            is LoginRepository.Result.Error.InvalidCredentials -> {
                                LoginState.Failure.InvalidCredentials
                            }
                            is LoginRepository.Result.Error.UnknownErrorCode -> {
                                LoginState.Failure.UnexpectedErrorCode(result.code, result.error, result.detail)
                            }
                            is LoginRepository.Result.Error.Unknown -> {
                                LoginState.Failure.UnknownResponse(result.code, result.response)
                            }
                        }
                    }
            }
        }
    }

    private suspend fun storeCredential(username: String, password: String) {
        accountDatastore.updateData {
            accountProto {
                this@accountProto.username = username
                this@accountProto.password = password
            }
        }
    }

}

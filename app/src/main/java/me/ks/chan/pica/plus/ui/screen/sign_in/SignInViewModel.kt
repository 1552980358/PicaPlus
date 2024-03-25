package me.ks.chan.pica.plus.ui.screen.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.serialization.SerializationException
import me.ks.chan.pica.plus.repository.pica.auth.sign_in.PicaSignInRepository
import me.ks.chan.pica.plus.repository.pica.auth.sign_in.PicaSignInRepositoryResult
import me.ks.chan.pica.plus.storage.protobuf.AccountStore
import me.ks.chan.pica.plus.storage.protobuf.AddressProto.Account
import me.ks.chan.pica.plus.ui.screen.sign_in.model.SignInInputFields
import me.ks.chan.pica.plus.ui.screen.sign_in.model.SignInState
import me.ks.chan.pica.plus.ui.screen.sign_in.model.SignInUiState
import me.ks.chan.pica.plus.util.kotlinx.coroutine.Default
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob
import okio.IOException

val signInViewModel: SignInViewModel
    @Composable get() {
        val context = LocalContext.current
        return viewModel(
            factory = viewModelFactory {
                initializer {
                    SignInViewModel(context.AccountStore)
                }
            }
        )
    }

class SignInViewModel(accountStore: DataStore<Account>): ViewModel() {

    private val _inputFields = MutableStateFlow(SignInInputFields())
    val inputFields = _inputFields.asStateFlow()

    init {
        viewModelScope.defaultJob {
            accountStore.data
                .first()
                .let(::SignInInputFields)
                .let(_inputFields::value::set)
        }
    }

    fun updateUsernameField(username: String) {
        _inputFields.update { fields ->
            fields.copy(username = username)
        }
    }

    fun updatePasswordField(password: String) {
        _inputFields.update { fields ->
            fields.copy(password = password)
        }
    }

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    fun startSignIn(
        signInUiState: SignInUiState = this.uiState.value,
        username: String = inputFields.value.username,
        password: String = inputFields.value.password,
    ) {
        if (signInUiState.isPending &&
            username.isNotBlank() &&
            password.isNotBlank()
        ) {
            _uiState.update { uiState ->
                uiState.copy(signInState = SignInState.Loading)
            }

            viewModelScope.defaultJob {
                PicaSignInRepository(username, password)
                    .repositoryRequest
                    .flowOn(Default)
                    .catch { throwable ->
                        _uiState.update { uiState ->
                            uiState.copy(
                                signInState = when (throwable) {
                                    is IOException -> { SignInState.ConnectionError }
                                    is SerializationException -> { SignInState.InvalidResponseError }
                                    else -> { SignInState.UnexpectedExceptionError }
                                }
                            )
                        }
                    }
                    .collect { picaSignInRepositoryResult ->
                        _uiState.update { uiState ->
                            uiState.copy(
                                signInState = when (picaSignInRepositoryResult) {
                                    is PicaSignInRepositoryResult.Success -> {
                                        SignInState.Success(picaSignInRepositoryResult.token)
                                    }
                                    is PicaSignInRepositoryResult.Error -> {
                                        when (picaSignInRepositoryResult.errorType) {
                                            PicaSignInRepositoryResult.ErrorType.EmptyBody -> {
                                                SignInState.InvalidResponseError
                                            }
                                            PicaSignInRepositoryResult.ErrorType.InvalidCredential -> {
                                                SignInState.InvalidCredentialError
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
            }
        }
    }

}
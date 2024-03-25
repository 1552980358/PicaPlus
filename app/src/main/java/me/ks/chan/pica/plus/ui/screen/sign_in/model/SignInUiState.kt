package me.ks.chan.pica.plus.ui.screen.sign_in.model

import androidx.annotation.StringRes
import me.ks.chan.pica.plus.R

data class SignInUiState(
    val signInState: SignInState = SignInState.Pending,
) {

    val isLoading: Boolean
        get() = signInState == SignInState.Loading

    val isPending: Boolean
        get() = signInState == SignInState.Pending

    val editable: Boolean
        get() = !isLoading && !isSuccess

    val isSuccess: Boolean
        get() = signInState is SignInState.Success

}

sealed interface SignInState {

    data object Pending: SignInState

    data object Loading: SignInState

    class Success(val token: String): SignInState

    data object InvalidCredentialError: SignInState

    sealed class DialogError(@StringRes val stringResId: Int): SignInState

    data object ConnectionError: DialogError(
        R.string.screen_sign_in_dialog_text_error_connection
    )

    data object InvalidResponseError: DialogError(
        R.string.screen_sign_in_dialog_text_error_invalid_response
    )

    data object UnexpectedExceptionError: DialogError(
        R.string.screen_sign_in_dialog_text_error_unexpected_exception
    )

}
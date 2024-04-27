package me.ks.chan.pica.plus.ui.screen.sign_in.viewmodel

import androidx.annotation.StringRes
import me.ks.chan.pica.plus.R

sealed interface SignInState {

    val isLoading: Boolean
        get() = this is Loading

    val isPending: Boolean
        get() = this is Pending

    val editable: Boolean
        get() = !isLoading && !isSuccess

    val isSuccess: Boolean
        get() = this is Success

    val submittable: Boolean
        get() = editable || this is Error

    data object Pending: SignInState

    data object Loading: SignInState

    class Success(val token: String): SignInState

    sealed interface Error: SignInState {

        data object InvalidCredential: Error

        sealed class Dialog(@StringRes val stringResId: Int): Error

        data object Connection: Dialog(
            R.string.screen_sign_in_dialog_text_error_connection
        )

        data object InvalidResponse: Dialog(
            R.string.screen_sign_in_dialog_text_error_invalid_response
        )

        data object UnknownException: Dialog(
            R.string.screen_sign_in_dialog_text_error_unknown
        )

    }

}
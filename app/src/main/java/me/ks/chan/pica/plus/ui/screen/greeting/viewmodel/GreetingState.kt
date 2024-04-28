package me.ks.chan.pica.plus.ui.screen.greeting.viewmodel

import androidx.annotation.StringRes
import me.ks.chan.pica.plus.R

sealed interface GreetingState {

    data object Loading: GreetingState

    data class Success(
        val nickname: String,
        val avatar: String?
    ): GreetingState

    data class Error(
        val type: Type
    ): GreetingState {

        enum class Type(
            @StringRes val resId: Int
        ) {
            Connection(R.string.screen_greeting_dialog_error_text_connection),
            InvalidResponse(R.string.screen_greeting_dialog_error_text_invalid),
            Unauthorized(R.string.screen_greeting_dialog_error_text_unauthorized),
            Unknown(R.string.screen_greeting_dialog_error_text_unknown)
        }

    }

}
package me.ks.chan.pica.plus.ui.screen.greeting.viewmodel

import androidx.annotation.StringRes
import me.ks.chan.pica.plus.R

sealed interface GreetingState {

    data object Loading: GreetingState

    data class Success(
        val nickname: String,
        val avatar: String?
    ): GreetingState

    sealed interface Error: GreetingState {

        val messageResId: Int
            @StringRes get

        data object Connection: Error {
            override val messageResId: Int
                get() = R.string.screen_greeting_dialog_error_text_connection
        }

        data object InvalidResponse: Error {
            override val messageResId: Int
                get() = R.string.screen_greeting_dialog_error_text_invalid
        }

        data object Unauthorized: Error {
            override val messageResId: Int
                get() = R.string.screen_greeting_dialog_error_text_unauthorized
        }

        data object Unknown: Error {
            override val messageResId: Int
                get() = R.string.screen_greeting_dialog_error_text_unknown
        }

    }

}
package me.ks.chan.pica.plus.ui.screen.register.viewmodel

import androidx.annotation.StringRes
import me.ks.chan.pica.plus.R

sealed interface RegisterState {

    val editable: Boolean
        get() = this !is Loading

    val submittable: Boolean
        get() = this !is Pending && this !is Loading

    data object Pending: RegisterState

    data object Submittable: RegisterState

    data object Loading: RegisterState {
        override val editable: Boolean
            get() = false
    }

    sealed interface Result: RegisterState {
        @get:StringRes
        val snackTextResId: Int
    }

    data object Success: Result {
        override val snackTextResId: Int
            get() = R.string.screen_register_snackbar_success
    }

    sealed interface Error: Result {

        data object Network: Error {
            override val snackTextResId: Int
                get() = R.string.screen_register_snackbar_error_network
        }

        data object DuplicateUsername: Error {
            override val snackTextResId: Int
                get() = R.string.screen_register_snackbar_error_duplicated
        }

        data object UnknownResponse: Error {
            override val snackTextResId: Int
                get() = R.string.screen_register_snackbar_error_unknown_response
        }

        data object Unknown: Error {
            override val snackTextResId: Int
                get() = R.string.screen_register_snackbar_error_unknown
        }

    }

}
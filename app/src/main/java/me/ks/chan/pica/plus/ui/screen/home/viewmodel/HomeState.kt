package me.ks.chan.pica.plus.ui.screen.home.viewmodel

import androidx.annotation.StringRes
import me.ks.chan.pica.plus.R

sealed interface HomeState {

    data object Loading: HomeState

    data object Pending: HomeState

    data class Update(val comicList: List<HomeComicModel>): HomeState

    sealed interface Error: HomeState {

        @get:StringRes
        val messageResId: Int

        data object Network: Error {
            override val messageResId: Int
                get() = R.string.screen_home_snackbar_error_network
        }

        data object InvalidResponse: Error {
            override val messageResId: Int
                get() = R.string.screen_home_snackbar_error_invalid_response
        }

        data object UnknownStatus: Error {
            override val messageResId: Int
                get() = R.string.screen_home_snackbar_error_unknown_state
        }

        data object Unknown: Error {
            override val messageResId: Int
                get() = R.string.screen_home_snackbar_error_unknown
        }

    }

}
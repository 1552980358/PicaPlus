package me.ks.chan.pica.plus.ui.screen.comic.viewmodel

import androidx.annotation.StringRes
import me.ks.chan.pica.plus.R

sealed interface ComicEpisodeState {

    data object Loading: ComicEpisodeState

    data class Success(
        val episodeList: List<ComicEpisodeModel>
    ): ComicEpisodeState

    sealed interface Error: ComicEpisodeState {

        @get:StringRes
        val messageResId: Int

        data object Network: Error {
            override val messageResId: Int
                get() = R.string.screen_comic_episode_snackbar_error_network
        }

        data object InvalidResponse: Error {
            override val messageResId: Int
                get() = R.string.screen_comic_episode_snackbar_error_invalid_response
        }

        data object InvalidStatus: Error {
            override val messageResId: Int
                get() = R.string.screen_comic_episode_snackbar_error_invalid_status
        }

        data object InvalidResult: Error {
            override val messageResId: Int
                get() = R.string.screen_comic_episode_snackbar_error_invalid_result
        }

        data object Unknown: Error {
            override val messageResId: Int
                get() = R.string.screen_comic_episode_snackbar_error_unknown
        }

    }

}
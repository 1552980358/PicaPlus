package me.ks.chan.pica.plus.ui.screen.comic.viewmodel

import androidx.annotation.StringRes
import me.ks.chan.pica.plus.R

sealed interface ComicDetailState {

    data object Loading: ComicDetailState

    data class Success(
        val comic: ComicModel,
        val uploader: ComicUploaderModel,
    ): ComicDetailState

    sealed interface Error: ComicDetailState {

        @get:StringRes
        val messageResId: Int

        data object UnderReview: Error {
            override val messageResId: Int
                get() = R.string.screen_comic_detail_snackbar_error_review
        }

        data object Network: Error {
            override val messageResId: Int
                get() = R.string.screen_comic_detail_snackbar_error_network
        }

        data object InvalidResponse: Error {
            override val messageResId: Int
                get() = R.string.screen_comic_detail_snackbar_error_invalid_response
        }

        data object InvalidStatus: Error {
            override val messageResId: Int
                get() = R.string.screen_comic_detail_snackbar_error_unknown_status
        }

        data object Unknown: Error {
            override val messageResId: Int
                get() = R.string.screen_comic_detail_snackbar_error_unknown
        }

    }

}
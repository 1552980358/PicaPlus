package me.ks.chan.pica.plus.repository.pica.comics.detail

import me.ks.chan.pica.plus.repository.pica.field.PicaGender

sealed interface PicaComicDetailRepositoryResult {

    data class Success(
        val comic: Comic,
        val uploader: Uploader,
    ): PicaComicDetailRepositoryResult {

        data class Comic(
            val id: String,
            val title: String,
            val description: String,
            val thumb: String,
            val author: String,
            val chineseTeam: String?,
            val categoryList: List<String>,
            val tagList: List<String>,
            val pages: Int,
            val episodes: Int,
            val finished: Boolean,
            val create: Long,
            val update: Long,
            val likes: Int,
            val views: Int,
            val comments: Int,
            val favourite: Boolean,
            val like: Boolean,
            val comment: Boolean,
        )

        data class Uploader(
            val id: String,
            val nickname: String,
            val avatar: String?,
            val gender: PicaGender,
            val experience: Int,
            val level: Int,
            val characterList: List<String> ,
            val role: String?,
            val title: String?,
            val slogan: String?,
        )

    }

    sealed interface Error: PicaComicDetailRepositoryResult {

        data object UnderReview: Error

        data object InvalidResponse: Error

        data object InvalidStatus: Error

    }

}
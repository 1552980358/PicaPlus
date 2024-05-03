package me.ks.chan.pica.plus.repository.pica.comics.random

sealed interface PicaRandomComicsRepositoryResult {

    data class Success(val comicList: List<Comic>): PicaRandomComicsRepositoryResult {
        data class Comic(
            val id: String,
            val title: String,
            val author: String,
            val categoryList: List<String>,
            val episodes: Int,
            val finished: Boolean,
            val likes: Int,
            val pages: Int,
            val thumb: String,
            val views: Int,
        )
    }

    sealed interface Error: PicaRandomComicsRepositoryResult {

        data object InvalidResponse: Error

        data object UnknownStatus: Error

    }

}
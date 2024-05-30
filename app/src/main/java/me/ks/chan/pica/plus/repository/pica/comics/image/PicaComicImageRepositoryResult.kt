package me.ks.chan.pica.plus.repository.pica.comics.image

import me.ks.chan.pica.plus.repository.pica.field.PicaImage

sealed interface PicaComicImageRepositoryResult {

    sealed interface Loop: PicaComicImageRepositoryResult {
        val imageUrlList: List<PicaImage>
    }

    data class Continue(
        val next: Int,
        override val imageUrlList: List<PicaImage>,
    ): Loop

    data class Break(override val imageUrlList: List<PicaImage>): Loop

    sealed interface Collectable: PicaComicImageRepositoryResult

    data class Success(val imageUrlList: List<PicaImage>): Collectable

    sealed interface Error: Collectable {

        data object InvalidResponseData: Error

        data object InvalidResponseStatus: Error

    }

}
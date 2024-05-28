package me.ks.chan.pica.plus.repository.pica.comics.episode

sealed interface PicaComicEpisodeRepositoryResult {

    data class Episode(
        val id: String,
        val title: String,
        val order: Int,
        val update: Long,
    )

    sealed interface Loop: PicaComicEpisodeRepositoryResult {
        val episodeList: List<Episode>
    }

    data class Continue(
        val next: Int,
        override val episodeList: List<Episode>,
    ): Loop

    data class Break(
        override val episodeList: List<Episode>
    ): Loop

    sealed interface Collectable: PicaComicEpisodeRepositoryResult

    data class Success(val episodeList: List<Episode>): Collectable

    sealed interface Error: Collectable {

        data object InvalidResponse: Error

        data object InvalidStatus: Error

    }

}
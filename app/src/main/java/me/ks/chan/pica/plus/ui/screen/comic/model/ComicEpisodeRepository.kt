package me.ks.chan.pica.plus.ui.screen.comic.model

import kotlinx.coroutines.flow.catch
import kotlinx.serialization.SerializationException
import me.ks.chan.pica.plus.repository.pica.comics.episode.PicaComicEpisodeRepository
import me.ks.chan.pica.plus.repository.pica.comics.episode.PicaComicEpisodeRepositoryResult
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicEpisodeModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicEpisodeState
import okio.IOException

class ComicEpisodeRepository(private val comic: String) {

    suspend fun collect(updateEpisodeState: (ComicEpisodeState) -> Unit) {
        PicaComicEpisodeRepository(comic).repositoryFlow
            .catch { cause: Throwable ->
                cause.printStackTrace()
                cause.let(::collectExceptionState)
                    .let(updateEpisodeState)
            }
            .collect { result: PicaComicEpisodeRepositoryResult ->
                result.let(::collectResultState)
                    .let(updateEpisodeState)
            }
    }

}

private fun collectExceptionState(cause: Throwable): ComicEpisodeState {
    return when (cause) {
        is IOException -> { ComicEpisodeState.Error.Network }
        is SerializationException -> { ComicEpisodeState.Error.InvalidResponse }
        else -> { ComicEpisodeState.Error.Unknown }
    }
}

private fun collectResultState(result: PicaComicEpisodeRepositoryResult): ComicEpisodeState {
    return when (result) {
        is PicaComicEpisodeRepositoryResult.Success -> {
            result.episodeList
                .map(::asEpisode)
                .let(ComicEpisodeState::Success)
        }
        is PicaComicEpisodeRepositoryResult.Error -> {
            when (result) {
                is PicaComicEpisodeRepositoryResult.Error.InvalidResponse -> {
                    ComicEpisodeState.Error.InvalidResponse
                }
                is PicaComicEpisodeRepositoryResult.Error.InvalidStatus -> {
                    ComicEpisodeState.Error.InvalidStatus
                }
            }
        }
        else -> { ComicEpisodeState.Error.InvalidResult }
    }
}

private fun asEpisode(episode: PicaComicEpisodeRepositoryResult.Episode): ComicEpisodeModel {
    return ComicEpisodeModel(
        index = episode.order,
        id = episode.id,
        title = episode.title,
        update = episode.update,
    )
}
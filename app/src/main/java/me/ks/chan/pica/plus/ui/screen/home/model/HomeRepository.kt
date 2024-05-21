package me.ks.chan.pica.plus.ui.screen.home.model

import kotlinx.coroutines.flow.catch
import kotlinx.serialization.SerializationException
import me.ks.chan.pica.plus.repository.pica.comics.random.PicaRandomComicsRepository
import me.ks.chan.pica.plus.repository.pica.comics.random.PicaRandomComicsRepositoryResult
import me.ks.chan.pica.plus.ui.screen.home.viewmodel.HomeComicCategoryModel
import me.ks.chan.pica.plus.ui.screen.home.viewmodel.HomeComicModel
import me.ks.chan.pica.plus.ui.screen.home.viewmodel.HomeState
import okio.IOException

object HomeRepository {

    suspend fun collect(updateState: (HomeState) -> Unit) {
        PicaRandomComicsRepository.repositoryFlow
            .catch { cause ->
                cause.let(::collectErrorState)
                    .let(updateState)
            }
            .collect { result ->
                result.let(::collectResultState)
                    .let(updateState)
            }
    }

}

private fun collectErrorState(cause: Throwable): HomeState {
    return when (cause) {
        is IOException -> { HomeState.Error.Network }
        is SerializationException -> { HomeState.Error.InvalidResponse }
        else -> { HomeState.Error.Unknown }
    }
}

private fun collectResultState(result: PicaRandomComicsRepositoryResult): HomeState {
    return when (result) {
        is PicaRandomComicsRepositoryResult.Success -> {
            result.comicList
                .map(::asComicModel)
                .let(HomeState::Update)
        }
        is PicaRandomComicsRepositoryResult.Error -> {
            when (result) {
                is PicaRandomComicsRepositoryResult.Error.InvalidResponse -> {
                    HomeState.Error.InvalidResponse
                }
                is PicaRandomComicsRepositoryResult.Error.UnknownStatus -> {
                    HomeState.Error.UnknownStatus
                }
            }
        }
    }
}

private fun asComicModel(comic: PicaRandomComicsRepositoryResult.Success.Comic): HomeComicModel {
    return HomeComicModel(
        id = comic.id,
        title = comic.title,
        author = comic.author,
        thumb = comic.thumb,
        categoryList = comic.categoryList
            .map(::HomeComicCategoryModel),
        pages = comic.pages,
        serializing = comic.serializing,
        likes = comic.likes,
        views = comic.views,
    )
}
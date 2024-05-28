package me.ks.chan.pica.plus.ui.screen.comic.model

import kotlinx.coroutines.flow.catch
import kotlinx.serialization.SerializationException
import me.ks.chan.pica.plus.repository.pica.comics.detail.PicaComicDetailRepository
import me.ks.chan.pica.plus.repository.pica.comics.detail.PicaComicDetailRepositoryResult
import me.ks.chan.pica.plus.repository.pica.field.PicaGender
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicCategoryModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicDetailState
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicUploaderGenderModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicUploaderModel
import okio.IOException

class ComicDetailRepository(private val comic: String) {

    suspend fun collect(updateState: (ComicDetailState) -> Unit) {
        PicaComicDetailRepository(comic).repositoryFlow
            .catch { cause: Throwable ->
                cause.printStackTrace()
                cause.let(::collectErrorState)
                    .let(updateState)
            }
            .collect { result: PicaComicDetailRepositoryResult ->
                result.let(::collectResultState)
                    .let(updateState)
            }
    }

}

private fun collectErrorState(cause: Throwable): ComicDetailState {
    return when (cause) {
        is IOException -> { ComicDetailState.Error.Network }
        is SerializationException -> { ComicDetailState.Error.InvalidResponse }
        else -> { ComicDetailState.Error.Unknown }
    }
}

private fun collectResultState(result: PicaComicDetailRepositoryResult): ComicDetailState {
    return when (result) {
        is PicaComicDetailRepositoryResult.Success -> {
            ComicDetailState.Success(
                comic = result.comic.let(::asComic),
                uploader = result.uploader.let(::asUploader)
            )
        }
        is PicaComicDetailRepositoryResult.Error -> {
            when (result) {
                is PicaComicDetailRepositoryResult.Error.UnderReview -> {
                    ComicDetailState.Error.UnderReview
                }
                is PicaComicDetailRepositoryResult.Error.InvalidResponse -> {
                    ComicDetailState.Error.InvalidResponse
                }
                is PicaComicDetailRepositoryResult.Error.InvalidStatus -> {
                    ComicDetailState.Error.InvalidStatus
                }
            }
        }
    }
}

private fun asComic(comic: PicaComicDetailRepositoryResult.Success.Comic): ComicModel {
    return ComicModel(
        id = comic.id,
        title = comic.title,
        description = comic.description,
        thumb = comic.thumb,
        author = comic.author,
        chineseTeam = comic.chineseTeam,
        categoryList = comic.categoryList.map(::ComicCategoryModel),
        tagList = comic.tagList,
        pages = comic.pages,
        episodes = comic.episodes,
        finished = comic.finished,
        create = comic.create,
        update = comic.update,
        likes = comic.likes,
        views = comic.views,
        comments = comic.comments,
        favourite = comic.favourite,
        like = comic.like,
        comment = comic.comment,
    )
}

private fun asUploader(uploader: PicaComicDetailRepositoryResult.Success.Uploader): ComicUploaderModel {
    return ComicUploaderModel(
        id = uploader.id,
        nickname = uploader.nickname,
        avatar = uploader.avatar,
        gender = when (uploader.gender) {
            PicaGender.Gentleman -> { ComicUploaderGenderModel.Gentleman }
            PicaGender.Lady -> { ComicUploaderGenderModel.Lady }
            PicaGender.Bot -> { ComicUploaderGenderModel.Bot }
        },
        experience = uploader.experience,
        level = uploader.level,
        characterList = uploader.characterList,
        role = uploader.role,
        title = uploader.title,
        slogan = uploader.slogan,
    )
}
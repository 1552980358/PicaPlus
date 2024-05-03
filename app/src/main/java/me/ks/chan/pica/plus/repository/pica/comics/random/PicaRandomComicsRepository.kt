package me.ks.chan.pica.plus.repository.pica.comics.random

import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ks.chan.pica.plus.repository.pica.PicaImage
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryDataResponse
import me.ks.chan.pica.plus.util.okhttp.RequestSuccess
import me.ks.chan.pica.plus.util.okhttp.deserialize

private const val FieldComicList = "comics"

private const val ComicFieldId = "_id"
private const val ComicFieldTitle = "title"
private const val ComicFieldAuthor = "author"
private const val ComicFieldPages = "pagesCount"
private const val ComicFieldEpisodes = "epsCount"
private const val ComicFieldFinished = "finished"
private const val ComicFieldCategoryList = "categories"
private const val ComicFieldThumb = "thumb"
private const val ComicFieldViews = "totalViews"
private const val ComicFieldLikes = "totalLikes"

@Serializable
private data class Data(
    @SerialName(FieldComicList)
    val comicList: List<Comic>
) {
    @Serializable
    data class Comic(
        @SerialName(ComicFieldId)
        val id: String,
        @SerialName(ComicFieldTitle)
        val title: String,
        @SerialName(ComicFieldAuthor)
        val author: String,
        @SerialName(ComicFieldCategoryList)
        val categoryList: List<String>,
        @SerialName(ComicFieldEpisodes)
        val episodes: Int,
        @SerialName(ComicFieldFinished)
        val finished: Boolean,
        @SerialName(ComicFieldLikes)
        val likes: Int,
        @SerialName(ComicFieldPages)
        val pages: Int,
        @SerialName(ComicFieldThumb)
        val thumb: PicaImage,
        @SerialName(ComicFieldViews)
        val views: Int,
        @Deprecated("This is a duplicated field for lower version compatibility.")
        @SerialName("likesCount")
        private val likesCount: Int,
    )
}

@Serializable
private data class ResponseBody(
    override val code: Int,
    override val message: String,
    override val data: Data
): PicaRepositoryDataResponse<Data>()

private const val RandomComicsApiPath = "comics/random"

object PicaRandomComicsRepository {

    val repositoryFlow = flow {
        val response = PicaRepository.get(RandomComicsApiPath)

        val result = when (response.code) {
            RequestSuccess -> {
                response.deserialize<ResponseBody>()
                    ?.data
                    ?.let(::asResultComicList)
                    ?.let(PicaRandomComicsRepositoryResult::Success)
                    ?: PicaRandomComicsRepositoryResult.Error.InvalidResponse
            }
            else -> {
                PicaRandomComicsRepositoryResult.Error.UnknownStatus
            }
        }

        emit(result)
    }

}

private fun asResultComicList(data: Data): List<PicaRandomComicsRepositoryResult.Success.Comic> {
    return data.comicList
        .map {
            PicaRandomComicsRepositoryResult.Success.Comic(
                id = it.id,
                title = it.title,
                author = it.author,
                categoryList = it.categoryList,
                episodes = it.episodes,
                finished = it.finished,
                likes = it.likes,
                pages = it.pages,
                thumb = it.thumb.url,
                views = it.views,
            )
        }
}
package me.ks.chan.pica.plus.repository.pica.comics.random

import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ks.chan.pica.plus.repository.pica.PicaImage
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryDataResponse
import me.ks.chan.pica.plus.util.okhttp.RequestSuccess
import me.ks.chan.pica.plus.util.okhttp.deserialize

@Serializable
private data class Data(
    @SerialName("comics")
    val comicList: List<Comic>
) {
    @Serializable
    data class Comic(
        @SerialName("_id")
        val id: String,
        @SerialName("title")
        val title: String,
        @SerialName("author")
        val author: String,
        @SerialName("categories")
        val categoryList: List<String>,
        @SerialName("epsCount")
        val episodes: Int,
        @SerialName("finished")
        val finished: Boolean,
        @SerialName("totalLikes")
        val likes: Int,
        @SerialName("pagesCount")
        val pages: Int,
        @SerialName("thumb")
        val thumb: PicaImage,
        @SerialName("totalViews")
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
                serializing = it.finished.not(),
                likes = it.likes,
                pages = it.pages,
                thumb = it.thumb.url,
                views = it.views,
            )
        }
}
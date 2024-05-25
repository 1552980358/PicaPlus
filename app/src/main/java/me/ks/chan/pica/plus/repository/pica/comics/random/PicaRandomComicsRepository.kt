package me.ks.chan.pica.plus.repository.pica.comics.random

import kotlin.math.max
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ks.chan.pica.plus.repository.pica.field.PicaImage
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
        val author: String? = null,
        @SerialName("categories")
        val categoryList: List<String>,
        @SerialName("epsCount")
        val episodes: Int,
        @SerialName("finished")
        val finished: Boolean,
        @SerialName("pagesCount")
        val pages: Int,
        @SerialName("thumb")
        val thumb: PicaImage,
        /**
         * Following fields are for backward capability.
         **/
        /**
         * Open method see [Comic.views]
         **/
        @SerialName("totalViews")
        private val totalViews: Int = 0,
        @SerialName("viewsCount")
        private val viewsCount: Int = 0,
        /**
         * Open method see [Comic.likes]
         **/
        @SerialName("totalLikes")
        private val totalLikes: Int = 0,
        @SerialName("likesCount")
        private val likesCount: Int = 0,
    ) {

        val views: Int
            get() = max(totalViews, viewsCount)

        val likes: Int
            get() = max(totalLikes, likesCount)

    }
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
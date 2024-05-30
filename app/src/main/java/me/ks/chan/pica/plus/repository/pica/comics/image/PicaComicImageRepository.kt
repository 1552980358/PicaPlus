package me.ks.chan.pica.plus.repository.pica.comics.image

import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ks.chan.pica.plus.repository.pica.comics.image.PicaComicImageRepositoryResult as Result
import android.util.Log
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryDataResponse
import me.ks.chan.pica.plus.repository.pica.field.PicaImage
import me.ks.chan.pica.plus.util.kotlin.observe
import me.ks.chan.pica.plus.util.okhttp.ResponseStatus
import me.ks.chan.pica.plus.util.okhttp.deserialize
import me.ks.chan.pica.plus.util.okhttp.status

@Serializable
private data class Data(
    @SerialName("ep")
    val episode: Episode,
    @SerialName("pages")
    val pages: Pages,
) {

    @Serializable
    data class Episode(
        @SerialName("_id")
        val id: String,
        @SerialName("title")
        val title: String,
    )

    @Serializable
    data class Pages(
        @SerialName("docs")
        val pageList: List<Page>,
        @SerialName("page")
        val page: Int,
        @SerialName("pages")
        val pages: Int,

        @SerialName("limit")
        private val limit: Int,
        @SerialName("total")
        private val total: Int,
    )

    @Serializable
    data class Page(
        @SerialName("media")
        val image: PicaImage,

        @SerialName("id")
        private val id: String,
        @SerialName("_id")
        private val _id: String,
    )

}

@Serializable
private data class ResponseBody(
    override val code: Int,
    override val message: String,
    override val data: Data
): PicaRepositoryDataResponse<Data>()

private const val ComicImageUrlPath = "comics/%s/order/%d/pages?page=%d"
private const val DefaultPage = 1

class PicaComicImageRepository(comic: String, index: Int) {

    val repositoryFlow = flow {
        val imageUrlList = mutableListOf<PicaImage>()

        var result: Result? = null
        do {
            val response = PicaRepository.get(
                ComicImageUrlPath.format(
                    comic,
                    index,
                    result.observe(Result.Continue::next) ?: DefaultPage
                )
            )

            result = when (response.status) {
                is ResponseStatus.Ok -> {
                    response.deserialize(catchBody = ::log, transform = ::pages)
                        ?.let(::collectPagesLoopResult)
                        ?: Result.Error.InvalidResponseData
                }
                else -> {
                    Result.Error.InvalidResponseStatus
                }
            }

            if (result is Result.Loop) {
                imageUrlList += result.imageUrlList
            }
        } while (result !is Result.Error && result !is Result.Break)

        if (result is Result.Break) {
            result = Result.Success(imageUrlList)
        }
        emit(result)
    }

}

@Suppress("LongLogTag")
private fun log(body: String) = Log.d("PicaComicImageRepository", body)

private fun pages(responseBody: ResponseBody) =
    responseBody.data.pages

private fun collectPagesLoopResult(
    pages: Data.Pages,
    imageUrlList: List<PicaImage> = pages.pageList.map(Data.Page::image),
): Result.Loop {
    return when (pages.page) {
        pages.pages -> { Result.Break(imageUrlList) }
        else -> { Result.Continue(pages.page.inc(), imageUrlList) }
    }
}
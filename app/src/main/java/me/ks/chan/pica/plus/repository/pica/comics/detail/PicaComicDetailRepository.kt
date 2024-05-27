package me.ks.chan.pica.plus.repository.pica.comics.detail

import kotlin.math.max
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ks.chan.pica.plus.repository.pica.comics.detail.PicaComicDetailRepositoryResult.Error
import me.ks.chan.pica.plus.repository.pica.comics.detail.PicaComicDetailRepositoryResult.Success
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryDataResponse
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryErrorResponse
import me.ks.chan.pica.plus.repository.pica.field.PicaGender
import me.ks.chan.pica.plus.repository.pica.field.PicaImage
import me.ks.chan.pica.plus.util.okhttp.ResponseStatus
import me.ks.chan.pica.plus.util.okhttp.deserialize
import me.ks.chan.pica.plus.util.okhttp.status

@Serializable
private data class Data(
    @SerialName("comic")
    val comic: Comic
) {

    @Serializable
    data class Comic(
        @SerialName("_id")
        val id: String,
        @SerialName("_creator")
        val uploader: Uploader,
        @SerialName("title")
        val title: String,
        @SerialName("description")
        val description: String,
        @SerialName("thumb")
        val thumb: PicaImage,
        @SerialName("author")
        val author: String,
        @SerialName("chineseTeam")
        val chineseTeam: String? = null,
        @SerialName("categories")
        val categoryList: List<String>,
        @SerialName("tags")
        val tagList: List<String>,
        @SerialName("pagesCount")
        val pages: Int,
        @SerialName("epsCount")
        val episodes: Int,
        @SerialName("finished")
        val finished: Boolean,
        @SerialName("created_at")
        val create: String,
        @SerialName("updated_at")
        val update: String,

        /**
         * User status
         */
        @SerialName("isFavourite")
        val favourite: Boolean,
        @SerialName("isLiked")
        val like: Boolean,

        /**
         * User action
         **/
        @SerialName("allowComment")
        val comment: Boolean,

        /**
         * Download permission check will be ignored forever
         */
        @SerialName("allowDownload")
        private val download: Boolean,

        /**
         * Compatibility fields, see member backing fields
         **/
        /**
         * See [Comic.views]
         **/
        @SerialName("totalViews")
        private val totalViews: Int = 0,
        @SerialName("viewsCount")
        private val viewsCount: Int = 0,

        /**
         * See [Comic.likes]
         **/
        @SerialName("totalLikes")
        private val totalLikes: Int = 0,
        @SerialName("likesCount")
        private val likesCount: Int = 0,

        /**
         * See [Comic.comments]
         **/
        @SerialName("totalComments")
        private val totalComments: Int = 0,
        @SerialName("commentsCount")
        private val commentsCount: Int = 0,
    ) {

        val views: Int
            get() = max(totalViews, viewsCount)

        val likes: Int
            get() = max(totalLikes, likesCount)

        val comments: Int
            get() = max(totalComments, commentsCount)

    }

    @Serializable
    data class Uploader(
        @SerialName("_id")
        val id: String,
        @SerialName("name")
        val nickname: String,
        @SerialName("avatar")
        val avatar: PicaImage? = null,
        @SerialName("gender")
        val gender: PicaGender,
        @SerialName("exp")
        val experience: Int,
        @SerialName("level")
        val level: Int,
        @SerialName("characters")
        val characterList: List<String> = listOf(),
        @SerialName("role")
        val role: String? = null,
        @SerialName("title")
        val title: String? = null,
        @SerialName("slogan")
        val slogan: String? = null,

        /**
         * Hidden field: verification is now broken and not fixed
         **/
        @SerialName("verified")
        private val verified: Boolean = false,
    )

}

@Serializable
private data class ResponseBody(
    override val code: Int,
    override val message: String,
    override val data: Data
): PicaRepositoryDataResponse<Data>()

private const val PicaComicDetailRepositoryPath = "comics/%s"

private const val ErrorUnderReview = "1014"

class PicaComicDetailRepository(comic: String) {

    val repositoryFlow = flow {
        val response = PicaRepository.get(
            PicaComicDetailRepositoryPath.format(comic)
        )

        val result = when (response.status) {
            is ResponseStatus.Ok -> {
                response.deserialize<ResponseBody>()
                    ?.data
                    ?.comic
                    ?.let(::asSuccessResult)
                    ?: Error.InvalidResponse
            }
            is ResponseStatus.BadRequest -> {
                response.deserialize<PicaRepositoryErrorResponse>()
                    ?.error
                    .takeIf { it == ErrorUnderReview }
                    ?.let { Error.UnderReview }
                    ?: Error.InvalidStatus
            }
            else -> {
                Error.InvalidStatus
            }
        }

        emit(result)
    }

}

private fun asSuccessResult(comic: Data.Comic): Success {
    return Success(
        comic = comic.let(::asResultComic),
        uploader = comic.uploader.let(::asResultUploader),
    )
}

private fun asResultComic(comic: Data.Comic): Success.Comic {
    return Success.Comic(
        id = comic.id,
        title = comic.title,
        description = comic.description,
        thumb = comic.thumb.url,
        author = comic.author,
        chineseTeam = comic.chineseTeam,
        categoryList = comic.categoryList,
        tagList = comic.tagList,
        pages = comic.pages,
        episodes = comic.episodes,
        finished = comic.finished,
        create = Instant.parse(comic.create)
            .toEpochMilliseconds(),
        update = Instant.parse(comic.update)
            .toEpochMilliseconds(),
        likes = comic.likes,
        views = comic.views,
        comments = comic.comments,
        favourite = comic.favourite,
        like = comic.like,
        comment = comic.comment,
    )
}

private fun asResultUploader(uploader: Data.Uploader): Success.Uploader {
    return Success.Uploader(
        id = uploader.id,
        nickname = uploader.nickname,
        gender = uploader.gender,
        experience = uploader.experience,
        level = uploader.level,
        characterList = uploader.characterList,
        role = uploader.role,
        avatar = uploader.avatar?.url,
        slogan = uploader.slogan,
        title = uploader.title,
    )
}
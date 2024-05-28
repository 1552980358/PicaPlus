package me.ks.chan.pica.plus.repository.pica.comics.episode

import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ks.chan.pica.plus.repository.pica.comics.episode.PicaComicEpisodeRepositoryResult as Result
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryDataResponse
import me.ks.chan.pica.plus.util.kotlin.observe
import me.ks.chan.pica.plus.util.okhttp.ResponseStatus
import me.ks.chan.pica.plus.util.okhttp.deserialize
import me.ks.chan.pica.plus.util.okhttp.status

@Serializable
private data class Data(
    @SerialName("eps")
    val episodes: Episodes
) {
    @Serializable
    data class Episodes(
        @SerialName("docs")
        val episodeList: List<Episode>,

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
    data class Episode(
        @SerialName("id")
        val id: String,
        @SerialName("order")
        val order: Int,
        @SerialName("title")
        val title: String,
        @SerialName("updated_at")
        val update: String,

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

private const val DefaultPage = 1
private const val ComicEpisodeUrlPath = "comics/%s/eps?page=%d"

class PicaComicEpisodeRepository(comic: String) {

    val repositoryFlow = flow {
        val episodeList = mutableListOf<Result.Episode>()

        var result: Result? = null
        do {
            val response = PicaRepository.get(
                ComicEpisodeUrlPath.format(
                    comic,
                    /**
                     * Get [Result.Continue.next] if result is instance of [Result.Continue],
                     * otherwise, use default page
                     **/
                    result.observe(Result.Continue::next) ?: DefaultPage
                )
            )
            result = when (response.status) {
                is ResponseStatus.Ok -> {
                    response.deserialize<ResponseBody>()
                        ?.data
                        ?.episodes
                        ?.let(::collectResponseResult)
                        ?: Result.Error.InvalidResponse
                }
                else -> { Result.Error.InvalidStatus }
            }
            // Collect
            if (result is Result.Loop) {
                episodeList += result.episodeList
            }
        } while (
            result !is Result.Collectable &&
            result !is Result.Break
        )

        // Swap as Collectable
        if (result is Result.Break) {
            result = Result.Success(episodeList)
        }

        emit(result)
    }

}

private fun collectResponseResult(episodes: Data.Episodes): Result {
    return episodes.episodeList
        .map(::asResultEpisode)
        .let { episodeList: List<Result.Episode> ->
            collectLoopResult(
                episodes = episodes,
                episodeList = episodeList,
            )
        }
}

private fun asResultEpisode(episode: Data.Episode): Result.Episode {
    return Result.Episode(
        id = episode.id,
        title = episode.title,
        order = episode.order,
        update = episode.update.let(Instant::parse)
            .toEpochMilliseconds(),
    )
}

private fun collectLoopResult(episodes: Data.Episodes, episodeList: List<Result.Episode>): Result {
    return when (episodes.page) {
        episodes.pages -> { Result.Break(episodeList = episodeList) }
        else -> {
            Result.Continue(
                episodeList = episodeList, next = episodes.page.inc(),
            )
        }
    }
}
package me.ks.chan.pica.plus.repository.pica.comics

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.comics.episode.PicaComicEpisodeRepository
import me.ks.chan.pica.plus.repository.pica.comics.episode.PicaComicEpisodeRepositoryResult
import org.junit.Test

class PicaComicEpisodeRepositoryTest {

    private val token: String
        get() = """
            |
            """.trimMargin()
            .lines()
            .joinToString("")

    @Test
    fun testRepositoryFlow() {
        PicaRepository.authorization(token = token)
        runTest {
            PicaComicEpisodeRepository("6479efb8f109b12134ff0a69").repositoryFlow
                .catch { cause: Throwable ->
                    cause.printStackTrace()
                    if (cause is SerializationException) {
                        assert(false)
                    }
                }
                .collect { result ->
                    result.let(::println)
                    when (result) {
                        is PicaComicEpisodeRepositoryResult.Success -> {
                            assertEquals(44, result.episodeList.size)
                        }
                        else -> {
                            assert(false)
                        }
                    }
                }
        }
    }

}
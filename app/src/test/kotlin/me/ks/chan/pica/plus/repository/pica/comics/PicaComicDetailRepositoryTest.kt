package me.ks.chan.pica.plus.repository.pica.comics

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.comics.detail.PicaComicDetailRepository
import org.junit.Test

class PicaComicDetailRepositoryTest {

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
            PicaComicDetailRepository("5cd6debdbb45ef7cfbf3df71").repositoryFlow
                .catch { cause: Throwable ->
                    cause.printStackTrace()
                    if (cause is SerializationException) {
                        assert(false)
                    }
                }
                .collect { result ->
                    result.let(::println)
                }
        }
    }

}
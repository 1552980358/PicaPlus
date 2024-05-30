package me.ks.chan.pica.plus.repository.pica.comics

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.comics.image.PicaComicImageRepository
import me.ks.chan.pica.plus.repository.pica.comics.image.PicaComicImageRepositoryResult
import org.junit.Assert.assertEquals
import org.junit.Test

class PicaComicImageRepositoryTest {

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
            PicaComicImageRepository("64147e54da9b1b07d30df7e1", 1).repositoryFlow
                .catch { cause: Throwable ->
                    cause.printStackTrace()
                    assert(false)
                }
                .collect { result: PicaComicImageRepositoryResult ->
                    when (result) {
                        is PicaComicImageRepositoryResult.Success -> {
                            assertEquals(
                                64,
                                result.imageUrlList.size,
                            )
                        }
                        else -> {
                            assert(false)
                        }
                    }
                }
        }
    }

}
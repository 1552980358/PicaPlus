package me.ks.chan.pica.plus.repository.pica.categories

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import me.ks.chan.pica.plus.repository.pica.categories.PicaCategoriesRepositoryResult.Error
import me.ks.chan.pica.plus.repository.pica.categories.PicaCategoriesRepositoryResult.Success
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import org.junit.Test

class PicaCategoriesRepositoryTest {

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
            PicaCategoriesRepository.repositoryFlow
                .catch { cause ->
                    cause.printStackTrace()
                    assert(false)
                }
                .collect { result ->
                    when (result) {
                        is Success -> {
                            result.categoryList.let(::println)
                        }
                        is Error -> {
                            result.let(::println)
                            assert(false)
                        }
                    }
                }
        }
    }

}
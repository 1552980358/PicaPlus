package me.ks.chan.pica.plus.repository.pica.account.profile

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import org.junit.Before
import org.junit.Test

class ProfileRepositoryTest {

    @Before
    fun setUp() {
        PicaRepository.token =
            """|
            |
            |""".trimMargin()
    }

    @Test
    fun test() = runTest {
        ProfileRepository.flow
            .catch {
                println(it)
                assert(false)
            }
            .collect { result ->
                if (result is ProfileRepository.Result.Success) {
                    println(result)
                    assert(true)
                } else {
                    assert(false)
                }
            }
    }

}
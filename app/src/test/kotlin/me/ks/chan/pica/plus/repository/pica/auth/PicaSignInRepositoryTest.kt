package me.ks.chan.pica.plus.repository.pica.auth

import kotlinx.coroutines.runBlocking
import me.ks.chan.pica.plus.repository.pica.auth.sign_in.PicaSignInRepository
import me.ks.chan.pica.plus.repository.pica.auth.sign_in.PicaSignInRepositoryResult
import org.junit.Test

class PicaSignInRepositoryTest {

    @Test
    fun picaSignInRepositoryTest() = runBlocking {
        PicaSignInRepository("", "")
            .repositoryRequest
            .collect { picaSignInRepositoryResult ->
                when (picaSignInRepositoryResult) {
                    is PicaSignInRepositoryResult.Success -> {
                        println("Token: ${picaSignInRepositoryResult.token}")
                    }
                    is PicaSignInRepositoryResult.Error -> {
                        println("Error: ${picaSignInRepositoryResult.type}")
                    }
                }
            }
    }

}
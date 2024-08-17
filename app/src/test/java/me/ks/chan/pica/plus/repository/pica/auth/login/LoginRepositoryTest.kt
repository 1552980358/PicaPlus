package me.ks.chan.pica.plus.repository.pica.auth.login

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LoginRepositoryTest {

    private val username = ""
    private val password = ""

    @Test
    fun testLogin() = runTest {
        if (username.isNotBlank() && password.isNotBlank()) {
            LoginRepository(username, password).flow
                .catch { assert(false) { it.message!! } }
                .collect { result ->
                    when (result) {
                        is LoginRepository.Result.Success -> {
                            assert(result.token.isNotEmpty(), result::token)
                        }
                        else -> {
                            assert(false, result::toString)
                        }
                    }
                }
        }
    }

}
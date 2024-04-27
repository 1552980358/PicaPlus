package me.ks.chan.pica.plus.ui.screen.sign_in.model

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.SerializationException
import me.ks.chan.pica.plus.repository.pica.auth.sign_in.PicaSignInRepository
import me.ks.chan.pica.plus.repository.pica.auth.sign_in.PicaSignInRepositoryResult
import me.ks.chan.pica.plus.ui.screen.sign_in.viewmodel.SignInFields
import me.ks.chan.pica.plus.ui.screen.sign_in.viewmodel.SignInState
import me.ks.chan.pica.plus.util.kotlinx.coroutine.Default
import okio.IOException

interface SignInRepository {

    suspend fun collect(
        fields: SignInFields,
        updateState: (SignInState) -> Unit
    )

    companion object: SignInRepository {

        override suspend fun collect(
            fields: SignInFields,
            updateState: (SignInState) -> Unit,
        ) {
            PicaSignInRepository(fields).repositoryRequest
                .flowOn(Default)
                .catch { cause ->
                    cause.let(::asErrorState)
                        .let(updateState)
                }
                .collect { result ->
                    result.let(::collectResultState)
                        .let(updateState)
                }
        }

        private fun asErrorState(cause: Throwable): SignInState {
            return when (cause) {
                is IOException -> { SignInState.Error.Connection }
                is SerializationException -> { SignInState.Error.InvalidResponse }
                else -> { SignInState.Error.UnknownException }
            }
        }

        private fun collectResultState(result: PicaSignInRepositoryResult): SignInState {
            return when (result) {
                is PicaSignInRepositoryResult.Success -> { SignInState.Success(result.token) }
                is PicaSignInRepositoryResult.Error -> {
                    when (result.type) {
                        PicaSignInRepositoryResult.Error.Type.EmptyBody -> {
                            SignInState.Error.InvalidResponse
                        }
                        PicaSignInRepositoryResult.Error.Type.InvalidCredential -> {
                            SignInState.Error.InvalidCredential
                        }
                    }
                }
            }
        }
    }

}

private fun PicaSignInRepository(fields: SignInFields): PicaSignInRepository {
    return PicaSignInRepository(fields.username, fields.password)
}
package me.ks.chan.pica.plus.ui.screen.greeting.model

import java.io.IOException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.SerializationException
import me.ks.chan.pica.plus.repository.pica.users.profile.PicaProfileRepository
import me.ks.chan.pica.plus.repository.pica.users.profile.PicaProfileRepositoryResult
import me.ks.chan.pica.plus.ui.screen.greeting.viewmodel.GreetingState
import me.ks.chan.pica.plus.util.kotlinx.coroutine.Default

interface GreetingRepository {

    suspend fun collect(updateState: (GreetingState) -> Unit)

    companion object: GreetingRepository {

        override suspend fun collect(updateState: (GreetingState) -> Unit) {
            PicaProfileRepository.repositoryFlow
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

        private fun asErrorState(cause: Throwable): GreetingState {
            return GreetingState.Error(
                type = when (cause) {
                    is IOException -> {
                        GreetingState.Error.Type.Connection
                    }
                    is SerializationException,
                    is IllegalArgumentException -> {
                        GreetingState.Error.Type.InvalidResponse
                    }
                    else -> {
                        GreetingState.Error.Type.Unknown
                    }
                }
            )
        }

        private fun collectResultState(result: PicaProfileRepositoryResult): GreetingState {
            return when (result) {
                is PicaProfileRepositoryResult.Success -> {
                    GreetingState.Success(
                        nickname = result.nickname,
                        avatar = result.avatar
                    )
                }
                is PicaProfileRepositoryResult.Error -> {
                    GreetingState.Error(
                        type = when (result.type) {
                            PicaProfileRepositoryResult.Error.Type.Unauthorized -> {
                                GreetingState.Error.Type.Unauthorized
                            }
                            else -> {
                                GreetingState.Error.Type.Unknown
                            }
                        }
                    )
                }
            }
        }

    }

}
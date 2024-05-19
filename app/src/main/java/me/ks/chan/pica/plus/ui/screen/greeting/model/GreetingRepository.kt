package me.ks.chan.pica.plus.ui.screen.greeting.model

import java.io.IOException
import kotlinx.coroutines.flow.catch
import kotlinx.serialization.SerializationException
import me.ks.chan.pica.plus.repository.pica.users.profile.PicaProfileRepository
import me.ks.chan.pica.plus.repository.pica.users.profile.PicaProfileRepositoryResult
import me.ks.chan.pica.plus.ui.screen.greeting.viewmodel.GreetingState

object GreetingRepository {

    suspend fun collect(updateState: (GreetingState) -> Unit) {
        PicaProfileRepository.repositoryFlow
            .catch { cause ->
                cause.let(::asErrorState)
                    .let(updateState)
            }
            .collect { result ->
                result.let(::collectResultState)
                    .let(updateState)
            }
    }

}

private fun asErrorState(cause: Throwable): GreetingState {
    return when (cause) {
        is IOException -> {
            GreetingState.Error.Connection
        }
        is SerializationException,
        is IllegalArgumentException -> {
            GreetingState.Error.InvalidResponse
        }
        else -> {
            GreetingState.Error.Unknown
        }
    }
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
            when (result) {
                is PicaProfileRepositoryResult.Error.Unauthorized -> {
                    GreetingState.Error.Unauthorized
                }
                else -> {
                    GreetingState.Error.Unknown
                }
            }
        }
    }
}
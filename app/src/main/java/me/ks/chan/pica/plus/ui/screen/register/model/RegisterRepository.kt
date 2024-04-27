package me.ks.chan.pica.plus.ui.screen.register.model

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.SerializationException
import me.ks.chan.pica.plus.repository.pica.auth.register.PicaRegisterRepository
import me.ks.chan.pica.plus.repository.pica.auth.register.PicaRegisterRepositoryResult
import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterFields
import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterState
import me.ks.chan.pica.plus.util.kotlin.Blank
import me.ks.chan.pica.plus.util.kotlinx.coroutine.Default
import okio.IOException
import java.text.SimpleDateFormat
import java.util.Locale

interface RegisterRepository {

    suspend fun collect(
        fields: RegisterFields,
        updateState: (RegisterState) -> Unit
    )

    companion object: RegisterRepository {

        override suspend fun collect(
            fields: RegisterFields,
            updateState: (RegisterState) -> Unit,
        ) {
            PicaRegisterRepository(fields).repositoryFlow
                .catch { throwable ->
                    throwable.let(::asErrorState)
                        .let(updateState)
                }
                .flowOn(Default)
                .collect { result ->
                    result.let(::collectResultState)
                        .let(updateState)
                }
        }

        private fun asErrorState(throwable: Throwable): RegisterState {
            return when (throwable) {
                is IOException -> {
                    RegisterState.Error.Network
                }
                is SerializationException -> {
                    RegisterState.Error.UnknownResponse
                }
                else -> {
                    RegisterState.Error.Unknown
                }
            }
        }

        private fun collectResultState(result: PicaRegisterRepositoryResult): RegisterState {
            return when (result) {
                is PicaRegisterRepositoryResult.Success -> {
                    RegisterState.Success
                }
                is PicaRegisterRepositoryResult.Error -> {
                    when (result.type) {
                        PicaRegisterRepositoryResult.Error.Type.UsernameDuplicated -> {
                            RegisterState.Error.DuplicateUsername
                        }
                        else -> {
                            RegisterState.Error.Unknown
                        }
                    }
                }
            }
        }

    }

}

private const val BirthdayFormat = "yyyy-MM-dd"
private fun asDateString(millis: Long?): String {
    return when {
        millis == null -> Blank
        else -> {
            SimpleDateFormat(BirthdayFormat, Locale.getDefault())
                .format(millis)
        }
    }
}

private fun PicaRegisterRepository(fields: RegisterFields): PicaRegisterRepository {
    return PicaRegisterRepository(
        username = fields.username,
        password = fields.password,
        nickname = fields.nickname,
        birthday = fields.birthdayMillis.let(::asDateString),
        questionA = fields.questionA,
        answerA = fields.answerA,
        questionB = fields.questionB,
        answerB = fields.answerB,
        questionC = fields.questionC,
        answerC = fields.answerC,
    )
}
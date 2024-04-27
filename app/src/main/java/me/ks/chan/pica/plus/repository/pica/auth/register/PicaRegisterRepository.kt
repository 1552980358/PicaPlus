package me.ks.chan.pica.plus.repository.pica.auth.register

import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryErrorResponse
import me.ks.chan.pica.plus.util.okhttp.RequestSuccess
import me.ks.chan.pica.plus.util.okhttp.deserialize

private const val RequestFieldUsername = "email"
private const val RequestFieldPassword = "password"
private const val RequestFieldNickname = "name"
private const val RequestFieldBirthday = "birthday"
private const val RequestFieldQuestionA = "question1"
private const val RequestFieldAnswerA = "answer1"
private const val RequestFieldQuestionB = "question2"
private const val RequestFieldAnswerB = "answer2"
private const val RequestFieldQuestionC = "question3"
private const val RequestFieldAnswerC = "answer3"

@Serializable
private data class RequestBody(
    @SerialName(RequestFieldUsername)
    val username: String,
    @SerialName(RequestFieldPassword)
    val password: String,
    @SerialName(RequestFieldNickname)
    val nickname: String,
    @SerialName(RequestFieldBirthday)
    val birthday: String,
    @SerialName(RequestFieldQuestionA)
    val questionA: String,
    @SerialName(RequestFieldAnswerA)
    val answerA: String,
    @SerialName(RequestFieldQuestionB)
    val questionB: String,
    @SerialName(RequestFieldAnswerB)
    val answerB: String,
    @SerialName(RequestFieldQuestionC)
    val questionC: String,
    @SerialName(RequestFieldAnswerC)
    val answerC: String,
)

private const val RegisterApiPath = "auth/register"
class PicaRegisterRepository(
    username: String,
    password: String,
    nickname: String,
    birthday: String,
    questionA: String,
    answerA: String,
    questionB: String,
    answerB: String,
    questionC: String,
    answerC: String,
) {

    val repositoryFlow = flow {
        val requestBody = RequestBody(
            username = username,
            password = password,
            nickname = nickname,
            birthday = birthday,
            questionA = questionA,
            answerA = answerA,
            questionB = questionB,
            answerB = answerB,
            questionC = questionC,
            answerC = answerC,
        )
        val response = PicaRepository.post(RegisterApiPath, requestBody)

        val result = when (response.code) {
            RequestSuccess -> {
                PicaRegisterRepositoryResult.Success
            }
            // RequestBadRequest -> {
            //     PicaRegisterRepositoryResult.Error(
            //         response.deserialize<PicaRepositoryErrorResponse>()
            //             ?.error
            //     )
            // }
            else -> {
                PicaRegisterRepositoryResult.Error(
                    response.deserialize<PicaRepositoryErrorResponse>()
                        ?.error
                )
            }
        }

        emit(result)
    }

}
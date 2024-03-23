package me.ks.chan.pica.plus.repository.pica.auth.sign_in

import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryResponseDataBody
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryResponseErrorBody
import me.ks.chan.pica.plus.util.okhttp.RequestSuccess

private const val RequestFieldUsername = "email"
private const val RequestFieldPassword = "password"
@Serializable
private data class RequestBody(
    @SerialName(RequestFieldUsername)
    val username: String,
    @SerialName(RequestFieldPassword)
    val password: String,
)

private const val ResponseDataFieldToken = "token"
@Serializable
private class ResponseBodyData(
    @SerialName(ResponseDataFieldToken)
    val token: String
)

@Serializable
private class ResponseBody(
    override val code: Int,
    override val message: String,
    override val data: ResponseBodyData
): PicaRepositoryResponseDataBody<ResponseBodyData>()

private const val SignInApiPath = "auth/sign-in"
class PicaSignInRepository(
    username: String, password: String,
) {

    val repositoryRequest = flow {
        val response = PicaRepository.post(
            SignInApiPath,
            RequestBody(username, password)
        )

        val result = when (response.code) {
            RequestSuccess -> {
                val responseDataBody: ResponseBody? = response.body
                    ?.string()
                    ?.let(Json::decodeFromString)

                responseDataBody?.data
                    ?.token
                    ?.let(PicaSignInRepositoryResult::Success)
                    ?: PicaSignInRepositoryResult.Error(null)
            }
            else -> {
                val responseErrorBody: PicaRepositoryResponseErrorBody? = response.body
                    ?.string()
                    ?.let(Json::decodeFromString)

                responseErrorBody?.error
                    .let(PicaSignInRepositoryResult::Error)
            }
        }

        emit(result)
    }

}
package me.ks.chan.pica.plus.repository.pica.auth.login

import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import me.ks.chan.pica.plus.repository.pica.PicaDataResponse
import me.ks.chan.pica.plus.repository.pica.PicaErrorResponse
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.auth.login.LoginRepository.Result
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

@Serializable
private data class RequestBody(
    @SerialName("email")
    val username: String,
    @SerialName("password")
    val password: String,
)

@Serializable
private data class ResponseBody(
    override val code: Int,
    override val message: String,
    override val data: Data,
): PicaDataResponse<ResponseBody.Data>() {
    @Serializable
    class Data(@SerialName("token") val token: String)
}

private interface LoginApi {

    @POST("auth/sign-in")
    suspend fun login(
        @Body
        requestBody: RequestBody,
    ): Response<ResponseBody>
}

class LoginRepository(
    username: String,
    password: String,
): PicaRepository() {

    sealed interface Result {
        data class Success(val token: String): Result

        sealed interface Error: Result {
            data object InvalidCredentials: Error
            data class UnknownErrorCode(
                val code: Int, val error: String, val detail: String,
            ): Error

            data class Unknown(val code: Int, val response: String?): Error
        }
    }

    private inline val loginApi: LoginApi
        get() = repositoryApi()

    private suspend fun login(
        username: String, password: String,
    ): Response<ResponseBody> = let {
        val requestBody = RequestBody(username, password)
        loginApi.login(requestBody)
    }

    val flow = flow {
        val response = login(username, password)
        val result = when {
            response.isSuccessful -> {
                response.handleSuccessResponse()
            }
            else -> {
                response.handleErrorResponse()
            }
        }

        emit(result)
    }

}

private fun Response<ResponseBody>.handleSuccessResponse(): Result =
    body()?.data?.token
        ?.let(Result::Success)
        ?: Result.Error.Unknown(code(), null)

private fun Response<ResponseBody>.handleErrorResponse(): Result.Error = let {
    val errorResponseBody = errorBody()?.string()
    when {
        errorResponseBody != null -> {
            handleErrorResponseBody(errorResponseBody)
        }
        else -> {
            Result.Error.Unknown(code(), null)
        }
    }
}

private fun Response<ResponseBody>.handleErrorResponseBody(
    errorBody: String,
): Result.Error =
    runCatching { Json.decodeFromString<PicaErrorResponse>(errorBody) }
        .getOrNull()
        ?.let { errorResponse ->
            when (errorResponse.error) {
                "1002" -> {
                    Result.Error.InvalidCredentials
                }

                else -> {
                    Result.Error.UnknownErrorCode(
                        code(),
                        errorResponse.error,
                        errorResponse.message
                    )
                }
            }
        }
        ?: Result.Error.Unknown(code(), errorBody)
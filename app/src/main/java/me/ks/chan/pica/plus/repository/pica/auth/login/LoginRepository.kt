package me.ks.chan.pica.plus.repository.pica.auth.login

import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import me.ks.chan.pica.plus.repository.pica.PicaDataResponse
import me.ks.chan.pica.plus.repository.pica.PicaErrorResponse
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.auth.login.invoke
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

private typealias LoginResponse = Response<ResponseBody>

private interface LoginApi {
    @POST("auth/sign-in")
    suspend fun login(@Body requestBody: RequestBody): LoginResponse
}

private suspend operator fun LoginApi.invoke(username: String, password: String): LoginResponse =
    login(RequestBody(username, password))

class LoginRepository(
    username: String,
    password: String,
): PicaRepository() {

    sealed interface Result {

        data class Success(val token: String): Result

        sealed interface Failure: Result {
            data object InvalidCredentials: Failure
            data class UnknownFailureCode(val code: Int, val error: String, val detail: String): Failure
            data class Unknown(val code: Int, val response: String?): Failure
        }

    }

    private inline val login: LoginApi
        get() = repositoryApi()

    val flow = flow {
        val response = login(username, password)
        val result = when {
            response.isSuccessful -> { response.handleSuccessResponse() }
            else -> { response.handleErrorResponse() }
        }

        emit(result)
    }

}

private fun LoginResponse.handleSuccessResponse(): Result {
    return when (val token = body()?.data?.token) {
        null -> { Result.Failure.Unknown(code(), null) }
        else -> { Result.Success(token) }
    }
}

private fun LoginResponse.handleErrorResponse(): Result.Failure {
    return when (val errorResponseBody = errorBody()?.string()) {
        null -> { Result.Failure.Unknown(code(), null) }
        else -> { handleErrorResponseBody(errorResponseBody) }
    }
}

private fun LoginResponse.handleErrorResponseBody(errorBody: String): Result.Failure {
    val errorResponse = runCatching { Json.decodeFromString<PicaErrorResponse>(errorBody) }
        .getOrNull()
    return when (errorResponse?.error) {
        "1002" -> { Result.Failure.InvalidCredentials }
        null -> { Result.Failure.Unknown(code(), errorBody) }
        else -> {
            Result.Failure.UnknownFailureCode(
                code(), errorResponse.error, errorResponse.message
            )
        }
    }
}
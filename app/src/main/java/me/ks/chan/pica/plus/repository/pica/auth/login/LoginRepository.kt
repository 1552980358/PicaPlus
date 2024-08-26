package me.ks.chan.pica.plus.repository.pica.auth.login

import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ks.chan.pica.plus.repository.pica.PicaDataResponse
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.auth.login.invoke
import me.ks.chan.pica.plus.repository.pica.tryAsPicaErrorResponse
import me.ks.chan.pica.plus.repository.pica.auth.login.LoginRepository.Result as LoginResult
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
            else -> { response.handleFailureResponse() }
        }

        emit(result)
    }

}

private fun LoginResponse.handleSuccessResponse(): LoginResult {
    return when (val token = body()?.data?.token) {
        null -> { LoginResult.Failure.Unknown(code(), null) }
        else -> { LoginResult.Success(token) }
    }
}

private fun LoginResponse.handleFailureResponse(): LoginResult {
    return (
        errorBody()?.string()?.handleErrorResponse()
            ?: LoginResult.Failure.Unknown(code(), null)
    )
}

context(LoginResponse)
private fun String.handleErrorResponse(): LoginResult {
    val errorResponse = tryAsPicaErrorResponse
    return when (errorResponse?.error) {
        "1002" -> { LoginResult.Failure.InvalidCredentials }
        null -> { LoginResult.Failure.Unknown(code(), this) }
        else -> {
            LoginResult.Failure.UnknownFailureCode(
                code(), errorResponse.error, errorResponse.message
            )
        }
    }
}
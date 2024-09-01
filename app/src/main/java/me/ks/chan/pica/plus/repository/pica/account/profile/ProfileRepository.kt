package me.ks.chan.pica.plus.repository.pica.account.profile

import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ks.chan.pica.plus.repository.pica.account.profile.ProfileRepository.Result as ProfileResult
import me.ks.chan.pica.plus.repository.pica.PicaDataResponse
import me.ks.chan.pica.plus.repository.pica.PicaImage
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import retrofit2.Response
import retrofit2.http.GET

@Serializable
private data class ResponseBody(
    override val code: Int,
    override val message: String,
    override val data: Data,
): PicaDataResponse<ResponseBody.Data>() {

    @Serializable
    data class Data(val user: User)

    @Serializable
    data class User(
        @SerialName("_id")
        val id: String,
        @SerialName("email")
        val username: String,
        @SerialName("avatar")
        val avatar: PicaImage,
        @SerialName("name")
        val nickname: String,
        @SerialName("birthday")
        val birthday: String,
        @SerialName("gender")
        val gender: Char,
        @SerialName("slogan")
        val slogan: String,
        @SerialName("title")
        val title: String,
        @SerialName("characters")
        val characterList: List<String>,
        @SerialName("exp")
        val experience: Int,
        @SerialName("level")
        val level: Int,
        @SerialName("isPunched")
        val punched: Boolean,
        @SerialName("created_at")
        val register: String,
        @SerialName("verified")
        val verified: Boolean,
    )

}

private typealias ProfileResponse = Response<ResponseBody>

private interface ProfileApi {
    @GET("users/profile")
    suspend fun fetch(): ProfileResponse
}

private suspend operator fun ProfileApi.invoke(): ProfileResponse =
    fetch()

data object ProfileRepository: PicaRepository() {

    sealed interface Result {

        data class Success(
            val id: String,
            val username: String,
            val avatar: PicaImage,
            val nickname: String,
            val birthday: String,
            val gender: Char,
            val slogan: String,
            val title: String,
            val characterList: List<String>,
            val experience: Int,
            val level: Int,
            val punched: Boolean,
            val register: String,
            val verified: Boolean,
        ): Result

        sealed interface Failure: Result {
            data object Unauthorized: Failure
            data class Unknown(val code: Int, val response: String?): Failure
        }

    }

    private inline val profile: ProfileApi
        get() = repositoryApi()

    val flow = flow {
        val result = when {
            isAuthorized.not() -> { Result.Failure.Unauthorized }
            else -> {
                with(profile()) {
                    if (isSuccessful) handleSuccessResponse() else handleFailureResponse()
                }
            }
        }
        emit(result)
    }

}

private fun ProfileResponse.handleSuccessResponse(): ProfileResult {
    return body()?.data?.user?.asSuccessResult
        // This should never happen, otherwise, it should be Retrofit's bug
        ?: ProfileResult.Failure.Unknown(code(), null)
}

private inline val ResponseBody.User.asSuccessResult: ProfileResult
    get() = ProfileResult.Success(
        id = id,
        username = username,
        avatar = avatar,
        nickname = nickname,
        birthday = birthday,
        gender = gender,
        slogan = slogan,
        title = title,
        characterList = characterList,
        experience = experience,
        level = level,
        punched = punched,
        register = register,
        verified = verified,
    )

private fun ProfileResponse.handleFailureResponse(): ProfileResult {
    return when (val code = code()) {
        401 -> { ProfileResult.Failure.Unauthorized }
        else -> { ProfileResult.Failure.Unknown(code, errorBody()?.string()) }
    }
}
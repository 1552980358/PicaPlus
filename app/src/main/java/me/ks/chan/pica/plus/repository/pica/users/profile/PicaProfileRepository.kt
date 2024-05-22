package me.ks.chan.pica.plus.repository.pica.users.profile

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ks.chan.pica.plus.repository.pica.field.PicaImage
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryDataResponse
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryErrorResponse
import me.ks.chan.pica.plus.util.okhttp.RequestSuccess
import me.ks.chan.pica.plus.util.okhttp.deserialize
import me.ks.chan.pica.plus.repository.pica.users.profile.PicaProfileRepositoryResult.Error
import me.ks.chan.pica.plus.repository.pica.users.profile.PicaProfileRepositoryResult.Success
import me.ks.chan.pica.plus.util.okhttp.RequestUnauthorized

private const val FieldUser = "user"

private const val UserFieldId = "_id"
private const val UserFieldUsername = "email"
private const val UserFieldAvatar = "avatar"
private const val UserFieldNickname = "name"
private const val UserFieldBirthday = "birthday"
private const val UserFieldGender = "gender"
private const val UserFieldSlogan = "slogan"
private const val UserFieldExperience = "exp"
private const val UserFieldLevel = "level"
private const val UserFieldTitle = "title"
private const val UserFieldCharacterList = "characters"
private const val UserFieldPunched = "isPunched"
private const val UserFieldRegister = "created_at"
private const val UserFieldVerified = "verified"

@Serializable
private data class Data(
    @SerialName(FieldUser)
    val user: User
) {
    @Serializable
    data class User(
        @SerialName(UserFieldId)
        val id: String,
        @SerialName(UserFieldUsername)
        val username: String,

        @SerialName(UserFieldAvatar)
        val avatar: PicaImage,
        @SerialName(UserFieldNickname)
        val nickname: String,

        @SerialName(UserFieldBirthday)
        val birthday: String,
        @SerialName(UserFieldGender)
        val gender: Char,

        @SerialName(UserFieldSlogan)
        val slogan: String,

        @SerialName(UserFieldTitle)
        val title: String,
        @SerialName(UserFieldCharacterList)
        val characterList: List<String>,

        @SerialName(UserFieldExperience)
        val experience: Int,
        @SerialName(UserFieldLevel)
        val level: Int,

        @SerialName(UserFieldPunched)
        val punched: Boolean,

        @SerialName(UserFieldRegister)
        val register: String,
        @SerialName(UserFieldVerified)
        val verified: Boolean,
    )
}

@Serializable
private data class ResponseBody(
    override val code: Int,
    override val message: String,
    override val data: Data
): PicaRepositoryDataResponse<Data>()

private const val ProfileApiPath = "users/profile"

object PicaProfileRepository {

    val repositoryFlow: Flow<PicaProfileRepositoryResult>
        get() = flow {
            val response = PicaRepository.get(ProfileApiPath)

            val result = when (response.code) {
                RequestSuccess -> {
                    response.deserialize<ResponseBody>()
                        ?.data
                        ?.user
                        ?.let(::asSuccessState)
                        ?: Error.InvalidResponse
                }
                RequestUnauthorized -> {
                    Error.Unauthorized
                }
                else -> {
                    response.deserialize<PicaRepositoryErrorResponse>()
                        ?.error
                        ?.let { Error.UnknownStatusCode }
                        ?: Error.InvalidState
                }
            }

            emit(result)
        }

}

private fun asSuccessState(user: Data.User): Success {
    return Success(
        id = user.id,
        username = user.username,
        avatar = user.avatar.url,
        nickname = user.nickname,
        birthday = user.birthday,
        gender = user.gender,
        slogan = user.slogan,
        title = user.title,
        characterList = user.characterList,
        experience = user.experience,
        level = user.level,
        punched = user.punched,
        register = user.register,
        verified = user.verified,
    )
}
package me.ks.chan.pica.plus.repository.pica.users.profile

import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ks.chan.pica.plus.repository.pica.PicaImage
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryDataResponse
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryErrorResponse
import me.ks.chan.pica.plus.util.okhttp.RequestSuccess
import me.ks.chan.pica.plus.util.okhttp.deserialize
import me.ks.chan.pica.plus.repository.pica.users.profile.PicaProfileRepositoryResult.Error
import me.ks.chan.pica.plus.repository.pica.users.profile.PicaProfileRepositoryResult.Success
import me.ks.chan.pica.plus.util.okhttp.RequestUnauthorized

private const val FieldId = "_id"
private const val FieldUsername = "email"
private const val FieldAvatar = "avatar"
private const val FieldNickname = "name"
private const val FieldBirthday = "birthday"
private const val FieldGender = "gender"
private const val FieldSlogan = "slogan"
private const val FieldExperience = "exp"
private const val FieldLevel = "level"
private const val FieldTitle = "title"
private const val FieldCharacterList = "characters"
private const val FieldPunched = "isPunched"
private const val FieldRegister = "created_at"
private const val FieldVerified = "verified"

@Serializable
private data class Data(
    @SerialName(FieldId)
    val id: String,
    @SerialName(FieldUsername)
    val username: String,

    @SerialName(FieldAvatar)
    val avatar: PicaImage,
    @SerialName(FieldNickname)
    val nickname: String,

    @SerialName(FieldBirthday)
    val birthday: String,
    @SerialName(FieldGender)
    val gender: Char,

    @SerialName(FieldSlogan)
    val slogan: String,

    @SerialName(FieldTitle)
    val title: String,
    @SerialName(FieldCharacterList)
    val characterList: List<String>,

    @SerialName(FieldExperience)
    val experience: Int,
    @SerialName(FieldLevel)
    val level: Int,

    @SerialName(FieldPunched)
    val punched: Boolean,

    @SerialName(FieldRegister)
    val register: String,
    @SerialName(FieldVerified)
    val verified: Boolean,
)

@Serializable
private data class ResponseBody(
    override val code: Int,
    override val message: String,
    override val data: Data
): PicaRepositoryDataResponse<Data>()

private const val ProfileApiPath = "users/profile"

object PicaProfileRepository {

    private fun asSuccessResult(data: Data): Success {
        return Success(
            id = data.id,
            username = data.username,
            avatar = data.avatar.url,
            nickname = data.nickname,
            birthday = data.birthday,
            gender = data.gender,
            slogan = data.slogan,
            title = data.title,
            characterList = data.characterList,
            experience = data.experience,
            level = data.level,
            punched = data.punched,
            register = data.register,
            verified = data.verified,
        )
    }

    val repositoryFlow = flow {
        val response = PicaRepository.get(ProfileApiPath)
        val result = when (response.code) {
            RequestSuccess -> {
                response.deserialize<ResponseBody>()
                    ?.data
                    ?.let(::asSuccessResult)
            }
            RequestUnauthorized -> {
                Error(Error.Type.Unauthorized)
            }
            else -> {
                response.deserialize<PicaRepositoryErrorResponse>()
                    ?.error
                    ?.let { Error(Error.Type.UnknownStatusCode) }
            }
        }
        emit(
            result ?: Error(Error.Type.EmptyResponse)
        )
    }

}
package me.ks.chan.pica.plus.repository.pica

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val FieldCode = "code"
private const val FieldMessage = "message"

@Serializable
abstract class PicaRepositoryResponseBody {
    @SerialName(FieldCode)
    abstract val code: Int
    @SerialName(FieldMessage)
    abstract val message: String
}

private const val FieldData = "data"

@Serializable
abstract class PicaRepositoryResponseDataBody<D>: PicaRepositoryResponseBody() {
    @SerialName(FieldData)
    abstract val data: D
}

private const val FieldError = "error"
private const val FieldDetail = "detail"

@Serializable
data class PicaRepositoryResponseErrorBody(
    override val code: Int,
    override val message: String,
    @SerialName(FieldError)
    val error: String,
    @SerialName(FieldDetail)
    val detail: String
): PicaRepositoryResponseBody()
package me.ks.chan.pica.plus.repository.pica

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
abstract class PicaResponse {
    @SerialName("code")
    abstract val code: Int
    @SerialName("message")
    abstract val message: String
}

@Serializable
abstract class PicaDataResponse<D>: PicaResponse() {
    @SerialName("data")
    abstract val data: D
}

@Serializable
data class PicaErrorResponse(
    override val code: Int,
    override val message: String,
    @SerialName("error")
    val error: String,
    @SerialName("detail")
    val detail: String
): PicaResponse()
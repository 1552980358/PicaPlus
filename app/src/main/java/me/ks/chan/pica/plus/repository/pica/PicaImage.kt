package me.ks.chan.pica.plus.repository.pica

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val FieldName = "originalName"
private const val FieldHost = "fileServer"
private const val FieldPath = "path"

private const val UrlStaticRoute = "/static/"

@Serializable
data class PicaImage(
    @SerialName(FieldName)
    val name: String,
    @SerialName(FieldHost)
    val host: String,
    @SerialName(FieldPath)
    val path: String,
) {

    val url: String
        get() = when {
            host.contains(UrlStaticRoute) -> host + path
            else -> host + UrlStaticRoute + path
        }

}
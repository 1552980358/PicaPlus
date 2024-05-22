package me.ks.chan.pica.plus.repository.pica.field

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val UrlStaticRoute = "/static/"
@Serializable
data class PicaImage(
    @SerialName("originalName")
    val name: String,
    @SerialName("fileServer")
    val host: String,
    @SerialName("path")
    val path: String,
) {

    val url: String
        get() = when {
            host.contains(UrlStaticRoute) -> host + path
            else -> host + UrlStaticRoute + path
        }

}
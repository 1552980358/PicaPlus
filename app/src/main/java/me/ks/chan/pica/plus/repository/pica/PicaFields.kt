package me.ks.chan.pica.plus.repository.pica

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

    private inline val urlHost: String
        get() = host.takeIf { it.contains(UrlStaticRoute) } ?: (host + UrlStaticRoute)

    val url: String
        get() = urlHost + path

}
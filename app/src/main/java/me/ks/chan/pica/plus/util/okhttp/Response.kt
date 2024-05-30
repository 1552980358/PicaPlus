package me.ks.chan.pica.plus.util.okhttp

import kotlinx.serialization.json.Json
import okhttp3.Response

inline fun <reified T> Response.deserialize(): T? {
    return body?.string()
        ?.let(Json::decodeFromString)
}

inline fun <reified T, reified R> Response.deserialize(
    crossinline catchBody: (body: String) -> Unit = {},
    crossinline transform: (T.() -> R) = { this as R },
): R? {
    return body?.string()
        ?.let { body ->
            runCatching { Json.decodeFromString<T>(body).transform() }
                .onFailure { body.let(catchBody) }
                .getOrThrow()
        }
}
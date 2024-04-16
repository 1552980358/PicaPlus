package me.ks.chan.pica.plus.util.okhttp

import kotlinx.serialization.json.Json
import okhttp3.Response

inline fun <reified T> Response.deserialize(): T? {
    return body?.string()
        ?.let(Json::decodeFromString)
}
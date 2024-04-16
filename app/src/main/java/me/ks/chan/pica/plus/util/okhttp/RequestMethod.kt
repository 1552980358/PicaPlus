package me.ks.chan.pica.plus.util.okhttp

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.ks.chan.pica.plus.util.kotlin.Blank

private const val GetRequest = "GET"
private const val PostRequest = "POST"

sealed class RequestMethod(val method: String) {

    abstract val path: String

    data class Get(override val path: String): RequestMethod(GetRequest)

    data class Post(
        override val path: String,
        val content: String
    ): RequestMethod(PostRequest)

}

inline fun <reified Body> Post(path: String, body: Body): RequestMethod.Post {
    return RequestMethod.Post(path, body.let(Json::encodeToString))
}

fun Get(path: String, queries: Map<String, String> = mapOf()): RequestMethod.Get {
    return RequestMethod.Get(path + queries.let(::toSearchQuery))
}

private const val KeyValueSeparator = '='
private const val UrlQuerySeparator = "?"
private const val QuerySeparator = "&"
private fun toSearchQuery(queries: Map<String, String>): String {
    return queries.map(::searchQueryCombine)
        .takeIf(List<String>::isNotEmpty)
        ?.joinToString(prefix = UrlQuerySeparator, separator = QuerySeparator)
        ?: Blank
}
private fun searchQueryCombine(entry: Map.Entry<String, String>): String {
    return entry.key + KeyValueSeparator + entry.value
}
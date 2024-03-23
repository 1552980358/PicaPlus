package me.ks.chan.pica.plus.util.okhttp

private const val GetRequest = "GET"
private const val PostRequest = "POST"

sealed class RequestMethod(val method: String) {

    data object Get: RequestMethod(GetRequest)

    data object Post: RequestMethod(PostRequest)

}
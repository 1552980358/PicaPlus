package me.ks.chan.pica.plus.repository.pica

import me.ks.chan.pica.plus.util.kotlin.Blank
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultBlocking
import me.ks.chan.pica.plus.util.kotlinx.coroutine.ioBlocking
import me.ks.chan.pica.plus.util.okhttp.Get
import me.ks.chan.pica.plus.util.okhttp.Post
import me.ks.chan.pica.plus.util.okhttp.RequestMethod
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import okio.IOException

private val PicaRepositoryClient by lazy {
    OkHttpClient.Builder()
        .addInterceptor(PicaRepositoryInterceptor)
        .build()
}

private const val ContentTypeJson = "application/json; charset=UTF-8"

private var picaAuthorizationState: PicaAuthorizationState =
    PicaAuthorizationState.Unauthorized

object PicaRepository {

    val isAuthorized: Boolean
        get() = picaAuthorizationState is PicaAuthorizationState.Authorized

    fun authorization(token: String? = null) {
        picaAuthorizationState = when {
            token.isNullOrBlank() -> PicaAuthorizationState.Unauthorized
            else -> PicaAuthorizationState.Authorized(token)
        }
    }

    @Throws(IOException::class)
    suspend inline fun get(path: String): Response =
        request(Get(path))

    @Throws(IOException::class)
    suspend inline fun <reified Body> post(path: String, body: Body): Response =
        request(Post(path, body))

    @Throws(IOException::class)
    suspend fun request(
        requestMethod: RequestMethod
    ): Response = ioBlocking {
        PicaRepositoryClient.newCall(buildRequest(requestMethod))
            .execute()
    }

}

private object PicaRepositoryInterceptor: Interceptor {

    private const val AUTHORIZATION = "Authorization"

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
            .let(::addAuthorizationHeader)
            .let(chain::proceed)
    }

    private fun addAuthorizationHeader(
        request: Request,
        authorizationState: PicaAuthorizationState = picaAuthorizationState
    ): Request = when (authorizationState) {
        is PicaAuthorizationState.Authorized -> {
            request.newBuilder()
                .headers(
                    request.headers
                        .newBuilder()
                        .add(AUTHORIZATION, authorizationState.token)
                        .build()
                )
                .build()
        }
        else -> { request }
    }

}

private const val PicaApiHost = "https://picaapi.picacomic.com/"
private const val PicaApiKey = "C69BAF41DA5ABD1FFEDC6D2FEA56B"
private const val PicApiPublicKey = "~d}\$Q7\$eIni=V)9\\RK/P.RM4;9[7|@/CA}b~OW!3?EV`:<>M7pddUBL5n|0/*Cn"

private val PicaApiHeadersBuilder by lazy {
    Headers.Builder()
        .add("Accept", "application/vnd.picacomic.com.v1+json")
        .add("Api-Key", PicaApiKey)
        .add("App-Build-Version", "45")
        .add("App-Channel", "3")
        .add("App-Platform", "android")
        .add("App-Uuid", "defaultUuid")
        .add("App-Version", "2.2.1.3.3.4")
        .add("Image-Quality", "original")
        .add("User-Agent", "okhttp/3.8.1")
        .build()
}

private const val HeaderTime = "Time"
private const val HeaderNonce = "Nonce"
private const val HeaderSignature = "Signature"
private suspend fun buildRequest(
    requestMethod: RequestMethod, time: String = timeStr, nonce: String = nonceStr,
): Request {
    return Request.Builder()
        .url(PicaApiHost + requestMethod.path)
        .method(requestMethod.method, buildRequestBody(requestMethod))
        .headers(PicaApiHeadersBuilder)
        .header(HeaderNonce, nonce)
        .header(HeaderTime, time)
        .header(HeaderSignature, signRequest(requestMethod, time, nonce))
        .build()
}

private suspend fun buildRequestBody(
    requestMethod: RequestMethod
): RequestBody? = defaultBlocking {
    when (requestMethod) {
        is RequestMethod.Post -> {
            requestMethod.content
                .toRequestBody(ContentTypeJson.toMediaType())
        }
        else -> { null }
    }
}

private const val UuidDivider = "-"
private val nonceStr: String
    get() = UUID.randomUUID().toString()
        .replace(UuidDivider, Blank)

private val timeStr: String
    get() = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        .toString()

@Suppress("SpellCheckingInspection")
private const val HMacSHA256 = "HmacSHA256"
private suspend fun signRequest(
    requestMethod: RequestMethod, time: String, nonce: String
): String = defaultBlocking {
    Mac.getInstance(HMacSHA256)
        .apply { init(SecretKeySpec(PicApiPublicKey.toByteArray(), HMacSHA256)) }
        .doFinal(
            (requestMethod.path + time + nonce + requestMethod.method + PicaApiKey)
                .lowercase()
                .toByteArray()
        )
        .joinToString(separator = Blank, transform = ::toHexString)
}

private const val BytesHex = "%02x"
private fun toHexString(byte: Byte): String =
    String.format(BytesHex, byte)
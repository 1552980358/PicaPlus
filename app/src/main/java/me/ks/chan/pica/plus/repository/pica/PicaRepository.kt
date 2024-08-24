package me.ks.chan.pica.plus.repository.pica

import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlinx.serialization.json.Json
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

abstract class PicaRepository {

    companion object Metadata {

        val isAuthorized: Boolean
            get() = Authorization.isAuthorized

        var token: String?
            // private: Getter visibility must be the same as property visibility
            @Deprecated("Do not access this property directly, use `isAuthorized` to check.", level = DeprecationLevel.HIDDEN)
            get() = throw IllegalAccessException("Do not access this property directly, use `isAuthorized` to check.")
            set(value) { Authorization.token = value }

    }

    protected val retrofit: Retrofit
        get() = RetrofitClient

    protected inline fun <reified T> repositoryApi(): T {
        return retrofit.create(T::class.java)
    }

}

private data object Authorization {

    sealed interface State {
        data object Unauthorized: State
        data class Authorized(val token: String): State
    }

    private var state: State = State.Unauthorized
    var token: String?
        get() = (state as? State.Authorized)?.token
        set(value) {
            state = value?.let(State::Authorized) ?: State.Unauthorized
        }

    val isAuthorized: Boolean
        get() = state is State.Authorized

    val asHeader: Array<String>
        get() = when (val state = state) {
            is State.Authorized -> { arrayOf("Authorization", state.token) }
            else -> { arrayOf() }
        }

}

private const val PicaApiHost = "https://picaapi.picacomic.com/"

private val RetrofitClient by lazy {
    Retrofit.Builder()
        .baseUrl(PicaApiHost)
        .addConverterFactory(
            Json.asConverterFactory(
                "application/json; charset=UTF-8".toMediaType()
            )
        )
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    Interceptor { chain: Interceptor.Chain ->
                        chain.request()
                            .let { request ->
                                request.newBuilder()
                                    .headers(request.PicaRequestHeaders)
                                    .build()
                            }
                            .let(chain::proceed)
                    }
                )
                .build()
        )
        .build()
}

private const val PicaApiKey = "C69BAF41DA5ABD1FFEDC6D2FEA56B"
private val PicaSignaturePublicKey by lazy {
    "~d}\$Q7\$eIni=V)9\\RK/P.RM4;9[7|@/CA}b~OW!3?EV`:<>M7pddUBL5n|0/*Cn"
        .toByteArray()
}
private inline val Request.PicaRequestHeaders: Headers
    get() {
        val guid = UUID.randomUUID().toString()
            .replace("-", "")
        val unixTimestamp = TimeUnit.MILLISECONDS
            .toSeconds(System.currentTimeMillis())
            .toString()
        val signature = sign(unixTimestamp, guid)

        return Headers.headersOf(
            "Accept", "application/vnd.picacomic.com.v1+json",
            "Api-Key", PicaApiKey,
            "App-Build-Version", "45",
            "App-Channel", "3",
            "App-Platform", "android",
            "App-Uuid", "defaultUuid",
            "App-Version", "2.2.1.3.3.4",
            *Authorization.asHeader,
            "Image-Quality", "original",
            "Nonce", guid,
            "Signature", signature,
            "Time", unixTimestamp,
            "User-Agent", "okhttp/3.8.1",
        )
    }

@Suppress("SpellCheckingInspection")
private const val HMacSHA256 = "HmacSHA256"

private fun Request.sign(
    unixTimestamp: String, guid: String
): String {
    val uri = url.toString().removePrefix(PicaApiHost)

    return Mac.getInstance(HMacSHA256)
        .apply { init(SecretKeySpec(PicaSignaturePublicKey, HMacSHA256)) }
        .doFinal(
            "$uri$unixTimestamp$guid$method$PicaApiKey".lowercase()
                .toByteArray()
        )
        .joinToString(separator = "", transform = Byte::hex)
}

private const val HexFormat = "%02x"
private inline val Byte.hex: String
    get() = String.format(HexFormat, this)
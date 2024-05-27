package me.ks.chan.pica.plus.util.okhttp

import okhttp3.Response

private val ResponseStatusList =
    ResponseStatus::class.nestedClasses
        .filter { it.isData && it.objectInstance is ResponseStatus }
        .map { it.objectInstance as ResponseStatus }

val Response.status: ResponseStatus
    get() = ResponseStatusList.find { it.code == code } ?: ResponseStatus.Unknown(code)

sealed interface ResponseStatus {

    val code: Int

    sealed interface Success: ResponseStatus
    sealed interface ClientError: ResponseStatus

    data object Ok: Success {
        override val code: Int = 200
    }

    data object BadRequest: ClientError {
        override val code: Int = 400
    }

    data object Unauthorized: ClientError {
        override val code: Int = 401
    }

    data class Unknown(override val code: Int): ResponseStatus

}
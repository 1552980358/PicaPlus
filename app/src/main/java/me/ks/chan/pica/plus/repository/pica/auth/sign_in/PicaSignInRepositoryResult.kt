package me.ks.chan.pica.plus.repository.pica.auth.sign_in

import kotlin.IllegalArgumentException

sealed class PicaSignInRepositoryResult {

    data class Success(val token: String): PicaSignInRepositoryResult()

    data class Error(val type: Type): PicaSignInRepositoryResult() {
        constructor(errorCode: String?): this(Type.fromErrorCode(errorCode))

        enum class Type(val errorCode: String?) {
            EmptyBody(null),
            InvalidCredential("1002");

            companion object {
                fun fromErrorCode(errorCode: String?): Type {
                    return when (errorCode) {
                        EmptyBody.errorCode -> EmptyBody
                        InvalidCredential.errorCode -> InvalidCredential
                        else -> throw IllegalArgumentException()
                    }
                }
            }
        }
    }

}
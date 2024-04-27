package me.ks.chan.pica.plus.repository.pica.auth.register

sealed interface PicaRegisterRepositoryResult {

    data object Success: PicaRegisterRepositoryResult

    data class Error(val type: Type): PicaRegisterRepositoryResult {

        constructor(errorCode: String?): this(
            errorCode.let(Type::fromErrorCode)
        )

        enum class Type(val errorCode: String?) {
            UsernameDuplicated("1008"),
            Unknown(null);

            companion object {
                fun fromErrorCode(errorCode: String?): Type {
                    return when (errorCode) {
                        UsernameDuplicated.errorCode -> UsernameDuplicated
                        else -> Unknown
                    }
                }
            }
        }

    }

}
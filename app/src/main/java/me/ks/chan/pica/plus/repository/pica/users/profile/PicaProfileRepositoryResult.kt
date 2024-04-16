package me.ks.chan.pica.plus.repository.pica.users.profile

sealed interface PicaProfileRepositoryResult {

    data class Success(
        val id: String,
        val username: String,
        val avatar: String,
        val nickname: String,
        val birthday: String,
        val gender: Char,
        val slogan: String,
        val title: String,
        val characterList: List<String>,
        val experience: Int,
        val level: Int,
        val punched: Boolean,
        val register: String,
        val verified: Boolean,
    ): PicaProfileRepositoryResult

    data class Error(val type: Type): PicaProfileRepositoryResult {

        enum class Type {
            EmptyResponse,
            Unauthorized,
            UnknownStatusCode,
        }

    }

}
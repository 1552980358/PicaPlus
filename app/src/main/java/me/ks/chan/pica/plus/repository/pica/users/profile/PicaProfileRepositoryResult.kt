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

    sealed interface Error: PicaProfileRepositoryResult {

        data object InvalidResponse: Error

        data object Unauthorized: Error

        data object UnknownStatusCode: Error

        data object InvalidState: Error

    }

}
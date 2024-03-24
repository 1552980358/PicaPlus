package me.ks.chan.pica.plus.repository.pica

sealed interface PicaAuthorizationState {

    data class Authorized(val token: String): PicaAuthorizationState

    data object Unauthorized: PicaAuthorizationState

}
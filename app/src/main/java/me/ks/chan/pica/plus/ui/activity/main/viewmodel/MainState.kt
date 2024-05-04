package me.ks.chan.pica.plus.ui.activity.main.viewmodel

sealed interface MainState {

    data object Loading : MainState

    sealed interface Result: MainState

    data class Token(val token: String): Result

    data object Completed: MainState

}
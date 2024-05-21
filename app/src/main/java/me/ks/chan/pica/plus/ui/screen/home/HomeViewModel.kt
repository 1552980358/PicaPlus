package me.ks.chan.pica.plus.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.ks.chan.pica.plus.ui.screen.home.model.HomeRepository
import me.ks.chan.pica.plus.ui.screen.home.viewmodel.HomeComicModel
import me.ks.chan.pica.plus.ui.screen.home.viewmodel.HomeState
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob

val homeViewModel: HomeViewModel
    @Composable get() = viewModel()

class HomeViewModel: ViewModel() {

    val state: StateFlow<HomeState>
        field = MutableStateFlow<HomeState>(HomeState.Loading)

    val comicList = mutableStateListOf<HomeComicModel>()

    init {
        collectRepository()
    }

    fun updateComicList() {
        updateState(state = HomeState.Loading)
        collectRepository()
    }

    fun refreshComicList() {
        comicList.clear()
        updateComicList()
    }

    private fun updateState(state: HomeState) {
        this.state.value = state
    }

    private fun collectRepository() {
        viewModelScope.defaultJob {
            HomeRepository.collect(
                updateState = ::repositoryUpdateState
            )
        }
    }

    private fun repositoryUpdateState(
        state: HomeState,
        finalState: HomeState = when (state) {
            is HomeState.Update -> {
                comicList += state.comicList
                HomeState.Pending
            }
            else -> { state }
        }
    ) {
        updateState(state = finalState)
    }

}
package me.ks.chan.pica.plus.ui.screen.comic

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.ks.chan.pica.plus.ui.screen.comic.model.ComicDetailRepository
import me.ks.chan.pica.plus.ui.screen.comic.model.ComicEpisodeRepository
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicDetailState
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicEpisodeState
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob

@Composable
fun comicViewModel(comic: String): ComicViewModel {
    return viewModel(
        factory = viewModelFactory {
            initializer { ComicViewModel(comic) }
        }
    )
}

class ComicViewModel(comic: String): ViewModel() {

    private val detailRepository = ComicDetailRepository(comic)
    private val episodeRepository = ComicEpisodeRepository(comic)

    val detailState: StateFlow<ComicDetailState>
        field = MutableStateFlow<ComicDetailState>(ComicDetailState.Loading)

    val episodeState: StateFlow<ComicEpisodeState>
        field = MutableStateFlow<ComicEpisodeState>(ComicEpisodeState.Loading)

    init {
        collectDetailRepository()
        collectEpisodeRepository()
    }

    private fun updateDetailState(state: ComicDetailState) {
        this.detailState.value = state
    }

    fun collectDetailRepository() {
        if (detailState.value != ComicDetailState.Loading) {
            updateDetailState(state = ComicDetailState.Loading)
        }
        viewModelScope.defaultJob {
            detailRepository.collect(
                updateState = ::updateDetailState
            )
        }
    }

    private fun updateEpisodeState(state: ComicEpisodeState) {
        this.episodeState.value = state
    }

    fun collectEpisodeRepository() {
        if (episodeState.value != ComicEpisodeState.Loading) {
            updateEpisodeState(state = ComicEpisodeState.Loading)
        }
        viewModelScope.defaultJob {
            episodeRepository.collect(
                updateEpisodeState = ::updateEpisodeState
            )
        }
    }

}
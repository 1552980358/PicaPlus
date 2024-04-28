package me.ks.chan.pica.plus.ui.screen.greeting

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.ks.chan.pica.plus.ui.screen.greeting.model.GreetingRepository
import me.ks.chan.pica.plus.ui.screen.greeting.viewmodel.GreetingState
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob

val greetingViewModel: GreetingViewModel
    @Composable
    get() = viewModel()

class GreetingViewModel: ViewModel() {

    val state: StateFlow<GreetingState>
        field = MutableStateFlow<GreetingState>(GreetingState.Loading)

    init {
        viewModelScope.defaultJob {
            GreetingRepository.collect(::updateState)
        }
    }

    private fun updateState(state: GreetingState) {
        this.state.value = state
    }

}
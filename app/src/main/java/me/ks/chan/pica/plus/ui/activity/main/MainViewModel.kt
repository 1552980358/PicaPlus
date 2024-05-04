package me.ks.chan.pica.plus.ui.activity.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.ks.chan.pica.plus.ui.activity.main.model.MainRepository
import me.ks.chan.pica.plus.ui.activity.main.viewmodel.MainState
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob

class MainViewModel: ViewModel() {

    val state: StateFlow<MainState>
        field = MutableStateFlow<MainState>(MainState.Loading)

    private fun updateState(state: MainState) {
        this.state.value = state
    }

    fun splashSetup(context: Context) {
        viewModelScope.defaultJob {
            MainRepository.collect(context, ::updateState)
        }
    }

}
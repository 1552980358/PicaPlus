package me.ks.chan.pica.plus.ui.screen.categroy

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.ks.chan.pica.plus.ui.screen.categroy.model.CategoryRepository
import me.ks.chan.pica.plus.ui.screen.categroy.viewmodel.CategoryState
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob

val categoryViewModel: CategoryViewModel
    @Composable get() = viewModel()

class CategoryViewModel: ViewModel() {

    val state: StateFlow<CategoryState>
        field = MutableStateFlow<CategoryState>(CategoryState.Loading)

    init {
        requestCategories()
    }

    private fun updateState(state: CategoryState) {
        this.state.value = state
    }

    private fun requestCategories() {
        viewModelScope.defaultJob {
            CategoryRepository.collect(
                updateState = ::updateState
            )
        }
    }

}
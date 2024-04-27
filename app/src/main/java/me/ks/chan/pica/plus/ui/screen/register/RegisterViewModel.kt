package me.ks.chan.pica.plus.ui.screen.register

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.ks.chan.pica.plus.ui.screen.register.model.RegisterRepository
import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterFields
import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterState
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob

val registerViewModel: RegisterViewModel
    @Composable
    get() = viewModel()

class RegisterViewModel: ViewModel() {

    // Early access magic
    val fields: StateFlow<RegisterFields>
        field = MutableStateFlow(RegisterFields())

    // private val _fields = MutableStateFlow(RegisterFields())
    // val fields: StateFlow<RegisterFields> = _fields.asStateFlow()

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Pending)
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun updateState(state: RegisterState) {
        _state.value = state
    }

    fun updateFields(fields: RegisterFields) {
        // _fields.value = fields
        this.fields.value = fields
    }

    fun registerAccount() {
        if (state.value.submittable) {
            updateState(RegisterState.Loading)

            viewModelScope.defaultJob {
                RegisterRepository.collect(fields.value, ::updateState)
            }
        }
    }

}
package me.ks.chan.pica.plus.ui.screen.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import me.ks.chan.pica.plus.storage.protobuf.AccountStore
import me.ks.chan.pica.plus.storage.protobuf.AddressProto.Account
import me.ks.chan.pica.plus.ui.screen.sign_in.model.SignInRepository
import me.ks.chan.pica.plus.ui.screen.sign_in.viewmodel.SignInFields
import me.ks.chan.pica.plus.ui.screen.sign_in.viewmodel.SignInState
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob

val signInViewModel: SignInViewModel
    @Composable get() {
        val context = LocalContext.current
        return viewModel(
            factory = viewModelFactory {
                initializer {
                    SignInViewModel(context.AccountStore)
                }
            }
        )
    }

class SignInViewModel(accountStore: DataStore<Account>): ViewModel() {

    val state: StateFlow<SignInState>
        field = MutableStateFlow<SignInState>(SignInState.Pending)

    val fields: StateFlow<SignInFields>
        field = MutableStateFlow(SignInFields())

    init {
        viewModelScope.defaultJob {
            accountStore.data
                .first()
                .let(::SignInFields)
                .let(::updateFields)
        }
    }

    fun updateFields(fields: SignInFields) {
        this.fields.value = fields
    }

    private fun updateState(state: SignInState) {
        this.state.value = state
    }

    fun signInAccount() {
        if (state.value.submittable) {
            updateState(SignInState.Loading)

            viewModelScope.defaultJob {
                SignInRepository.collect(fields.value, ::updateState)
            }
        }
    }

}
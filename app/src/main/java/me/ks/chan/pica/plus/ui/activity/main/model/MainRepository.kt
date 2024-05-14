package me.ks.chan.pica.plus.ui.activity.main.model

import android.content.Context
import kotlinx.coroutines.flow.catch
import me.ks.chan.pica.plus.storage.protobuf.AccountStore
import me.ks.chan.pica.plus.storage.protobuf.AddressProto.Account
import me.ks.chan.pica.plus.ui.activity.main.viewmodel.MainState

object MainRepository {

    suspend fun collect(
        context: Context,
        updateState: (MainState) -> Unit
    ) {
        context.AccountStore
            .data
            .catch {
                updateState(MainState.Completed)
            }
            .collect { account ->
                account.let(::collectAsState)
                    .let(updateState)
            }
    }

}

private fun collectAsState(account: Account): MainState {
    return when {
        account.token.isNullOrBlank() -> { MainState.Completed }
        else -> { MainState.Token(account.token) }
    }
}
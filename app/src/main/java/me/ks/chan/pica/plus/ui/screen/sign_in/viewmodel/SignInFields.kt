package me.ks.chan.pica.plus.ui.screen.sign_in.viewmodel

import me.ks.chan.pica.plus.storage.protobuf.AddressProto.Account
import me.ks.chan.pica.plus.util.kotlin.Blank

data class SignInFields(
    val username: String = Blank,
    val password: String = Blank,
) {

    constructor(account: Account): this(
        account.username ?: Blank, account.password ?: Blank
    )

}
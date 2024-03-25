package me.ks.chan.pica.plus.storage.protobuf

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import me.ks.chan.pica.plus.storage.protobuf.AddressProto.Account
import java.io.InputStream
import java.io.OutputStream

private const val FileName = "account.protobuf"

private object AccountSerializer: Serializer<Account> {

    override val defaultValue: Account
        get() = Account.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Account {
        return Account.parseFrom(input)
    }

    override suspend fun writeTo(t: Account, output: OutputStream) {
        t.writeTo(output)
    }

}

val Context.AccountStore: DataStore<Account> by dataStore(
    fileName = FileName, serializer = AccountSerializer
)
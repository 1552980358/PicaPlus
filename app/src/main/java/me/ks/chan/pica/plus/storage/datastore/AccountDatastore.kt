package me.ks.chan.pica.plus.storage.datastore

import android.content.Context
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import java.io.InputStream
import java.io.OutputStream
import me.ks.chan.pica.plus.storage.proto.AccountProto

private data object AccountDatastoreSerializer: Serializer<AccountProto> {
    override val defaultValue: AccountProto
        get() = AccountProto.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): AccountProto =
        AccountProto.parseFrom(input)
    override suspend fun writeTo(t: AccountProto, output: OutputStream) =
        t.writeTo(output)
}

private const val Filename = "account.proto"

val Context.accountDatastore by dataStore(
    fileName = Filename, serializer = AccountDatastoreSerializer,
)
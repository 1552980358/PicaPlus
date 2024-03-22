package me.ks.chan.pica.plus.util.kotlinx.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

val Main: CoroutineDispatcher
    inline get() = Dispatchers.Main

val Default: CoroutineDispatcher
    inline get() = Dispatchers.Default

val Io: CoroutineDispatcher
    inline get() = Dispatchers.IO

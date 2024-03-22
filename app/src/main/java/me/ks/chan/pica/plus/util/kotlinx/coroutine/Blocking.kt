package me.ks.chan.pica.plus.util.kotlinx.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

suspend inline fun <R> defaultBlocking(
    noinline block: suspend CoroutineScope.() -> R
): R = withContext(Default, block)

suspend inline fun <R> ioBlocking(
    noinline block: suspend CoroutineScope.() -> R
): R = withContext(Io, block)

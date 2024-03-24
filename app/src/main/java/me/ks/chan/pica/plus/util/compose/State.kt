package me.ks.chan.pica.plus.util.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

val TrueState: MutableState<Boolean>
    get() = mutableStateOf(true)

val FalseState: MutableState<Boolean>
    get() = mutableStateOf(false)
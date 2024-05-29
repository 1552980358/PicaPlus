package me.ks.chan.pica.plus.util.androidx.compose

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

val TrueState: MutableState<Boolean>
    get() = mutableStateOf(true)

val FalseState: MutableState<Boolean>
    get() = mutableStateOf(false)

val ZeroState: MutableIntState
    get() = mutableIntStateOf(0)
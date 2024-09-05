package me.ks.chan.pica.plus.ui.composed.shimmer

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize

sealed interface ShimmerSize {

    data object Auto: ShimmerSize

    data class Minimum(
        val width: Dp = Dp.Unspecified,
        val height: Dp = Dp.Unspecified,
    ): ShimmerSize

    data class Exactly(
        val width: DpSize,
        val height: DpSize,
    ): ShimmerSize

}

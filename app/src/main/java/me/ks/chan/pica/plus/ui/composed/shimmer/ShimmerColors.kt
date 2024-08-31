package me.ks.chan.pica.plus.ui.composed.shimmer

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse

data class ShimmerColors(
    val base: Color = Color.Unspecified,
    val shimmer: Color = Color.Unspecified,
)

inline val ShimmerColors.composed: ShimmerColors
    @Composable get() = ShimmerColors(
        base = base.takeOrElse { MaterialTheme.colorScheme.surface },
        shimmer = shimmer.takeOrElse { MaterialTheme.colorScheme.surfaceVariant },
    )
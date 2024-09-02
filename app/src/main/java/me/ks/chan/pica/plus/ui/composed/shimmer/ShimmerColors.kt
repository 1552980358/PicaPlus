package me.ks.chan.pica.plus.ui.composed.shimmer

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse

data class ShimmerColors(
    val placeholder: Color = Color.Unspecified,
    val shimmer: Color = Color.Unspecified,
)

inline val ShimmerColors.composed: ShimmerColors
    @Composable get() = ShimmerColors(
        placeholder = placeholder.takeOrElse { MaterialTheme.colorScheme.surface },
        shimmer = shimmer.takeOrElse { MaterialTheme.colorScheme.surfaceVariant },
    )

fun ShimmerColors.color(targetState: Boolean): Color {
    return when {
        targetState -> { shimmer }
        else -> { placeholder }
    }
}

package me.ks.chan.pica.plus.ui.composed.shimmer

import androidx.compose.ui.graphics.Color

data object ShimmerDefaults {

    fun colors(
        placeholder: Color = Color.Unspecified,
        shimmer: Color = Color.Unspecified,
    ): ShimmerColors {
        return ShimmerColors(
            placeholder = placeholder,
            shimmer = shimmer,
        )
    }

    fun durations(
        startDelay: Long = 0,
        placeholderLeaving: Int = 400,
        shimmerLeaving: Int = placeholderLeaving,
        placeholderAwaiting: Long = 0,
        shimmerAwaiting: Long = placeholderAwaiting,
    ): ShimmerDurations {
        return ShimmerDurations(
            startDelay = startDelay,
            placeholderLeaving = placeholderLeaving,
            shimmerLeaving = shimmerLeaving,
            placeholderAwaiting = placeholderAwaiting,
            shimmerAwaiting = shimmerAwaiting,
        )
    }

}
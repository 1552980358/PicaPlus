package me.ks.chan.pica.plus.ui.composed.shimmer

import androidx.compose.ui.graphics.Color

data object ShimmerDefaults {

    fun colors(
        baseColor: Color = Color.Unspecified,
        shimmerColor: Color = Color.Unspecified,
    ): ShimmerColors {
        return ShimmerColors(
            base = baseColor,
            shimmer = shimmerColor,
        )
    }

    fun durations(
        switching: Int = 400,
        launch: Long = 0,
        baseColor: Long = 0,
        shimmerColor: Long = baseColor,
    ): ShimmerDurations {
        return ShimmerDurations(
            switching = switching,
            launch = launch,
            base = baseColor,
            shimmer = shimmerColor,
        )
    }

}
package me.ks.chan.pica.plus.ui.composed.shimmer

data class ShimmerDurations(
    val startDelay: Long = 0,
    val placeholderLeaving: Int = 400,
    val shimmerLeaving: Int = placeholderLeaving,
    val placeholderAwaiting: Long = 0,
    val shimmerAwaiting: Long = placeholderAwaiting,
)

fun ShimmerDurations.leaving(targetState: Boolean): Int {
    return when {
        targetState -> { placeholderLeaving }
        else -> { shimmerLeaving }
    }
}

fun ShimmerDurations.awaiting(targetState: Boolean): Long {
    return when {
        targetState -> { placeholderAwaiting }
        else -> { shimmerAwaiting }
    }
}
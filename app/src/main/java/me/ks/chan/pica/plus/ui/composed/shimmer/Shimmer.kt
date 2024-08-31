package me.ks.chan.pica.plus.ui.composed.shimmer

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.delay
import me.ks.chan.pica.plus.resources.dimen.Sizing_12

fun Modifier.shimmer(
    enabled: Boolean = true,
    colors: ShimmerColors = ShimmerDefaults.colors(),
    durations: ShimmerDurations = ShimmerDefaults.durations(),
    cornerRadiusDp: Dp = Sizing_12,
    easing: (Boolean) -> Easing = { EaseInOutSine },
): Modifier = composed {
    val launchShimmeringState = remember { mutableStateOf(false) }
    var launchShimmering by launchShimmeringState

    LaunchedEffect(key1 = enabled) {
        if (enabled) {
            if (durations.launch > 0) {
                delay(durations.launch)
            }
            launchShimmering = true
        }
    }

    val shimmerColors = colors.composed

    val density = LocalDensity.current
    val cornerRadius = remember(key1 = cornerRadiusDp) {
        CornerRadius(with(density) { cornerRadiusDp.toPx() })
    }

    var currentColor = remember { shimmerColors.base }

    val color = when {
        enabled.not() && launchShimmering -> {
            shimmerFinishingColor(
                currentColor = currentColor,
                shimmerColors = shimmerColors,
                shimmerDurations = durations,
                launchShimmeringState = launchShimmeringState,
                easing = easing,
            )
        }
        enabled && launchShimmering -> {
            shimmerColor(
                updateCurrentColor = { currentColor = it },
                shimmerColors = shimmerColors,
                shimmerDurations = durations,
                easing = easing,
            )
        }
        enabled /* && launchShimmering.not() */ -> { shimmerColors.base }
        else -> { Color.Unspecified }
    }

    this then ShimmerElement(enabled, color, cornerRadius)
}

@Composable
private fun shimmerFinishingColor(
    currentColor: Color,
    shimmerColors: ShimmerColors,
    shimmerDurations: ShimmerDurations,
    launchShimmeringState: MutableState<Boolean>,
    easing: (Boolean) -> Easing,
): Color {
    var launchShimmering by launchShimmeringState

    var resetColor by remember { mutableStateOf(true) }
    if (resetColor) {
        val animatable = remember { Animatable(currentColor) }
        LaunchedEffect(key1 = Unit) {
            animatable.animateTo(
                shimmerColors.base,
                tween(
                    durationMillis = shimmerDurations.switching,
                    easing = easing(false),
                )
            )
            resetColor = false
        }
    }

    val color by when {
        resetColor -> {
            val animatable = remember { Animatable(currentColor) }
            LaunchedEffect(key1 = Unit) {
                animatable.animateTo(shimmerColors.base)
                resetColor = false
            }
            animatable.asState()
        }
        else -> {
            val transitionState = remember { MutableTransitionState(true) }
            val transition = rememberTransition(transitionState = transitionState)

            LaunchedEffect(key1 = transitionState.isIdle) {
                if (transitionState.isIdle) {
                    when {
                        transitionState.currentState -> {
                            // Color reset, switch to shimmering color for switching to content
                            transitionState.targetState = false
                        }
                        else -> {
                            // Finished, stop shimmering
                            launchShimmering = false
                        }
                    }
                }
            }

            transition.animateColor(
                transitionSpec = {
                    tween(durationMillis = shimmerDurations.switching, easing = easing(targetState))
                },
                targetValueByState = { finishing ->
                    with(shimmerColors) { if (finishing) base else shimmer }
                },
                label = "Ui.Shimmer.Finishing",
            )
        }
    }

    return color
}

@Composable
private fun shimmerColor(
    updateCurrentColor: (Color) -> Unit,
    shimmerColors: ShimmerColors,
    shimmerDurations: ShimmerDurations,
    easing: (Boolean) -> Easing,
): Color {
    val transitionState = remember { MutableTransitionState(true) }

    val transition = rememberTransition(transitionState = transitionState)
    val color by transition.animateColor(
        transitionSpec = {
            tween(durationMillis = shimmerDurations.switching, easing = easing(targetState))
        },
        targetValueByState = { switching ->
            with(shimmerColors) { if (switching) shimmer else base }
        },
        label = "Ui.Shimmer.Loading",
    )

    LaunchedEffect(key1 = transitionState.isIdle) {
        if (transitionState.isIdle) {
            delay(
                with (shimmerDurations) { if (transitionState.targetState) shimmer else base }
            )
            transitionState.targetState = !transitionState.currentState
        }
    }

    updateCurrentColor(color)
    return color
}

private class ShimmerElement(
    val enable: Boolean,
    val color: Color,
    val cornerRadius: CornerRadius,
): ModifierNodeElement<ShimmerNode>() {

    override fun create(): ShimmerNode = ShimmerNode(enable, color, cornerRadius)

    override fun update(node: ShimmerNode) {
        node.drawShimmer = enable
        node.currentColor = color
        node.cornerRadius = cornerRadius
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return other is ShimmerElement &&
               other.enable == enable &&
               other.color.value == color.value &&
               other.cornerRadius.toString() == cornerRadius.toString()
    }

    override fun hashCode(): Int {
        return "$enable$color$cornerRadius".hashCode()
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "Shimmer"
        properties["enable"] = enable
        properties["color"] = color
        properties["cornerRadius"] = cornerRadius
    }

}

private class ShimmerNode(
    var drawShimmer: Boolean,
    var currentColor: Color,
    var cornerRadius: CornerRadius,
): Modifier.Node(), DrawModifierNode {
    override fun ContentDrawScope.draw() {
        when {
            drawShimmer -> {
                drawRoundRect(color = currentColor, cornerRadius = cornerRadius)
            }
            else -> { drawContent() }
        }
    }
}
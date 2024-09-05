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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.isSpecified
import kotlinx.coroutines.delay
import me.ks.chan.pica.plus.resources.dimen.Sizing_12
import kotlin.math.max

fun Modifier.shimmer(
    enabled: Boolean = true,
    size: ShimmerSize = ShimmerSize.Auto,
    colors: ShimmerColors = ShimmerDefaults.colors(),
    durations: ShimmerDurations = ShimmerDefaults.durations(),
    cornerRadiusDp: Dp = Sizing_12,
    easing: (Boolean) -> Easing = { EaseInOutSine },
): Modifier = composed {
    val launchShimmeringState = remember { mutableStateOf(false) }
    var launchShimmering by launchShimmeringState

    LaunchedEffect(key1 = enabled) {
        if (enabled) {
            if (durations.startDelay > 0) {
                delay(durations.startDelay)
            }
            launchShimmering = true
        }
    }

    val shimmerColors = colors.composed

    val density = LocalDensity.current
    val cornerRadius = remember(key1 = cornerRadiusDp) {
        CornerRadius(with(density) { cornerRadiusDp.toPx() })
    }

    var currentColor = remember { shimmerColors.placeholder }

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
        enabled /* && launchShimmering.not() */ -> { shimmerColors.placeholder }
        else -> { Color.Unspecified }
    }

    this then ShimmerElement(enabled, color, size, cornerRadius)
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

    val color by when {
        resetColor -> {
            val animatable = remember { Animatable(currentColor) }
            LaunchedEffect(key1 = Unit) {
                animatable.animateTo(
                    shimmerColors.placeholder,
                    tween(
                        durationMillis = shimmerDurations.placeholderLeaving,
                        easing = easing(false),
                    )
                )
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
                    tween(
                        durationMillis = shimmerDurations.leaving(targetState),
                        easing = easing(targetState)
                    )
                },
                targetValueByState = { finishing -> shimmerColors.color(finishing) },
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
            tween(
                durationMillis = shimmerDurations.leaving(targetState),
                easing = easing(targetState),
            )
        },
        targetValueByState = { targetValue -> shimmerColors.color(targetValue) },
        label = "Ui.Shimmer.Loading",
    )

    LaunchedEffect(key1 = transitionState.isIdle) {
        if (transitionState.isIdle) {
            delay(shimmerDurations.awaiting(transitionState.targetState))
            transitionState.targetState = !transitionState.currentState
        }
    }

    updateCurrentColor(color)
    return color
}

private class ShimmerElement(
    val enabled: Boolean,
    val color: Color,
    val size: ShimmerSize,
    val cornerRadius: CornerRadius,
): ModifierNodeElement<ShimmerNode>() {

    override fun create() = ShimmerNode(
        enabled = enabled,
        color = color,
        size = size,
        cornerRadius = cornerRadius,
    )

    override fun update(node: ShimmerNode) {
        node.enabled = enabled
        node.color = color
        node.size = size
        node.cornerRadius = cornerRadius
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return other is ShimmerElement &&
               other.enabled == enabled &&
               other.color.value == color.value &&
               other.cornerRadius.toString() == cornerRadius.toString()
    }

    override fun hashCode(): Int {
        return "$enabled,$color,$size,$cornerRadius".hashCode()
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "Shimmer"
        properties["enabled"] = enabled
        properties["size"] = size
        properties["color"] = color
        properties["cornerRadius"] = cornerRadius
    }

}

private class ShimmerNode(
    var enabled: Boolean,
    var color: Color,
    var size: ShimmerSize,
    var cornerRadius: CornerRadius,
): Modifier.Node(), LayoutModifierNode, DrawModifierNode {

    private var roundRectSize = Size(0F, 0F)

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)

        val size = size
        return when {
            size is ShimmerSize.Minimum && enabled -> {
                val width = when {
                    size.width.isSpecified -> {
                        max(placeable.width, size.width.roundToPx())
                    }
                    else -> { placeable.width }
                }
                val height = when {
                    size.height.isSpecified -> {
                        max(placeable.height, size.height.roundToPx())
                    }
                    else -> { placeable.height }
                }

                roundRectSize = Size(width.toFloat(), height.toFloat())

                layout(width, height) { placeable.place(0, 0) }
            }
            else -> {
                if (enabled) {
                    roundRectSize = Size(
                        placeable.width.toFloat(),
                        placeable.height.toFloat(),
                    )
                }

                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            }
        }
    }

    override fun ContentDrawScope.draw() {
        when {
            enabled -> {
                drawRoundRect(
                    size = roundRectSize,
                    color = color,
                    cornerRadius = cornerRadius,
                )
            }
            else -> { drawContent() }
        }
    }

}
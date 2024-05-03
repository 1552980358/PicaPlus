package me.ks.chan.pica.plus.ui.composable.shimmer

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.theme.Corner_12

private const val Shimmer = "Shimmer"
private const val Duration = 1000

fun Modifier.shimmer(
    loading: Boolean = true,
    paddings: PaddingValues = PaddingValues(),
    initialColor: Color? = null,
    targetColor: Color? = null,
    cornerRadius: Dp = Corner_12,
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(
        size = cornerRadius
    ),
    durationMillis: Int = Duration,
) = when {
    loading -> shimmerLoading(
        paddings,
        initialColor,
        targetColor,
        roundedCornerShape,
        durationMillis,
    )
    else -> this
}

private fun Modifier.shimmerLoading(
    paddings: PaddingValues,
    initialColor: Color?,
    targetColor: Color?,
    roundedCornerShape: RoundedCornerShape,
    durationMillis: Int,
) = composed {
    val initial = initialColor ?:  MaterialTheme.colorScheme.primaryContainer
    val target = targetColor ?: MaterialTheme.colorScheme.surfaceContainer

    val transition = rememberInfiniteTransition(label = Shimmer)
    val transitionColor by transition.animateColor(
        initialValue = initial,
        targetValue = target,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = Shimmer,
    )

    padding(paddingValues = paddings)
        .background(
            color = transitionColor,
            shape = roundedCornerShape,
        )
        // Cover content
        .drawWithContent { }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ShimmerPreview() {

    Column {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .shimmer(
                    loading = true,
                    cornerRadius = 4.dp
                ),
            text = "Text Content"
        )

        Image(
            modifier = Modifier
                .padding(16.dp, 8.dp)
                .size(48.dp)
                .shimmer(loading = true),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
        )

        ListItem(
            headlineContent = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmer(loading = true),
                    text = "ItemTitle"
                )
            },
            leadingContent = {
                Image(
                    modifier = Modifier
                        .padding(16.dp, 8.dp)
                        .size(48.dp)
                        .shimmer(
                            loading = true,
                            cornerRadius = 24.dp
                        ),
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                )
            },
            supportingContent = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(.5F)
                        .shimmer(
                            loading = true,
                            paddings = PaddingValues(top = 2.dp),
                        ),
                    text = "ItemTitle"
                )
            },
        )

    }
}
package me.ks.chan.pica.plus.ui.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.Dp
import me.ks.chan.pica.plus.ui.theme.Icon_24

fun materialSymbol(
    name: String,
    viewportWidth: Float,
    viewportHeight: Float = viewportWidth,
    defaultWidth: Dp = Icon_24,
    defaultHeight: Dp = Icon_24,
    pathBuilder: PathBuilder.() -> Unit,
): ImageVector {
    return ImageVector.Builder(name, defaultWidth, defaultHeight, viewportWidth, viewportHeight)
        .path(
            fill = SolidColor(Color.Black),
            pathBuilder = pathBuilder
        )
        .build()
}
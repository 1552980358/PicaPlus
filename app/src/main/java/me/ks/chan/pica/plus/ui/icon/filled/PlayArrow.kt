package me.ks.chan.pica.plus.ui.icon.filled

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "PlayArrow"
private const val ViewPortSize = 960F
val PlayArrow by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(320F, 687F)
        lineTo(320F, 273F)
        quadTo(320F, 256F, 332F, 244.5F)
        quadTo(344F, 233F, 360F, 233F)
        quadTo(365F, 233F, 370.5F, 234.5F)
        quadTo(376F, 236F, 381F, 239F)
        lineTo(707F, 446F)
        quadTo(716F, 452F, 720.5F, 461F)
        quadTo(725F, 470F, 725F, 480F)
        quadTo(725F, 490F, 720.5F, 499F)
        quadTo(716F, 508F, 707F, 514F)
        lineTo(381F, 721F)
        quadTo(376F, 724F, 370.5F, 725.5F)
        quadTo(365F, 727F, 360F, 727F)
        quadTo(344F, 727F, 332F, 715.5F)
        quadTo(320F, 704F, 320F, 687F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayArrow() {
    Icon(imageVector = PlayArrow, contentDescription = Name)
}
package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "KeyboardArrowUp"
private const val ViewPortSize = 960F
val KeyboardArrowUp by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(480F, 432F)
        lineTo(324F, 588F)
        quadTo(313F, 599F, 296F, 599F)
        quadTo(279F, 599F, 268F, 588F)
        quadTo(257F, 577F, 257F, 560F)
        quadTo(257F, 543F, 268F, 532F)
        lineTo(452F, 348F)
        quadTo(464F, 336F, 480F, 336F)
        quadTo(496F, 336F, 508F, 348F)
        lineTo(692F, 532F)
        quadTo(703F, 543F, 703F, 560F)
        quadTo(703F, 577F, 692F, 588F)
        quadTo(681F, 599F, 664F, 599F)
        quadTo(647F, 599F, 636F, 588F)
        lineTo(480F, 432F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun KeyboardArrowUp() {
    Icon(imageVector = KeyboardArrowUp, contentDescription = Name)
}
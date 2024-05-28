package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "KeyboardArrowDown"
private const val ViewPortSize = 960F
val KeyboardArrowDown by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(480F, 599F)
        quadTo(472F, 599F, 465F, 596.5F)
        quadTo(458F, 594F, 452F, 588F)
        lineTo(268F, 404F)
        quadTo(257F, 393F, 257F, 376F)
        quadTo(257F, 359F, 268F, 348F)
        quadTo(279F, 337F, 296F, 337F)
        quadTo(313F, 337F, 324F, 348F)
        lineTo(480F, 504F)
        lineTo(636F, 348F)
        quadTo(647F, 337F, 664F, 337F)
        quadTo(681F, 337F, 692F, 348F)
        quadTo(703F, 359F, 703F, 376F)
        quadTo(703F, 393F, 692F, 404F)
        lineTo(508F, 588F)
        quadTo(502F, 594F, 495F, 596.5F)
        quadTo(488F, 599F, 480F, 599F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun KeyboardArrowDown() {
    Icon(imageVector = KeyboardArrowDown, contentDescription = Name)
}
package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Pages"
private const val ViewPortSize = 960F
val Pages by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(200F, 840F)
        quadTo(167F, 840F, 143.5F, 816.5F)
        quadTo(120F, 793F, 120F, 760F)
        lineTo(120F, 200F)
        quadTo(120F, 167F, 143.5F, 143.5F)
        quadTo(167F, 120F, 200F, 120F)
        lineTo(760F, 120F)
        quadTo(793F, 120F, 816.5F, 143.5F)
        quadTo(840F, 167F, 840F, 200F)
        lineTo(840F, 760F)
        quadTo(840F, 793F, 816.5F, 816.5F)
        quadTo(793F, 840F, 760F, 840F)
        lineTo(200F, 840F)
        close()
        moveTo(200F, 760F)
        lineTo(760F, 760F)
        quadTo(760F, 760F, 760F, 760F)
        quadTo(760F, 760F, 760F, 760F)
        lineTo(760F, 200F)
        quadTo(760F, 200F, 760F, 200F)
        quadTo(760F, 200F, 760F, 200F)
        lineTo(200F, 200F)
        quadTo(200F, 200F, 200F, 200F)
        quadTo(200F, 200F, 200F, 200F)
        lineTo(200F, 760F)
        quadTo(200F, 760F, 200F, 760F)
        quadTo(200F, 760F, 200F, 760F)
        close()
        moveTo(200F, 200F)
        lineTo(200F, 200F)
        quadTo(200F, 200F, 200F, 200F)
        quadTo(200F, 200F, 200F, 200F)
        lineTo(200F, 760F)
        quadTo(200F, 760F, 200F, 760F)
        quadTo(200F, 760F, 200F, 760F)
        lineTo(200F, 760F)
        quadTo(200F, 760F, 200F, 760F)
        quadTo(200F, 760F, 200F, 760F)
        lineTo(200F, 200F)
        quadTo(200F, 200F, 200F, 200F)
        quadTo(200F, 200F, 200F, 200F)
        close()
        moveTo(480F, 579F)
        lineTo(556F, 625F)
        quadTo(567F, 632F, 578F, 624.5F)
        quadTo(589F, 617F, 586F, 604F)
        lineTo(566F, 517F)
        lineTo(634F, 458F)
        quadTo(644F, 449F, 640F, 436.5F)
        quadTo(636F, 424F, 622F, 423F)
        lineTo(533F, 416F)
        lineTo(498F, 334F)
        quadTo(493F, 322F, 480F, 322F)
        quadTo(467F, 322F, 462F, 334F)
        lineTo(427F, 416F)
        lineTo(338F, 423F)
        quadTo(324F, 424F, 320F, 436.5F)
        quadTo(316F, 449F, 326F, 458F)
        lineTo(394F, 517F)
        lineTo(374F, 604F)
        quadTo(371F, 617F, 382F, 624.5F)
        quadTo(393F, 632F, 404F, 625F)
        lineTo(480F, 579F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Pages() {
    Icon(imageVector = Pages, contentDescription = Name)
}
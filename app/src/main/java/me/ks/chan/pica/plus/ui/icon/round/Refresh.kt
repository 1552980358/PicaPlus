package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Refresh"
private const val ViewPortSize = 960F
val Refresh by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(480F, 800F)
        quadTo(346F, 800F, 253F, 707F)
        quadTo(160F, 614F, 160F, 480F)
        quadTo(160F, 346F, 253F, 253F)
        quadTo(346F, 160F, 480F, 160F)
        quadTo(549F, 160F, 612F, 188.5F)
        quadTo(675F, 217F, 720F, 270F)
        lineTo(720F, 200F)
        quadTo(720F, 183F, 731.5F, 171.5F)
        quadTo(743F, 160F, 760F, 160F)
        quadTo(777F, 160F, 788.5F, 171.5F)
        quadTo(800F, 183F, 800F, 200F)
        lineTo(800F, 400F)
        quadTo(800F, 417F, 788.5F, 428.5F)
        quadTo(777F, 440F, 760F, 440F)
        lineTo(560F, 440F)
        quadTo(543F, 440F, 531.5F, 428.5F)
        quadTo(520F, 417F, 520F, 400F)
        quadTo(520F, 383F, 531.5F, 371.5F)
        quadTo(543F, 360F, 560F, 360F)
        lineTo(688F, 360F)
        quadTo(656F, 304F, 600.5F, 272F)
        quadTo(545F, 240F, 480F, 240F)
        quadTo(380F, 240F, 310F, 310F)
        quadTo(240F, 380F, 240F, 480F)
        quadTo(240F, 580F, 310F, 650F)
        quadTo(380F, 720F, 480F, 720F)
        quadTo(548F, 720F, 604.5F, 685.5F)
        quadTo(661F, 651F, 692F, 593F)
        quadTo(700F, 579F, 714.5F, 573.5F)
        quadTo(729F, 568F, 744F, 573F)
        quadTo(760F, 578F, 767F, 594F)
        quadTo(774F, 610F, 766F, 624F)
        quadTo(725F, 704F, 649F, 752F)
        quadTo(573F, 800F, 480F, 800F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Refresh() {
    Icon(imageVector = Refresh, contentDescription = Name)
}
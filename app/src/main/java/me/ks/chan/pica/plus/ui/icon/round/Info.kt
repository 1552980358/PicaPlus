package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Info"
private const val ViewPortSize = 960F
val Info by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(480F, 680F)
        quadTo(497F, 680F, 508.5F, 668.5F)
        quadTo(520F, 657F, 520F, 640F)
        lineTo(520F, 480F)
        quadTo(520F, 463F, 508.5F, 451.5F)
        quadTo(497F, 440F, 480F, 440F)
        quadTo(463F, 440F, 451.5F, 451.5F)
        quadTo(440F, 463F, 440F, 480F)
        lineTo(440F, 640F)
        quadTo(440F, 657F, 451.5F, 668.5F)
        quadTo(463F, 680F, 480F, 680F)
        close()
        moveTo(480F, 360F)
        quadTo(497F, 360F, 508.5F, 348.5F)
        quadTo(520F, 337F, 520F, 320F)
        quadTo(520F, 303F, 508.5F, 291.5F)
        quadTo(497F, 280F, 480F, 280F)
        quadTo(463F, 280F, 451.5F, 291.5F)
        quadTo(440F, 303F, 440F, 320F)
        quadTo(440F, 337F, 451.5F, 348.5F)
        quadTo(463F, 360F, 480F, 360F)
        close()
        moveTo(480F, 880F)
        quadTo(397F, 880F, 324F, 848.5F)
        quadTo(251F, 817F, 197F, 763F)
        quadTo(143F, 709F, 111.5F, 636F)
        quadTo(80F, 563F, 80F, 480F)
        quadTo(80F, 397F, 111.5F, 324F)
        quadTo(143F, 251F, 197F, 197F)
        quadTo(251F, 143F, 324F, 111.5F)
        quadTo(397F, 80F, 480F, 80F)
        quadTo(563F, 80F, 636F, 111.5F)
        quadTo(709F, 143F, 763F, 197F)
        quadTo(817F, 251F, 848.5F, 324F)
        quadTo(880F, 397F, 880F, 480F)
        quadTo(880F, 563F, 848.5F, 636F)
        quadTo(817F, 709F, 763F, 763F)
        quadTo(709F, 817F, 636F, 848.5F)
        quadTo(563F, 880F, 480F, 880F)
        close()
        moveTo(480F, 800F)
        quadTo(614F, 800F, 707F, 707F)
        quadTo(800F, 614F, 800F, 480F)
        quadTo(800F, 346F, 707F, 253F)
        quadTo(614F, 160F, 480F, 160F)
        quadTo(346F, 160F, 253F, 253F)
        quadTo(160F, 346F, 160F, 480F)
        quadTo(160F, 614F, 253F, 707F)
        quadTo(346F, 800F, 480F, 800F)
        close()
        moveTo(480F, 480F)
        quadTo(480F, 480F, 480F, 480F)
        quadTo(480F, 480F, 480F, 480F)
        quadTo(480F, 480F, 480F, 480F)
        quadTo(480F, 480F, 480F, 480F)
        quadTo(480F, 480F, 480F, 480F)
        quadTo(480F, 480F, 480F, 480F)
        quadTo(480F, 480F, 480F, 480F)
        quadTo(480F, 480F, 480F, 480F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Info() {
    Icon(imageVector = Info, contentDescription = Name)
}
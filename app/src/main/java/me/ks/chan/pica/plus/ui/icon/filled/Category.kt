package me.ks.chan.pica.plus.ui.icon.filled

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Category"
private const val ViewPortSize = 960F
val Category by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(297F, 379F)
        lineTo(446F, 136F)
        quadTo(452F, 126F, 461F, 121.5F)
        quadTo(470F, 117F, 480F, 117F)
        quadTo(490F, 117F, 499F, 121.5F)
        quadTo(508F, 126F, 514F, 136F)
        lineTo(663F, 379F)
        quadTo(669F, 389F, 669F, 400F)
        quadTo(669F, 411F, 664F, 420F)
        quadTo(659F, 429F, 650F, 434.5F)
        quadTo(641F, 440F, 629F, 440F)
        lineTo(331F, 440F)
        quadTo(319F, 440F, 310F, 434.5F)
        quadTo(301F, 429F, 296F, 420F)
        quadTo(291F, 411F, 291F, 400F)
        quadTo(291F, 389F, 297F, 379F)
        close()
        moveTo(700F, 880F)
        quadTo(625F, 880F, 572.5F, 827.5F)
        quadTo(520F, 775F, 520F, 700F)
        quadTo(520F, 625F, 572.5F, 572.5F)
        quadTo(625F, 520F, 700F, 520F)
        quadTo(775F, 520F, 827.5F, 572.5F)
        quadTo(880F, 625F, 880F, 700F)
        quadTo(880F, 775F, 827.5F, 827.5F)
        quadTo(775F, 880F, 700F, 880F)
        close()
        moveTo(120F, 820F)
        lineTo(120F, 580F)
        quadTo(120F, 563F, 131.5F, 551.5F)
        quadTo(143F, 540F, 160F, 540F)
        lineTo(400F, 540F)
        quadTo(417F, 540F, 428.5F, 551.5F)
        quadTo(440F, 563F, 440F, 580F)
        lineTo(440F, 820F)
        quadTo(440F, 837F, 428.5F, 848.5F)
        quadTo(417F, 860F, 400F, 860F)
        lineTo(160F, 860F)
        quadTo(143F, 860F, 131.5F, 848.5F)
        quadTo(120F, 837F, 120F, 820F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Category() {
    Icon(imageVector = Category, contentDescription = Name)
}
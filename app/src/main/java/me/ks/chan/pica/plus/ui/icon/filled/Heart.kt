package me.ks.chan.pica.plus.ui.icon.filled

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Heart"
private const val ViewPortSize = 960F
val Heart by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(480F, 813F)
        quadTo(466F, 813F, 451.5F, 808F)
        quadTo(437F, 803F, 426F, 792F)
        lineTo(357F, 729F)
        quadTo(251F, 632F, 165.5F, 536.5F)
        quadTo(80F, 441F, 80F, 326F)
        quadTo(80F, 232F, 143F, 169F)
        quadTo(206F, 106F, 300F, 106F)
        quadTo(353F, 106F, 400F, 128.5F)
        quadTo(447F, 151F, 480F, 190F)
        quadTo(513F, 151F, 560F, 128.5F)
        quadTo(607F, 106F, 660F, 106F)
        quadTo(754F, 106F, 817F, 169F)
        quadTo(880F, 232F, 880F, 326F)
        quadTo(880F, 441F, 795F, 537F)
        quadTo(710F, 633F, 602F, 730F)
        lineTo(534F, 792F)
        quadTo(523F, 803F, 508.5F, 808F)
        quadTo(494F, 813F, 480F, 813F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Heart() {
    Icon(imageVector = Heart, contentDescription = Name)
}
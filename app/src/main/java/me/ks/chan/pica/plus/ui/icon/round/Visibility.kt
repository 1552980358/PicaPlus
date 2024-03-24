package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Visibility"
private const val ViewPortSize = 960F
val Visibility by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(480F, 640F)
        quadTo(555F, 640F, 607.5F, 587.5F)
        quadTo(660F, 535F, 660F, 460F)
        quadTo(660F, 385F, 607.5F, 332.5F)
        quadTo(555F, 280F, 480F, 280F)
        quadTo(405F, 280F, 352.5F, 332.5F)
        quadTo(300F, 385F, 300F, 460F)
        quadTo(300F, 535F, 352.5F, 587.5F)
        quadTo(405F, 640F, 480F, 640F)
        close()
        moveTo(480F, 568F)
        quadTo(435F, 568F, 403.5F, 536.5F)
        quadTo(372F, 505F, 372F, 460F)
        quadTo(372F, 415F, 403.5F, 383.5F)
        quadTo(435F, 352F, 480F, 352F)
        quadTo(525F, 352F, 556.5F, 383.5F)
        quadTo(588F, 415F, 588F, 460F)
        quadTo(588F, 505F, 556.5F, 536.5F)
        quadTo(525F, 568F, 480F, 568F)
        close()
        moveTo(480F, 760F)
        quadTo(346F, 760F, 235.5F, 688F)
        quadTo(125F, 616F, 61F, 498F)
        quadTo(56F, 489F, 53.5F, 479.5F)
        quadTo(51F, 470F, 51F, 460F)
        quadTo(51F, 450F, 53.5F, 440.5F)
        quadTo(56F, 431F, 61F, 422F)
        quadTo(125F, 304F, 235.5F, 232F)
        quadTo(346F, 160F, 480F, 160F)
        quadTo(614F, 160F, 724.5F, 232F)
        quadTo(835F, 304F, 899F, 422F)
        quadTo(904F, 431F, 906.5F, 440.5F)
        quadTo(909F, 450F, 909F, 460F)
        quadTo(909F, 470F, 906.5F, 479.5F)
        quadTo(904F, 489F, 899F, 498F)
        quadTo(835F, 616F, 724.5F, 688F)
        quadTo(614F, 760F, 480F, 760F)
        close()
        moveTo(480F, 460F)
        quadTo(480F, 460F, 480F, 460F)
        quadTo(480F, 460F, 480F, 460F)
        quadTo(480F, 460F, 480F, 460F)
        quadTo(480F, 460F, 480F, 460F)
        quadTo(480F, 460F, 480F, 460F)
        quadTo(480F, 460F, 480F, 460F)
        quadTo(480F, 460F, 480F, 460F)
        quadTo(480F, 460F, 480F, 460F)
        close()
        moveTo(480F, 680F)
        quadTo(593F, 680F, 687.5F, 620.5F)
        quadTo(782F, 561F, 832F, 460F)
        quadTo(782F, 359F, 687.5F, 299.5F)
        quadTo(593F, 240F, 480F, 240F)
        quadTo(367F, 240F, 272.5F, 299.5F)
        quadTo(178F, 359F, 128F, 460F)
        quadTo(178F, 561F, 272.5F, 620.5F)
        quadTo(367F, 680F, 480F, 680F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Visibility() {
    Icon(imageVector = Visibility, contentDescription = Name)
}
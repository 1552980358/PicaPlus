package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Password"
private const val ViewPortSize = 960F
val Password by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(160F, 520F)
        quadTo(110F, 520F, 75F, 485F)
        quadTo(40F, 450F, 40F, 400F)
        quadTo(40F, 350F, 75F, 315F)
        quadTo(110F, 280F, 160F, 280F)
        quadTo(210F, 280F, 245F, 315F)
        quadTo(280F, 350F, 280F, 400F)
        quadTo(280F, 450F, 245F, 485F)
        quadTo(210F, 520F, 160F, 520F)
        close()
        moveTo(120F, 760F)
        quadTo(103F, 760F, 91.5F, 748.5F)
        quadTo(80F, 737F, 80F, 720F)
        quadTo(80F, 703F, 91.5F, 691.5F)
        quadTo(103F, 680F, 120F, 680F)
        lineTo(840F, 680F)
        quadTo(857F, 680F, 868.5F, 691.5F)
        quadTo(880F, 703F, 880F, 720F)
        quadTo(880F, 737F, 868.5F, 748.5F)
        quadTo(857F, 760F, 840F, 760F)
        lineTo(120F, 760F)
        close()
        moveTo(480F, 520F)
        quadTo(430F, 520F, 395F, 485F)
        quadTo(360F, 450F, 360F, 400F)
        quadTo(360F, 350F, 395F, 315F)
        quadTo(430F, 280F, 480F, 280F)
        quadTo(530F, 280F, 565F, 315F)
        quadTo(600F, 350F, 600F, 400F)
        quadTo(600F, 450F, 565F, 485F)
        quadTo(530F, 520F, 480F, 520F)
        close()
        moveTo(800F, 520F)
        quadTo(750F, 520F, 715F, 485F)
        quadTo(680F, 450F, 680F, 400F)
        quadTo(680F, 350F, 715F, 315F)
        quadTo(750F, 280F, 800F, 280F)
        quadTo(850F, 280F, 885F, 315F)
        quadTo(920F, 350F, 920F, 400F)
        quadTo(920F, 450F, 885F, 485F)
        quadTo(850F, 520F, 800F, 520F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Password() {
    Icon(imageVector = Password, contentDescription = Name)
}
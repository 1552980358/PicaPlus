package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Lock"
private const val ViewPortSize = 960F
val Lock by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(240F, 880F)
        quadTo(207F, 880F, 183.5F, 856.5F)
        quadTo(160F, 833F, 160F, 800F)
        lineTo(160F, 400F)
        quadTo(160F, 367F, 183.5F, 343.5F)
        quadTo(207F, 320F, 240F, 320F)
        lineTo(280F, 320F)
        lineTo(280F, 240F)
        quadTo(280F, 157F, 338.5F, 98.5F)
        quadTo(397F, 40F, 480F, 40F)
        quadTo(563F, 40F, 621.5F, 98.5F)
        quadTo(680F, 157F, 680F, 240F)
        lineTo(680F, 320F)
        lineTo(720F, 320F)
        quadTo(753F, 320F, 776.5F, 343.5F)
        quadTo(800F, 367F, 800F, 400F)
        lineTo(800F, 800F)
        quadTo(800F, 833F, 776.5F, 856.5F)
        quadTo(753F, 880F, 720F, 880F)
        lineTo(240F, 880F)
        close()
        moveTo(240F, 800F)
        lineTo(720F, 800F)
        quadTo(720F, 800F, 720F, 800F)
        quadTo(720F, 800F, 720F, 800F)
        lineTo(720F, 400F)
        quadTo(720F, 400F, 720F, 400F)
        quadTo(720F, 400F, 720F, 400F)
        lineTo(240F, 400F)
        quadTo(240F, 400F, 240F, 400F)
        quadTo(240F, 400F, 240F, 400F)
        lineTo(240F, 800F)
        quadTo(240F, 800F, 240F, 800F)
        quadTo(240F, 800F, 240F, 800F)
        close()
        moveTo(480F, 680F)
        quadTo(513F, 680F, 536.5F, 656.5F)
        quadTo(560F, 633F, 560F, 600F)
        quadTo(560F, 567F, 536.5F, 543.5F)
        quadTo(513F, 520F, 480F, 520F)
        quadTo(447F, 520F, 423.5F, 543.5F)
        quadTo(400F, 567F, 400F, 600F)
        quadTo(400F, 633F, 423.5F, 656.5F)
        quadTo(447F, 680F, 480F, 680F)
        close()
        moveTo(360F, 320F)
        lineTo(600F, 320F)
        lineTo(600F, 240F)
        quadTo(600F, 190F, 565F, 155F)
        quadTo(530F, 120F, 480F, 120F)
        quadTo(430F, 120F, 395F, 155F)
        quadTo(360F, 190F, 360F, 240F)
        lineTo(360F, 320F)
        close()
        moveTo(240F, 800F)
        quadTo(240F, 800F, 240F, 800F)
        quadTo(240F, 800F, 240F, 800F)
        lineTo(240F, 400F)
        quadTo(240F, 400F, 240F, 400F)
        quadTo(240F, 400F, 240F, 400F)
        lineTo(240F, 400F)
        quadTo(240F, 400F, 240F, 400F)
        quadTo(240F, 400F, 240F, 400F)
        lineTo(240F, 800F)
        quadTo(240F, 800F, 240F, 800F)
        quadTo(240F, 800F, 240F, 800F)
        lineTo(240F, 800F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Lock() {
    Icon(imageVector = Lock, contentDescription = Name)
}
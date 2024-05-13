package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "MoreHoriz"
private const val ViewPortSize = 960F
val MoreHoriz by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(240F, 560F)
        quadTo(207F, 560F, 183.5F, 536.5F)
        quadTo(160F, 513F, 160F, 480F)
        quadTo(160F, 447F, 183.5F, 423.5F)
        quadTo(207F, 400F, 240F, 400F)
        quadTo(273F, 400F, 296.5F, 423.5F)
        quadTo(320F, 447F, 320F, 480F)
        quadTo(320F, 513F, 296.5F, 536.5F)
        quadTo(273F, 560F, 240F, 560F)
        close()
        moveTo(480F, 560F)
        quadTo(447F, 560F, 423.5F, 536.5F)
        quadTo(400F, 513F, 400F, 480F)
        quadTo(400F, 447F, 423.5F, 423.5F)
        quadTo(447F, 400F, 480F, 400F)
        quadTo(513F, 400F, 536.5F, 423.5F)
        quadTo(560F, 447F, 560F, 480F)
        quadTo(560F, 513F, 536.5F, 536.5F)
        quadTo(513F, 560F, 480F, 560F)
        close()
        moveTo(720F, 560F)
        quadTo(687F, 560F, 663.5F, 536.5F)
        quadTo(640F, 513F, 640F, 480F)
        quadTo(640F, 447F, 663.5F, 423.5F)
        quadTo(687F, 400F, 720F, 400F)
        quadTo(753F, 400F, 776.5F, 423.5F)
        quadTo(800F, 447F, 800F, 480F)
        quadTo(800F, 513F, 776.5F, 536.5F)
        quadTo(753F, 560F, 720F, 560F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun MoreHoriz() {
    Icon(imageVector = MoreHoriz, contentDescription = Name)
}
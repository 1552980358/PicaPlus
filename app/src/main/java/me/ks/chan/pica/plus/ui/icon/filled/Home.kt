package me.ks.chan.pica.plus.ui.icon.filled

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Home"
private const val ViewPortSize = 960F
val Home by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(160F, 760F)
        lineTo(160F, 400F)
        quadTo(160F, 381F, 168.5F, 364F)
        quadTo(177F, 347F, 192F, 336F)
        lineTo(432F, 156F)
        quadTo(453F, 140F, 480F, 140F)
        quadTo(507F, 140F, 528F, 156F)
        lineTo(768F, 336F)
        quadTo(783F, 347F, 791.5F, 364F)
        quadTo(800F, 381F, 800F, 400F)
        lineTo(800F, 760F)
        quadTo(800F, 793F, 776.5F, 816.5F)
        quadTo(753F, 840F, 720F, 840F)
        lineTo(600F, 840F)
        quadTo(583F, 840F, 571.5F, 828.5F)
        quadTo(560F, 817F, 560F, 800F)
        lineTo(560F, 600F)
        quadTo(560F, 583F, 548.5F, 571.5F)
        quadTo(537F, 560F, 520F, 560F)
        lineTo(440F, 560F)
        quadTo(423F, 560F, 411.5F, 571.5F)
        quadTo(400F, 583F, 400F, 600F)
        lineTo(400F, 800F)
        quadTo(400F, 817F, 388.5F, 828.5F)
        quadTo(377F, 840F, 360F, 840F)
        lineTo(240F, 840F)
        quadTo(207F, 840F, 183.5F, 816.5F)
        quadTo(160F, 793F, 160F, 760F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Home() {
    Icon(imageVector = Home, contentDescription = Name)
}
package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Home"
private const val ViewPortSize = 960F
val Home by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(240F, 760F)
        lineTo(360F, 760F)
        lineTo(360F, 560F)
        quadTo(360F, 543F, 371.5F, 531.5F)
        quadTo(383F, 520F, 400F, 520F)
        lineTo(560F, 520F)
        quadTo(577F, 520F, 588.5F, 531.5F)
        quadTo(600F, 543F, 600F, 560F)
        lineTo(600F, 760F)
        lineTo(720F, 760F)
        lineTo(720F, 400F)
        quadTo(720F, 400F, 720F, 400F)
        quadTo(720F, 400F, 720F, 400F)
        lineTo(480F, 220F)
        quadTo(480F, 220F, 480F, 220F)
        quadTo(480F, 220F, 480F, 220F)
        lineTo(240F, 400F)
        quadTo(240F, 400F, 240F, 400F)
        quadTo(240F, 400F, 240F, 400F)
        lineTo(240F, 760F)
        close()
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
        lineTo(560F, 840F)
        quadTo(543F, 840F, 531.5F, 828.5F)
        quadTo(520F, 817F, 520F, 800F)
        lineTo(520F, 600F)
        quadTo(520F, 600F, 520F, 600F)
        quadTo(520F, 600F, 520F, 600F)
        lineTo(440F, 600F)
        quadTo(440F, 600F, 440F, 600F)
        quadTo(440F, 600F, 440F, 600F)
        lineTo(440F, 800F)
        quadTo(440F, 817F, 428.5F, 828.5F)
        quadTo(417F, 840F, 400F, 840F)
        lineTo(240F, 840F)
        quadTo(207F, 840F, 183.5F, 816.5F)
        quadTo(160F, 793F, 160F, 760F)
        close()
        moveTo(480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        quadTo(480F, 490F, 480F, 490F)
        quadTo(480F, 490F, 480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        quadTo(480F, 490F, 480F, 490F)
        quadTo(480F, 490F, 480F, 490F)
        lineTo(480F, 490F)
        quadTo(480F, 490F, 480F, 490F)
        quadTo(480F, 490F, 480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Home() {
    Icon(imageVector = Home, contentDescription = Name)
}
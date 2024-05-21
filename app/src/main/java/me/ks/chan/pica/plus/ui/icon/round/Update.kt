package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Update"
private const val ViewPortSize = 960F
val Update by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(480F, 840F)
        quadTo(405F, 840F, 339.5F, 811.5F)
        quadTo(274F, 783F, 225.5F, 734.5F)
        quadTo(177F, 686F, 148.5F, 620.5F)
        quadTo(120F, 555F, 120F, 480F)
        quadTo(120F, 405F, 148.5F, 339.5F)
        quadTo(177F, 274F, 225.5F, 225.5F)
        quadTo(274F, 177F, 339.5F, 148.5F)
        quadTo(405F, 120F, 480F, 120F)
        quadTo(562F, 120F, 635.5F, 155F)
        quadTo(709F, 190F, 760F, 254F)
        lineTo(760F, 200F)
        quadTo(760F, 183F, 771.5F, 171.5F)
        quadTo(783F, 160F, 800F, 160F)
        quadTo(817F, 160F, 828.5F, 171.5F)
        quadTo(840F, 183F, 840F, 200F)
        lineTo(840F, 360F)
        quadTo(840F, 377F, 828.5F, 388.5F)
        quadTo(817F, 400F, 800F, 400F)
        lineTo(640F, 400F)
        quadTo(623F, 400F, 611.5F, 388.5F)
        quadTo(600F, 377F, 600F, 360F)
        quadTo(600F, 343F, 611.5F, 331.5F)
        quadTo(623F, 320F, 640F, 320F)
        lineTo(710F, 320F)
        quadTo(669F, 264F, 609F, 232F)
        quadTo(549F, 200F, 480F, 200F)
        quadTo(363F, 200F, 281.5F, 281.5F)
        quadTo(200F, 363F, 200F, 480F)
        quadTo(200F, 597F, 281.5F, 678.5F)
        quadTo(363F, 760F, 480F, 760F)
        quadTo(575F, 760F, 650F, 703F)
        quadTo(725F, 646F, 749F, 556F)
        quadTo(754F, 540F, 767F, 532F)
        quadTo(780F, 524F, 796F, 526F)
        quadTo(813F, 528F, 823F, 540.5F)
        quadTo(833F, 553F, 829F, 568F)
        quadTo(800F, 687F, 703F, 763.5F)
        quadTo(606F, 840F, 480F, 840F)
        close()
        moveTo(520F, 464F)
        lineTo(620F, 564F)
        quadTo(631F, 575F, 631F, 592F)
        quadTo(631F, 609F, 620F, 620F)
        quadTo(609F, 631F, 592F, 631F)
        quadTo(575F, 631F, 564F, 620F)
        lineTo(452F, 508F)
        quadTo(446F, 502F, 443F, 494.5F)
        quadTo(440F, 487F, 440F, 479F)
        lineTo(440F, 320F)
        quadTo(440F, 303F, 451.5F, 291.5F)
        quadTo(463F, 280F, 480F, 280F)
        quadTo(497F, 280F, 508.5F, 291.5F)
        quadTo(520F, 303F, 520F, 320F)
        lineTo(520F, 464F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Update() {
    Icon(imageVector = Update, contentDescription = Name)
}
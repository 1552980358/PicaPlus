package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Replay"
private const val ViewPortSize = 960F
val Replay by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(480F, 880F)
        quadTo(405F, 880F, 339.5F, 851.5F)
        quadTo(274F, 823F, 225.5F, 774.5F)
        quadTo(177F, 726F, 148.5F, 660.5F)
        quadTo(120F, 595F, 120F, 520F)
        quadTo(120F, 503F, 131.5F, 491.5F)
        quadTo(143F, 480F, 160F, 480F)
        quadTo(177F, 480F, 188.5F, 491.5F)
        quadTo(200F, 503F, 200F, 520F)
        quadTo(200F, 637F, 281.5F, 718.5F)
        quadTo(363F, 800F, 480F, 800F)
        quadTo(597F, 800F, 678.5F, 718.5F)
        quadTo(760F, 637F, 760F, 520F)
        quadTo(760F, 403F, 678.5F, 321.5F)
        quadTo(597F, 240F, 480F, 240F)
        lineTo(474F, 240F)
        lineTo(508F, 274F)
        quadTo(520F, 286F, 519.5F, 302F)
        quadTo(519F, 318F, 508F, 330F)
        quadTo(496F, 342F, 479.5F, 342.5F)
        quadTo(463F, 343F, 451F, 331F)
        lineTo(348F, 228F)
        quadTo(336F, 216F, 336F, 200F)
        quadTo(336F, 184F, 348F, 172F)
        lineTo(451F, 69F)
        quadTo(463F, 57F, 479.5F, 57.5F)
        quadTo(496F, 58F, 508F, 70F)
        quadTo(519F, 82F, 519.5F, 98F)
        quadTo(520F, 114F, 508F, 126F)
        lineTo(474F, 160F)
        lineTo(480F, 160F)
        quadTo(555F, 160F, 620.5F, 188.5F)
        quadTo(686F, 217F, 734.5F, 265.5F)
        quadTo(783F, 314F, 811.5F, 379.5F)
        quadTo(840F, 445F, 840F, 520F)
        quadTo(840F, 595F, 811.5F, 660.5F)
        quadTo(783F, 726F, 734.5F, 774.5F)
        quadTo(686F, 823F, 620.5F, 851.5F)
        quadTo(555F, 880F, 480F, 880F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Replay() {
    Icon(imageVector = Replay, contentDescription = Name)
}
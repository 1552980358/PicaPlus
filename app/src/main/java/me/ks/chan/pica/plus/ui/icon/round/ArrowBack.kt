package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "ArrowBack"
private const val ViewPortSize = 960F
val ArrowBack by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(313F, 520F)
        lineTo(509F, 716F)
        quadTo(521F, 728F, 520.5F, 744F)
        quadTo(520F, 760F, 508F, 772F)
        quadTo(496F, 783F, 480F, 783.5F)
        quadTo(464F, 784F, 452F, 772F)
        lineTo(188F, 508F)
        quadTo(182F, 502F, 179.5F, 495F)
        quadTo(177F, 488F, 177F, 480F)
        quadTo(177F, 472F, 179.5F, 465F)
        quadTo(182F, 458F, 188F, 452F)
        lineTo(452F, 188F)
        quadTo(463F, 177F, 479.5F, 177F)
        quadTo(496F, 177F, 508F, 188F)
        quadTo(520F, 200F, 520F, 216.5F)
        quadTo(520F, 233F, 508F, 245F)
        lineTo(313F, 440F)
        lineTo(760F, 440F)
        quadTo(777F, 440F, 788.5F, 451.5F)
        quadTo(800F, 463F, 800F, 480F)
        quadTo(800F, 497F, 788.5F, 508.5F)
        quadTo(777F, 520F, 760F, 520F)
        lineTo(313F, 520F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun ArrowBack() {
    Icon(imageVector = ArrowBack, contentDescription = Name)
}
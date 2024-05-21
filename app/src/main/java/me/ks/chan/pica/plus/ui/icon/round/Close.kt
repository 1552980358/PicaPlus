package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Close"
private const val ViewPortSize = 960F
val Close by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(480F, 536F)
        lineTo(284F, 732F)
        quadTo(273F, 743F, 256F, 743F)
        quadTo(239F, 743F, 228F, 732F)
        quadTo(217F, 721F, 217F, 704F)
        quadTo(217F, 687F, 228F, 676F)
        lineTo(424F, 480F)
        lineTo(228F, 284F)
        quadTo(217F, 273F, 217F, 256F)
        quadTo(217F, 239F, 228F, 228F)
        quadTo(239F, 217F, 256F, 217F)
        quadTo(273F, 217F, 284F, 228F)
        lineTo(480F, 424F)
        lineTo(676F, 228F)
        quadTo(687F, 217F, 704F, 217F)
        quadTo(721F, 217F, 732F, 228F)
        quadTo(743F, 239F, 743F, 256F)
        quadTo(743F, 273F, 732F, 284F)
        lineTo(536F, 480F)
        lineTo(732F, 676F)
        quadTo(743F, 687F, 743F, 704F)
        quadTo(743F, 721F, 732F, 732F)
        quadTo(721F, 743F, 704F, 743F)
        quadTo(687F, 743F, 676F, 732F)
        lineTo(480F, 536F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Close() {
    Icon(imageVector = Close, contentDescription = Name)
}
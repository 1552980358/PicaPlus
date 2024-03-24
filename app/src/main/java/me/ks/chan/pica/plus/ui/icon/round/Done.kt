package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Done"
private const val ViewPortSize = 960F
val Done by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(382F, 606F)
        lineTo(721F, 267F)
        quadTo(733F, 255F, 749.5F, 255F)
        quadTo(766F, 255F, 778F, 267F)
        quadTo(790F, 279F, 790F, 295.5F)
        quadTo(790F, 312F, 778F, 324F)
        lineTo(410F, 692F)
        quadTo(398F, 704F, 382F, 704F)
        quadTo(366F, 704F, 354F, 692F)
        lineTo(182F, 520F)
        quadTo(170F, 508F, 170.5F, 491.5F)
        quadTo(171F, 475F, 183F, 463F)
        quadTo(195F, 451F, 211.5F, 451F)
        quadTo(228F, 451F, 240F, 463F)
        lineTo(382F, 606F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Done() {
    Icon(imageVector = Done, contentDescription = Name)
}
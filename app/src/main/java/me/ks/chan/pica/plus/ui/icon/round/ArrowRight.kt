package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "ArrowRight"
private const val ViewPortSize = 960F
val ArrowRight by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(420F, 652F)
        quadTo(412F, 652F, 406F, 646.5F)
        quadTo(400F, 641F, 400F, 632F)
        lineTo(400F, 328F)
        quadTo(400F, 319F, 406F, 313.5F)
        quadTo(412F, 308F, 420F, 308F)
        quadTo(422F, 308F, 434F, 314F)
        lineTo(579F, 459F)
        quadTo(584F, 464F, 586F, 469F)
        quadTo(588F, 474F, 588F, 480F)
        quadTo(588F, 486F, 586F, 491F)
        quadTo(584F, 496F, 579F, 501F)
        lineTo(434F, 646F)
        quadTo(431F, 649F, 427.5F, 650.5F)
        quadTo(424F, 652F, 420F, 652F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun ArrowRight() {
    Icon(imageVector = ArrowRight, contentDescription = Name)
}
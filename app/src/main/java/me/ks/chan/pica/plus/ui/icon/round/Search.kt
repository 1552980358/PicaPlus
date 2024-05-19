package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Search"
private const val ViewPortSize = 960F
val Search by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(380F, 640F)
        quadTo(271F, 640F, 195.5F, 564.5F)
        quadTo(120F, 489F, 120F, 380F)
        quadTo(120F, 271F, 195.5F, 195.5F)
        quadTo(271F, 120F, 380F, 120F)
        quadTo(489F, 120F, 564.5F, 195.5F)
        quadTo(640F, 271F, 640F, 380F)
        quadTo(640F, 424F, 626F, 463F)
        quadTo(612F, 502F, 588F, 532F)
        lineTo(812F, 756F)
        quadTo(823F, 767F, 823F, 784F)
        quadTo(823F, 801F, 812F, 812F)
        quadTo(801F, 823F, 784F, 823F)
        quadTo(767F, 823F, 756F, 812F)
        lineTo(532F, 588F)
        quadTo(502F, 612F, 463F, 626F)
        quadTo(424F, 640F, 380F, 640F)
        close()
        moveTo(380F, 560F)
        quadTo(455F, 560F, 507.5F, 507.5F)
        quadTo(560F, 455F, 560F, 380F)
        quadTo(560F, 305F, 507.5F, 252.5F)
        quadTo(455F, 200F, 380F, 200F)
        quadTo(305F, 200F, 252.5F, 252.5F)
        quadTo(200F, 305F, 200F, 380F)
        quadTo(200F, 455F, 252.5F, 507.5F)
        quadTo(305F, 560F, 380F, 560F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Search() {
    Icon(imageVector = Search, contentDescription = Name)
}
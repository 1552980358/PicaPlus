package me.ks.chan.pica.plus.ui.icon.filled

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Person"
private const val ViewPortSize = 960F
val Person by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(480F, 480F)
        quadTo(414F, 480F, 367F, 433F)
        quadTo(320F, 386F, 320F, 320F)
        quadTo(320F, 254F, 367F, 207F)
        quadTo(414F, 160F, 480F, 160F)
        quadTo(546F, 160F, 593F, 207F)
        quadTo(640F, 254F, 640F, 320F)
        quadTo(640F, 386F, 593F, 433F)
        quadTo(546F, 480F, 480F, 480F)
        close()
        moveTo(160F, 800F)
        lineTo(160F, 688F)
        quadTo(160F, 654F, 177.5F, 625.5F)
        quadTo(195F, 597F, 224F, 582F)
        quadTo(286F, 551F, 350F, 535.5F)
        quadTo(414F, 520F, 480F, 520F)
        quadTo(546F, 520F, 610F, 535.5F)
        quadTo(674F, 551F, 736F, 582F)
        quadTo(765F, 597F, 782.5F, 625.5F)
        quadTo(800F, 654F, 800F, 688F)
        lineTo(800F, 800F)
        lineTo(160F, 800F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Person() {
    Icon(imageVector = Person, contentDescription = Name)
}
package me.ks.chan.pica.plus.resources.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.material.symbols.MaterialSymbols
import me.ks.chan.material.symbols.annotation.MaterialSymbol
import me.ks.chan.material.symbols.annotation.MaterialSymbolStyle
import me.ks.chan.material.symbols.annotation.Style

@MaterialSymbol
interface Visibility {
    @Style(MaterialSymbolStyle.Rounded)
    val rounded: ImageVector
}

@Preview
@Composable
private fun Preview() {
    Icon(
        imageVector = MaterialSymbols.Visibility.rounded,
        contentDescription = "Preview: ${Visibility::class.qualifiedName}"
    )
}
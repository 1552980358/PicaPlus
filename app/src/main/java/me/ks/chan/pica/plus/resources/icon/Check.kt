package me.ks.chan.pica.plus.resources.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.material.symbols.MaterialSymbols
import me.ks.chan.material.symbols.annotation.MaterialSymbol
import me.ks.chan.material.symbols.annotation.MaterialSymbolStyle
import me.ks.chan.material.symbols.annotation.Style

@MaterialSymbol
abstract class Check {

    @Style(MaterialSymbolStyle.Rounded)
    protected abstract val rounded: ImageVector

    @Composable
    fun RoundedIcon(
        modifier: Modifier = Modifier,
        contentDescription: String? = null
    ) {
        Icon(
            modifier = modifier,
            imageVector = rounded,
            contentDescription = contentDescription,
        )
    }

}

@Composable
@Preview
private fun Preview() {
    MaterialSymbols.Check.RoundedIcon()
}
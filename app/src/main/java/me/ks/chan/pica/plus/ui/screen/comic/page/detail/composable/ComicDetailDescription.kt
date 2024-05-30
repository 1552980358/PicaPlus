package me.ks.chan.pica.plus.ui.screen.comic.page.detail.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.composable.shimmer.shimmer
import me.ks.chan.pica.plus.ui.theme.Spacing_4
import me.ks.chan.pica.plus.util.androidx.compose.TwoThirdSize
import me.ks.chan.pica.plus.util.kotlin.Blank

@Composable
fun ComicDetailDescription(
    description: String?,
) {
    Column(
        modifier = Modifier.animateContentSize(),
    ) {
        TextStyle.Default
        Text(
            modifier = Modifier.shimmer(loading = description == null),
            text = stringResource(
                id = R.string.screen_comic_detail_description_title
            ),
            style = MaterialTheme.typography.bodyLarge,
        )

        when (description) {
            null -> {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmer(paddings = PaddingValues(top = Spacing_4)),
                    text = Blank,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(fraction = TwoThirdSize)
                        .shimmer(paddings = PaddingValues(top = Spacing_4)),
                    text = Blank,
                )
            }
            else -> {
                SelectionContainer {
                    Text(text = description)
                }
            }
        }
    }
}
package me.ks.chan.pica.plus.ui.screen.comic.page.detail.composable

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.ks.chan.pica.plus.ui.composable.shimmer.shimmer
import me.ks.chan.pica.plus.ui.theme.Spacing_8

@Composable
fun <T> ComicPropertyListGroup(
    itemList: List<T>?,
    @StringRes titleResId: Int,
    label: @Composable (T) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.shimmer(loading = itemList == null),
            text = stringResource(id = titleResId),
            style = MaterialTheme.typography.bodyLarge,
        )

        Crossfade(
            targetState = itemList,
            label = "ComicScreen.ComicDetailPage.PropertyListGroup.Crossfade",
        ) { itemList ->
            when {
                itemList != null -> {
                    ComicPropertyListItem(
                        itemList = itemList,
                        label = label,
                    )
                }
                else -> {
                    ComicPropertyShimmer()
                }
            }
        }
    }
}

@Composable
private fun <T> ComicPropertyListItem(
    itemList: List<T>,
    label: @Composable (T) -> Unit,
) {
    @OptIn(ExperimentalLayoutApi::class)
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(space = Spacing_8),
    ) {
        itemList.forEach { item ->
            ElevatedSuggestionChip(
                onClick = { /*TODO*/ },
                label = { label(item) }
            )
        }
    }
}

private const val MaterialChipHeight = 32
private const val ShimmerSecondaryWidthFraction = .666F
@Composable
private fun ComicPropertyShimmer() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Spacing_8)
                .height(MaterialChipHeight.dp)
                .shimmer()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(ShimmerSecondaryWidthFraction)
                .padding(top = Spacing_8)
                .height(MaterialChipHeight.dp)
                .shimmer()
        )
    }
}
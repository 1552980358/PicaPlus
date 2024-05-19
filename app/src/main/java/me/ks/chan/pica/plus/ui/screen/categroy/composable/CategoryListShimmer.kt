package me.ks.chan.pica.plus.ui.screen.categroy.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.ks.chan.pica.plus.ui.composable.shimmer.shimmer
import me.ks.chan.pica.plus.ui.theme.Sizing_56
import me.ks.chan.pica.plus.util.kotlin.Blank

private const val ShimmerItemCount = 10
fun LazyListScope.categoryListShimmer() {
    items(count = ShimmerItemCount) {
        CategoryListShimmerItem()
    }
}

@Composable
private fun CategoryListShimmerItem() {
    ListItem(
        headlineContent = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer(),
                text = Blank,
            )
        },
        leadingContent = {
            Box(modifier = Modifier.size(Sizing_56).shimmer())
        },
    )
}
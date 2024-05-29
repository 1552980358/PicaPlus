package me.ks.chan.pica.plus.ui.screen.categroy.composable

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.composable.shimmer.shimmer
import me.ks.chan.pica.plus.ui.icon.round.Refresh
import me.ks.chan.pica.plus.ui.screen.categroy.viewmodel.CategoryModel
import me.ks.chan.pica.plus.ui.theme.Corner_12
import me.ks.chan.pica.plus.ui.theme.Sizing_56
import me.ks.chan.pica.plus.util.androidx.compose.FalseState
import me.ks.chan.pica.plus.util.androidx.compose.TrueState

@Composable
fun CategoryListItem(
    category: CategoryModel,
) {
    val title = category.title
    ListItem(
        modifier = Modifier.clickable {  },
        headlineContent = { Text(text = title) },
        leadingContent = {
            CategoryListItemLeading(
                title = title,
                categoryThumb = category.thumb,
            )
        },
    )
}

private const val CategoryListItemLeadingImage = "CategoryListItemLeadingImage"
@Composable
private fun CategoryListItemLeading(
    title: String,
    categoryThumb: String
) {
    var error by remember(::FalseState)

    Crossfade(
        targetState = error,
        label = CategoryListItemLeadingImage,
    ) { isError ->
        when {
            isError -> {
                IconButton(onClick = { error = false }) {
                    Icon(
                        imageVector = Refresh,
                        contentDescription = stringResource(
                            id = R.string.action_retry
                        )
                    )
                }
            }
            else -> {
                var loading by remember(::TrueState)

                AsyncImage(
                    modifier = Modifier
                        .size(size = Sizing_56)
                        .shimmer(loading = loading)
                        .clip(RoundedCornerShape(size = Corner_12)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(categoryThumb)
                        .lifecycle(LocalLifecycleOwner.current)
                        .crossfade(true)
                        .listener(
                            onError = { _, _ -> error = true },
                            onSuccess = { _, _ -> loading = false },
                        )
                        .build(),
                    contentDescription = title,
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
}
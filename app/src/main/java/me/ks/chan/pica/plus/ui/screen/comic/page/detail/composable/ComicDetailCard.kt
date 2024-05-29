package me.ks.chan.pica.plus.ui.screen.comic.page.detail.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import kotlinx.datetime.Clock
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.repository.pica.PicaComicCategory
import me.ks.chan.pica.plus.ui.composable.shimmer.shimmer
import me.ks.chan.pica.plus.ui.icon.round.Heart
import me.ks.chan.pica.plus.ui.icon.round.Refresh
import me.ks.chan.pica.plus.ui.icon.round.Visibility
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicCategoryModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicModel
import me.ks.chan.pica.plus.ui.theme.Corner_12
import me.ks.chan.pica.plus.ui.theme.Duration_Short4
import me.ks.chan.pica.plus.ui.theme.Spacing_12
import me.ks.chan.pica.plus.ui.theme.Spacing_16
import me.ks.chan.pica.plus.ui.theme.Spacing_4
import me.ks.chan.pica.plus.util.androidx.compose.FullSize
import me.ks.chan.pica.plus.util.androidx.compose.HalfSize
import me.ks.chan.pica.plus.util.androidx.compose.TwoThirdSize
import me.ks.chan.pica.plus.util.coil.rememberImageRequestRetryHelper
import me.ks.chan.pica.plus.util.coil.retryWith
import me.ks.chan.pica.plus.util.kotlin.Blank

private const val ComicCoverWidth = 136
private const val ComicCoverHeight = ComicCoverWidth / 3 * 4

@Composable
fun ComicDetailCard(comic: ComicModel?) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(ComicCoverHeight.dp),
    ) {
        Row {
            val imageRequestRetryHelper = rememberImageRequestRetryHelper()
            val density = LocalDensity.current

            val asyncImagePainter = rememberAsyncImagePainter(
                model = comic?.thumb.let { thumb ->
                    ImageRequest.Builder(LocalContext.current)
                        .data(thumb)
                        .size(
                            width = density.run { ComicCoverWidth.dp.toPx() }.toInt(),
                            height = density.run { ComicCoverHeight.dp.toPx() }.toInt()
                        )
                        .scale(Scale.FIT)
                        .retryWith(imageRequestRetryHelper = imageRequestRetryHelper)
                        .crossfade(durationMillis = Duration_Short4)
                        .build()
                },
            )

            Box(
                modifier = Modifier
                    .size(ComicCoverWidth.dp, ComicCoverHeight.dp)
                    .clip(RoundedCornerShape(size = Corner_12))
                    .shimmer(loading = comic == null || asyncImagePainter.state is AsyncImagePainter.State.Loading),
            ) {
                when {
                    comic != null && asyncImagePainter.state is AsyncImagePainter.State.Success -> {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = asyncImagePainter,
                            contentDescription = comic.title,
                            contentScale = ContentScale.Crop,
                        )
                    }
                    comic != null && asyncImagePainter.state is AsyncImagePainter.State.Error -> {
                        IconButton(onClick = { imageRequestRetryHelper.retry() }) {
                            Icon(
                                imageVector = Refresh,
                                contentDescription = stringResource(
                                    id = R.string.action_retry
                                )
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Spacing_16, vertical = Spacing_12)
            ) {
                SelectionContainer {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shimmer(loading = comic == null),
                        text = comic?.title ?: Blank,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 3,
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = TwoThirdSize)
                        .shimmer(
                            loading = comic?.author == null,
                            paddings = PaddingValues(top = Spacing_4)
                        ),
                ) {
                    Text(
                        modifier = Modifier
                            .clickable { /*TODO*/ },
                        text = comic?.author ?: Blank,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = Spacing_4)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.shimmer(loading = comic == null),
                        imageVector = Visibility,
                        contentDescription = stringResource(
                            id = R.string.screen_home_comic_menu_item_views
                        )
                    )

                    Box(
                        modifier = Modifier
                            .padding(horizontal = Spacing_4)
                            .fillMaxWidth(fraction = when {
                                comic == null -> HalfSize
                                else -> FullSize
                            })
                            .shimmer(loading = comic == null),
                    ) {
                        Text(
                            text = comic?.views.toString()
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = Spacing_4)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.shimmer(loading = comic == null),
                        imageVector = Heart,
                        contentDescription = stringResource(
                            id = R.string.screen_home_comic_menu_item_likes
                        )
                    )

                    Box(
                        modifier = Modifier
                            .padding(horizontal = Spacing_4)
                            .fillMaxWidth(fraction = when {
                                comic == null -> HalfSize
                                else -> FullSize
                            })
                            .shimmer(loading = comic == null),
                    ) {
                        Text(
                            text = comic?.likes.toString()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ComicDetailCardPreview() {
    var comic by remember {
        mutableStateOf<ComicModel?>(null)
    }

    Column {
        ComicDetailCard(
            comic = comic
        )

        Button(
            onClick = {
                comic = when (comic) {
                    null -> {
                        ComicModel(
                            id = "1",
                            title = "Title",
                            description = "Description",
                            thumb = "https://storage-b.picacomic.com/static/f6ae8c0f-c79e-40f0-aa08-e007a576330f.jpg",
                            author = "Author",
                            chineseTeam = "Chinese Team",
                            categoryList = listOf(ComicCategoryModel(PicaComicCategory.Cosplay)),
                            tagList = listOf(PicaComicCategory.Cosplay.raw),
                            pages = 1,
                            episodes = 1,
                            finished = false,
                            create = Clock.System.now().toEpochMilliseconds(),
                            update = Clock.System.now().toEpochMilliseconds(),
                            views = 1000,
                            likes = 100,
                            comments = 10,
                            favourite = false,
                            like = false,
                            comment = false,
                        )
                    }
                    else -> { null }
                }
            }
        ) {
            Text(text = "Switch State")
        }
    }

}
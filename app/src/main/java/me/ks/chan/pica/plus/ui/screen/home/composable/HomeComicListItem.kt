package me.ks.chan.pica.plus.ui.screen.home.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.repository.pica.PicaComicCategory
import me.ks.chan.pica.plus.ui.composable.shimmer.shimmer
import me.ks.chan.pica.plus.ui.icon.round.ArrowBack
import me.ks.chan.pica.plus.ui.icon.round.ArrowRight
import me.ks.chan.pica.plus.ui.icon.round.Category
import me.ks.chan.pica.plus.ui.icon.round.Close
import me.ks.chan.pica.plus.ui.icon.round.Done
import me.ks.chan.pica.plus.ui.icon.round.Heart
import me.ks.chan.pica.plus.ui.icon.round.MoreHoriz
import me.ks.chan.pica.plus.ui.icon.round.Pages
import me.ks.chan.pica.plus.ui.icon.round.Refresh
import me.ks.chan.pica.plus.ui.icon.round.Update
import me.ks.chan.pica.plus.ui.icon.round.Visibility
import me.ks.chan.pica.plus.ui.screen.home.viewmodel.HomeComicCategoryModel
import me.ks.chan.pica.plus.ui.screen.home.viewmodel.HomeComicModel
import me.ks.chan.pica.plus.ui.theme.Corner_12
import me.ks.chan.pica.plus.ui.theme.Duration_Medium2
import me.ks.chan.pica.plus.ui.theme.Duration_Short4
import me.ks.chan.pica.plus.ui.theme.Sizing_56
import me.ks.chan.pica.plus.util.androidx.compose.FalseState

@Composable
fun LazyItemScope.HomeComicListItem(
    comic: HomeComicModel,
    onClick: (String) -> Unit,
) {
    var dropDownMenu by remember(::FalseState)

    @OptIn(ExperimentalFoundationApi::class)
    ListItem(
        modifier = Modifier
            .animateItemPlacement()
            .combinedClickable(
                onLongClick = { dropDownMenu = true },
                onClick = { onClick(comic.id) },
            ),
        leadingContent = {
            ComicThumbImage(
                title = comic.title,
                thumb = comic.thumb,
            )
        },
        headlineContent = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = comic.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        supportingContent = {
            if (!comic.author.isNullOrBlank()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = comic.author,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        trailingContent = {
            IconButton(onClick = { dropDownMenu = true }) {
                Icon(
                    imageVector = MoreHoriz,
                    contentDescription = stringResource(
                        id = R.string.screen_home_comic_item_trailing
                    )
                )
            }

            ComicDropDownMenu(
                expanded = dropDownMenu,
                onDismissRequest = { dropDownMenu = false },
                comic = comic,
            )
        }
    )
}

private const val RetryHash = "retry_hash"
@Composable
private fun ComicThumbImage(
    title: String,
    thumb: String,
) {
    var retryHash by remember { mutableIntStateOf(0) }

    SubcomposeAsyncImage(
        modifier = Modifier
            .size(Sizing_56)
            .clip(RoundedCornerShape(size = Corner_12)),
        model = ImageRequest.Builder(LocalContext.current)
            .data(thumb)
            // https://github.com/coil-kt/coil/issues/884#issuecomment-975932886
            .setParameter(RetryHash, retryHash)
            .crossfade(durationMillis = Duration_Short4)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = title,
        loading = {
            Box(
                modifier = Modifier
                    .size(Sizing_56)
                    .shimmer()
            )
        },
        error = {
            IconButton(onClick = { retryHash++ }) {
                Icon(
                    imageVector = Refresh,
                    contentDescription = stringResource(
                        id = R.string.action_retry
                    )
                )
            }
        }
    )
}

private const val ComicDropDownMenuAnimatedContent = "ComicDropDownMenuAnimatedContent"
@Composable
private fun ComicDropDownMenu(
    expanded: Boolean,
    comic: HomeComicModel,
    onDismissRequest: () -> Unit,
) {
    var categories by remember(::FalseState)

    DropdownMenu(
        modifier = Modifier.animateContentSize(),
        expanded = expanded,
        onDismissRequest = {
            onDismissRequest()
            categories = false
        },
    ) {
        AnimatedContent(
            targetState = categories,
            transitionSpec = {
                val transitionDirection = when {
                    categories -> SlideDirection.Start
                    else -> SlideDirection.End
                }
                slideIntoContainer(
                    towards = transitionDirection,
                    animationSpec = tween(durationMillis = Duration_Medium2)
                ).plus(
                    fadeIn(
                        animationSpec = tween(durationMillis = Duration_Medium2)
                    )
                ).togetherWith(
                    slideOutOfContainer(
                        towards = transitionDirection,
                        animationSpec = tween(durationMillis = Duration_Medium2)
                    ).plus(
                        fadeOut(animationSpec = tween(durationMillis = Duration_Medium2))
                    )
                )
            },
            label = ComicDropDownMenuAnimatedContent,
        ) { isCategories ->
            when {
                !isCategories -> {
                    ComicDropDownMenuDefault(
                        comic = comic,
                        showCategories = { categories = true },
                    )
                }
                else -> {
                    ComicDropDownMenuCategories(
                        categoryList = comic.categoryList,
                        exit = { categories = false },
                    )
                }
            }
        }
    }
}

@Composable
private fun ComicDropDownMenuDefault(
    comic: HomeComicModel,
    showCategories: () -> Unit,
) {
    Column {
        DropdownMenuItem(
            onClick = {},
            leadingIcon = {
                Icon(
                    imageVector = Visibility,
                    contentDescription = stringResource(
                        id = R.string.screen_home_comic_menu_item_views
                    )
                )
            },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.screen_home_comic_menu_item_views
                    )
                )
            },
            trailingIcon = {
                Text(text = comic.views.toString())
            }
        )

        DropdownMenuItem(
            onClick = {},
            leadingIcon = {
                Icon(
                    imageVector = Heart,
                    contentDescription = stringResource(
                        id = R.string.screen_home_comic_menu_item_likes
                    )
                )
            },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.screen_home_comic_menu_item_likes
                    )
                )
            },
            trailingIcon = {
                Text(text = comic.likes.toString())
            }
        )

        HorizontalDivider()

        DropdownMenuItem(
            onClick = {},
            leadingIcon = {
                Icon(
                    imageVector = Pages,
                    contentDescription = stringResource(
                        id = R.string.screen_home_comic_menu_item_pages
                    )
                )
            },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.screen_home_comic_menu_item_pages
                    )
                )
            },
            trailingIcon = {
                Text(text = comic.views.toString())
            }
        )

        DropdownMenuItem(
            onClick = {},
            leadingIcon = {
                Icon(
                    imageVector = Update,
                    contentDescription = stringResource(
                        id = R.string.screen_home_comic_menu_item_serializing
                    )
                )
            },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.screen_home_comic_menu_item_serializing
                    )
                )
            },
            trailingIcon = {
                val (textResId, icon) = when {
                    comic.serializing -> {
                        R.string.screen_home_comic_menu_item_serializing to Done
                    }
                    else -> {
                        R.string.screen_home_comic_menu_item_serializing to Close
                    }
                }

                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(id = textResId)
                )
            }
        )

        HorizontalDivider()

        DropdownMenuItem(
            onClick = showCategories,
            leadingIcon = {
                Icon(
                    imageVector = Category,
                    contentDescription = stringResource(
                        id = R.string.screen_home_comic_menu_item_categories
                    )
                )
            },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.screen_home_comic_menu_item_categories
                    )
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = ArrowRight,
                    contentDescription = stringResource(
                        id = R.string.screen_home_comic_menu_item_categories_trailing
                    )
                )
            }
        )
    }
}

@Composable
private fun ComicDropDownMenuCategories(
    categoryList: List<HomeComicCategoryModel>,
    exit: () -> Unit,
) {
    Column {
        DropdownMenuItem(
            onClick = exit,
            leadingIcon = {
                Icon(
                    imageVector = ArrowBack,
                    contentDescription = stringResource(id = R.string.action_back)
                )
            },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.screen_home_comic_menu_item_categories
                    )
                )
            },
            trailingIcon = {},
        )

        HorizontalDivider()

        categoryList.forEach { category ->
            DropdownMenuItem(
                onClick = {},
                text = { Text(text = category.title) },
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            HomeComicListItem(
                comic = HomeComicModel(
                    id = "1",
                    title = "Title",
                    author = "Author",
                    thumb = "https://example.com/thumb.jpg",
                    categoryList = listOf(
                        HomeComicCategoryModel(PicaComicCategory.Lesbian),
                        HomeComicCategoryModel(PicaComicCategory.PureLove),
                    ),
                    pages = 20,
                    serializing = true,
                    views = 100000,
                    likes = 1000,
                ),
                onClick = {},
            )
        }
    }
}
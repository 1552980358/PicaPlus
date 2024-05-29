package me.ks.chan.pica.plus.ui.screen.comic.page.detail.composable

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter.State
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.composable.shimmer.shimmer
import me.ks.chan.pica.plus.ui.icon.round.KeyboardArrowDown
import me.ks.chan.pica.plus.ui.icon.round.KeyboardArrowUp
import me.ks.chan.pica.plus.ui.icon.round.Refresh
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicUploaderGenderModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicUploaderModel
import me.ks.chan.pica.plus.ui.theme.Duration_Medium2
import me.ks.chan.pica.plus.ui.theme.Duration_Short4
import me.ks.chan.pica.plus.ui.theme.Sizing_40
import me.ks.chan.pica.plus.ui.theme.Spacing_4
import me.ks.chan.pica.plus.ui.theme.Spacing_8
import me.ks.chan.pica.plus.util.coil.rememberImageRequestRetryHelper
import me.ks.chan.pica.plus.util.coil.retryWith
import me.ks.chan.pica.plus.util.androidx.compose.FalseState
import me.ks.chan.pica.plus.util.kotlin.Blank

@Composable
fun ComicDetailUploaderCard(
    uploader: ComicUploaderModel?,
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        var expand by remember(::FalseState)
        val enabled = uploader != null

        ElevatedCard(
            modifier = Modifier.animateContentSize(),
            onClick = { expand = !expand },
            enabled = enabled,
            elevation = CardDefaults.cardElevation()
        ) {
            ListItem(
                leadingContent = {
                    ComicUploaderAvatar(uploader = uploader)
                },
                headlineContent = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shimmer(loading = uploader == null),
                        text = uploader?.nickname ?: Blank,
                    )
                },
                supportingContent = {
                    if (uploader == null || !uploader.slogan.isNullOrBlank()) {
                        AnimatedVisibility(
                            visible = !expand,
                            enter = slideInVertically(
                                animationSpec = tween(durationMillis = Duration_Short4),
                                initialOffsetY = { it },
                            ) + fadeIn(animationSpec = tween(durationMillis = Duration_Short4)),
                            exit = slideOutVertically(
                                animationSpec = tween(durationMillis = Duration_Medium2),
                                targetOffsetY = { it }
                            ) + fadeOut(animationSpec = tween(durationMillis = Duration_Short4)),
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shimmer(
                                        loading = uploader == null,
                                        paddings = PaddingValues(top = Spacing_4),
                                    ),
                                text = uploader?.slogan ?: Blank,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                            )
                        }
                    }
                },
                trailingContent = {
                    IconButton(
                        onClick = { expand = !expand },
                        enabled = enabled,
                    ) {
                        Crossfade(
                            targetState = expand,
                            label = "ComicScreen.ComicDetailPage.UploaderButton.Crossfade",
                        ) { expanded ->
                            Icon(
                                imageVector = when {
                                    expanded -> { KeyboardArrowUp }
                                    else -> { KeyboardArrowDown }
                                },
                                contentDescription = stringResource(
                                    id = when {
                                        expand -> { R.string.screen_comic_detail_uploader_button_content_collapse }
                                        else -> { R.string.screen_comic_detail_uploader_button_content_expand }
                                    }
                                )
                            )
                        }
                    }
                }
            )
        }

        Box(
            modifier = Modifier.animateContentSize(),
        ) {
            AnimatedContent(
                targetState = expand,
                transitionSpec = {
                    when {
                        targetState -> {
                            EnterTransition.None.togetherWith(ExitTransition.None)
                        }
                        else -> {
                            fadeIn().togetherWith(fadeOut())
                        }
                    }
                },
                label = "ComicScreen.ComicDetailPage.UploaderCard.DetailColumn.AnimatedContent",
            ) { showContent -> 
                when {
                    showContent -> {
                        if (uploader != null) {
                            Column (
                                modifier = Modifier.padding(top = Spacing_8),
                            ) {
                                if (!uploader.slogan.isNullOrBlank()) {
                                    ComicUploaderDetailItem(
                                        itemTitleResId = R.string.screen_comic_detail_uploader_slogan_title,
                                        itemValue = uploader.slogan,
                                    )
                                }

                                ComicUploaderDetailItem(
                                    itemTitleResId = R.string.screen_comic_detail_uploader_level_title,
                                    itemValue = uploader.level.toString(),
                                )

                                ComicUploaderDetailItem(
                                    itemTitleResId = R.string.screen_comic_detail_uploader_gender_title,
                                    itemValue = stringResource(id = uploader.gender.textResId),
                                )

                                if (!uploader.title.isNullOrBlank()) {
                                    ComicUploaderDetailItem(
                                        itemTitleResId = R.string.screen_comic_detail_uploader_title_title,
                                        itemValue = uploader.title,
                                    )
                                }

                                if (!uploader.role.isNullOrBlank()) {
                                    ComicUploaderDetailItem(
                                        itemTitleResId = R.string.screen_comic_detail_uploader_role_title,
                                        itemValue = uploader.role,
                                    )
                                }
                            }
                        }
                    }
                    else -> {
                        Box(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Composable
private fun ComicUploaderAvatar(uploader: ComicUploaderModel?) {
    val imageRequestRetryHelper = rememberImageRequestRetryHelper()
    val asyncImagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uploader?.avatar)
            .size(LocalDensity.current.run { Sizing_40.roundToPx() })
            .retryWith(imageRequestRetryHelper = imageRequestRetryHelper)
            .build()
    )

    Box(
        modifier = Modifier
            .size(size = Sizing_40)
            .clip(shape = CircleShape)
            .shimmer(
                loading = (
                    uploader == null ||
                        asyncImagePainter.state is State.Loading
                    ),
            ),
    ) {
        when {
            asyncImagePainter.state is State.Success -> {
                Image(
                    painter = asyncImagePainter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            uploader != null && asyncImagePainter.state is State.Error -> {
                IconButton(onClick = imageRequestRetryHelper::retry) {
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
}

@Composable
private fun ComicUploaderDetailItem(
    @StringRes itemTitleResId: Int,
    itemValue: String,
) {
    ListItem(
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
        ),
        overlineContent = {
            Text(text = stringResource(id = itemTitleResId))
        },
        headlineContent = {
            SelectionContainer {
                Text(text = itemValue)
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    var uploader0 by remember {
        mutableStateOf<ComicUploaderModel?>(
            ComicUploaderModel(
                id = "1",
                nickname = "Nickname",
                avatar = "",
                gender = ComicUploaderGenderModel.Gentleman,
                experience = 1,
                level = 1,
                characterList = listOf(),
                role = "role",
                title = "title",
                slogan = "Slogan, Slogan, Slogan, Slogan, Slogan",
            )
        )
    }
    var uploader1 by remember {
        mutableStateOf<ComicUploaderModel?>(null)
    }
    var switch by remember(::FalseState)

    Column(
        verticalArrangement = Arrangement.spacedBy(space = Spacing_8),
    ) {
        ComicDetailUploaderCard(uploader0)
        ComicDetailUploaderCard(uploader1)

        Button(
            onClick = {
                switch = !switch

                when {
                    switch -> {
                        uploader0 = null
                        uploader1 = ComicUploaderModel(
                            id = "2",
                            nickname = "Nickname",
                            avatar = "",
                            gender = ComicUploaderGenderModel.Lady,
                            experience = 1,
                            level = 1,
                            characterList = listOf(),
                            role = null,
                            title = null,
                            slogan = null,
                        )
                    }
                    else -> {
                        uploader0 = ComicUploaderModel(
                            id = "1",
                            nickname = "Nickname",
                            avatar = "",
                            gender = ComicUploaderGenderModel.Gentleman,
                            experience = 1,
                            level = 1,
                            characterList = listOf(),
                            role = "role",
                            title = "title",
                            slogan = "Slogan, Slogan, Slogan, Slogan, Slogan",
                        )
                        uploader1 = null
                    }
                }
            }
        ) {
            Text(text = "Trigger Preview State")
        }
    }
}
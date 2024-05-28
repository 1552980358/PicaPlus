package me.ks.chan.pica.plus.ui.screen.comic.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.datetime.Clock
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.repository.pica.PicaComicCategory
import me.ks.chan.pica.plus.ui.icon.filled.Heart as HeartFilled
import androidx.compose.material3.BottomAppBar
import me.ks.chan.pica.plus.ui.icon.filled.Star as StarFilled
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.ui.graphics.vector.ImageVector
import me.ks.chan.pica.plus.ui.icon.round.Star
import me.ks.chan.pica.plus.ui.icon.round.Heart

import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicCategoryModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicModel

@Composable
fun ComicBottomAppBarActions(
    comic: ComicModel,
) {
    ComicBottomAppBarAction(
        onClick = { /*TODO*/ },
        targetState = comic.like,
        trueIcon = ::HeartFilled::get,
        falseIcon = ::Heart::get,
        contentDescriptionResId = R.string.screen_comic_bottom_bar_action_favourite
    )

    ComicBottomAppBarAction(
        onClick = { /*TODO*/ },
        targetState = comic.favourite,
        trueIcon = ::StarFilled::get,
        falseIcon = ::Star::get,
        contentDescriptionResId = R.string.screen_comic_bottom_bar_action_favourite
    )
}

private const val ComicBottomAppBarActionCrossFade = "ComicBottomAppBarActionCrossFade"
@Composable
private fun ComicBottomAppBarAction(
    onClick: () -> Unit,
    targetState: Boolean,
    trueIcon: () -> ImageVector,
    falseIcon: () -> ImageVector,
    @StringRes
    contentDescriptionResId: Int,
) {
    IconButton(onClick = onClick) {
        Crossfade(
            targetState = targetState,
            label = ComicBottomAppBarActionCrossFade,
        ) { state ->
            Icon(
                imageVector = when {
                    state -> { trueIcon() }
                    else -> { falseIcon() }
                },
                contentDescription = stringResource(id = contentDescriptionResId)
            )
        }
    }
}

@Preview
@Composable
private fun Preview(modifier: Modifier = Modifier) {
    BottomAppBar(
        actions = {
            ComicBottomAppBarActions(
                comic = ComicModel(
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
                    views = 1,
                    likes = 1,
                    comments = 1,
                    favourite = false,
                    like = false,
                    comment = false,
                )
            )
        }
    )
}
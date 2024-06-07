package me.ks.chan.pica.plus.ui.screen.comic.page.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.datetime.Clock
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.repository.pica.PicaComicCategory
import me.ks.chan.pica.plus.ui.composable.scaffold.ProvideTopAppBarActions
import me.ks.chan.pica.plus.ui.icon.round.Comment
import me.ks.chan.pica.plus.ui.screen.comic.page.detail.composable.ComicDetailCard
import me.ks.chan.pica.plus.ui.screen.comic.page.detail.composable.ComicDetailDescription
import me.ks.chan.pica.plus.ui.screen.comic.page.detail.composable.ComicPropertyListGroup
import me.ks.chan.pica.plus.ui.screen.comic.page.detail.composable.ComicDetailUploaderCard
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicCategoryModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicDetailState
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicUploaderGenderModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicUploaderModel
import me.ks.chan.pica.plus.ui.theme.Spacing_16
import me.ks.chan.pica.plus.ui.theme.Spacing_8
import me.ks.chan.pica.plus.util.kotlin.observe

@Composable
fun ComicDetailPage(
    detailState: ComicDetailState,
    snackbarHostState: SnackbarHostState,
    collectRepository: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = detailState) {
        if (detailState is ComicDetailState.Error) {
            val snackbarResult = snackbarHostState.showSnackbar(
                message = context.getString(detailState.messageResId),
                actionLabel = R.string.action_retry
                    .takeIf { detailState !is ComicDetailState.Error.UnderReview }
                    ?.let(context::getString),
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite,
            )
            when (snackbarResult) {
                SnackbarResult.Dismissed -> {}
                SnackbarResult.ActionPerformed -> { collectRepository() }
            }
        }
    }

    if (detailState is ComicDetailState.Success) {
        ProvideTopAppBarActions {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Comment,
                    contentDescription = stringResource(
                        id = R.string.screen_comic_detail_top_bar_action_comment
                    )
                )
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Spacing_16),
        verticalArrangement = Arrangement.spacedBy(space = Spacing_8),
        contentPadding = PaddingValues(vertical = Spacing_8),
    ) {
        item {
            ComicDetailCard(
                comic = detailState.observe(ComicDetailState.Success::comic)
            )
        }

        item {
            ComicDetailUploaderCard(
                uploader = detailState.observe(ComicDetailState.Success::uploader)
            )
        }

        if (
            detailState is ComicDetailState.Loading ||
            detailState is ComicDetailState.Error ||
            !detailState.observe(ComicDetailState.Success::comic)
                ?.description
                .isNullOrBlank()
        ) {
            item {
                ComicDetailDescription(
                    description = detailState.observe(ComicDetailState.Success::comic)
                        ?.description
                )
            }
        }

        item {
            ComicPropertyListGroup(
                itemList = detailState.observe(ComicDetailState.Success::comic)?.categoryList,
                titleResId = R.string.screen_comic_detail_category_title,
                label = { item -> Text(text = item.title) },
            )
        }

        if (
            detailState is ComicDetailState.Loading ||
            detailState is ComicDetailState.Error ||
            detailState.observe(ComicDetailState.Success::comic)?.tagList?.isNotEmpty() == true
        ) {
            item {
                ComicPropertyListGroup(
                    itemList = detailState.observe(ComicDetailState.Success::comic)?.tagList,
                    titleResId = R.string.screen_comic_detail_tag_title,
                    label = { item -> Text(text = item) },
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val snackbarHostState = remember { SnackbarHostState() }
    ComicDetailPage(
        detailState = ComicDetailState.Success(
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
            ),
            uploader = ComicUploaderModel(
                id = "1",
                nickname = "Nickname",
                avatar = "",
                gender = ComicUploaderGenderModel.Gentleman,
                experience = 1,
                level = 1,
                characterList = listOf(),
                role = null,
                title = null,
                slogan = null,
            )
        ),
        snackbarHostState = snackbarHostState,
        collectRepository = {},
    )
}
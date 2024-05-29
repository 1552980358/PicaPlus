package me.ks.chan.pica.plus.ui.screen.comic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.Clock
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.repository.pica.PicaComicCategory
import me.ks.chan.pica.plus.ui.composable.scaffold.LocalScaffoldDispatcher
import me.ks.chan.pica.plus.ui.icon.filled.PlayArrow
import me.ks.chan.pica.plus.ui.icon.round.ArrowBack
import me.ks.chan.pica.plus.ui.screen.comic.composable.ComicBottomAppBarActions
import me.ks.chan.pica.plus.ui.screen.comic.page.detail.ComicDetailPage
import me.ks.chan.pica.plus.ui.screen.comic.page.episode.ComicEpisodePage
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicCategoryModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicDetailState
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicEpisodeModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicEpisodeState
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicTab
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicUploaderGenderModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicUploaderModel
import me.ks.chan.pica.plus.ui.theme.Duration_Long2
import me.ks.chan.pica.plus.ui.theme.Duration_Medium4
import me.ks.chan.pica.plus.util.androidx.compose.ZeroState

@Composable
fun ComicScreen(
    comic: String,
) {
    val viewModel = comicViewModel(comic = comic)

    val detailState by viewModel.detailState.collectAsStateWithLifecycle()
    val episodeState by viewModel.episodeState.collectAsStateWithLifecycle()

    ComicContent(
        detailState = detailState,
        episodeState = episodeState,
        collectDetailRepository = viewModel::collectDetailRepository,
        collectEpisodeRepository = viewModel::collectEpisodeRepository,
    )
}

@Composable
private fun ComicContent(
    detailState: ComicDetailState,
    episodeState: ComicEpisodeState,
    collectDetailRepository: () -> Unit,
    collectEpisodeRepository: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var selectedTabIndex by remember(::ZeroState)
    @OptIn(ExperimentalFoundationApi::class)
    val pagerState = rememberPagerState { ComicTab.entries.size }

    LaunchedEffect(key1 = selectedTabIndex) {
        @OptIn(ExperimentalFoundationApi::class)
        if (pagerState.currentPage != selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
    }
    @OptIn(ExperimentalFoundationApi::class)
    LaunchedEffect(key1 = pagerState.currentPage) {
        if (selectedTabIndex != pagerState.currentPage) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Scaffold(
        topBar = {
            ComicTopBars(
                state = detailState,
                selectedTabIndex = selectedTabIndex,
                updateSelectedTabIndex = { selectedTabIndex = it },
            )
        },
        bottomBar = { ComicBottomAppBar(state = detailState) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        val paddingTop by animateDpAsState(
            targetValue = innerPadding.calculateTopPadding(),
            label = "ComicScreen.Scaffold.InnerPadding.Top",
        )
        val paddingBottom by animateDpAsState(
            targetValue = innerPadding.calculateBottomPadding(),
            label = "ComicScreen.Scaffold.InnerPadding.Bottom",
        )

        @OptIn(ExperimentalFoundationApi::class)
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop, bottom = paddingBottom),
            state = pagerState,
            userScrollEnabled = detailState is ComicDetailState.Success,
        ) { page ->
            when (page) {
                0 -> {
                    ComicDetailPage(
                        detailState = detailState,
                        snackbarHostState = snackbarHostState,
                        collectRepository = collectDetailRepository,
                    )
                }
                1 -> {
                    ComicEpisodePage(
                        episodeState = episodeState,
                        snackbarHostState = snackbarHostState,
                        collectEpisodeRepository = collectEpisodeRepository,
                    )
                }
            }
        }
    }
}

@Composable
private fun ComicTopBars(
    state: ComicDetailState,
    selectedTabIndex: Int,
    updateSelectedTabIndex: (Int) -> Unit,
) {
    Column {
        @OptIn(ExperimentalMaterial3Api::class)
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.action_back
                        )
                    )
                }
            },
            title = {
                Text(text = stringResource(id = R.string.screen_comic_top_bar_title))
            },
            actions = {
                val scaffoldDispatcher = LocalScaffoldDispatcher.current
                scaffoldDispatcher.TopAppBarActions(this)
            }
        )

        AnimatedVisibility(
            visible = state is ComicDetailState.Success,
            enter = slideInVertically(
                animationSpec = tween(
                    durationMillis = Duration_Medium4,
                ),
                initialOffsetY = { -it },
            ),
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                ComicTab.entries.forEachIndexed { index, comicPage ->
                    val selected = selectedTabIndex == index
                    Tab(
                        selected = selected,
                        onClick = { updateSelectedTabIndex(index) },
                        icon = {
                            Icon(
                                imageVector = when {
                                    selected -> { comicPage.iconSelected }
                                    else -> { comicPage.iconDefault }
                                },
                                contentDescription = stringResource(id = comicPage.labelResId)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ComicBottomAppBar(state: ComicDetailState) {
    AnimatedVisibility(
        visible = state is ComicDetailState.Success,
        enter = slideInVertically(
            animationSpec = tween(
                delayMillis = Duration_Long2,
                durationMillis = Duration_Medium4,
            ),
            initialOffsetY = { it },
        ),
    ) {
        BottomAppBar(
            actions = {
                // Secure check: Should be always true
                if (state is ComicDetailState.Success) {
                    ComicBottomAppBarActions(comic = state.comic)
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                ) {
                    Icon(
                        imageVector = PlayArrow,
                        contentDescription = stringResource(
                            id = R.string.screen_comic_bottom_bar_fab_read
                        )
                    )
                }
            },
        )
    }
}

@Preview
@Composable
fun ComicPreview() {
    ComicContent(
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
        episodeState = ComicEpisodeState.Success(
            episodeList = listOf(
                ComicEpisodeModel(
                    index = 1,
                    id = "1",
                    title = "Title",
                    update = Clock.System.now().toEpochMilliseconds(),
                )
            )
        ),
        collectDetailRepository = {},
        collectEpisodeRepository = {},
    )
}

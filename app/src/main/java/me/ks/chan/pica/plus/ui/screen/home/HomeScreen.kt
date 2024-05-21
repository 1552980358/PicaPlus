package me.ks.chan.pica.plus.ui.screen.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.StateObject
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.composable.scaffold.ProvideNavBarItemSecondaryClick
import me.ks.chan.pica.plus.ui.icon.round.Search
import me.ks.chan.pica.plus.ui.screen.home.composable.HomeComicListItem
import me.ks.chan.pica.plus.ui.screen.home.viewmodel.HomeComicModel
import me.ks.chan.pica.plus.ui.screen.home.viewmodel.HomeState
import me.ks.chan.pica.plus.ui.theme.Duration_ExtraLong4
import me.ks.chan.pica.plus.ui.theme.Duration_Long2
import me.ks.chan.pica.plus.ui.theme.Spacing_8
import me.ks.chan.pica.plus.util.compose.FillSpace

@Composable
fun HomeScreen() {
    val viewModel = homeViewModel

    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeContent(
        state = state,
        comicList = viewModel.comicList,
        updateState = viewModel::updateState,
        updateComicList = viewModel::updateComicList,
        refreshComicList = viewModel::refreshComicList,
    )
}

private const val HomeComicListLoadingStateAnimation = "HomeComicListLoadingStateAnimation"
@Composable
private fun <L> HomeContent(
    state: HomeState,
    comicList: L,
    updateState: (HomeState) -> Unit,
    updateComicList: () -> Unit,
    refreshComicList: () -> Unit,
) where L: List<HomeComicModel>, L: StateObject {
    @OptIn(ExperimentalMaterial3Api::class)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val lazyListState = rememberLazyListState()
    LaunchedEffect(key1 = lazyListState.canScrollForward) {
        if (state !is HomeState.Loading) {
            updateComicList()
        }
    }

    val coroutineScope = rememberCoroutineScope()
    ProvideNavBarItemSecondaryClick {
        when {
            lazyListState.canScrollBackward -> {
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(index = 0)
                    @OptIn(ExperimentalMaterial3Api::class)
                    scrollBehavior.state.heightOffset -= scrollBehavior.state.heightOffsetLimit
                }
            }
            else -> { refreshComicList() }
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(key1 = state) {
        if (state is HomeState.Error) {
            val result = snackbarHostState.showSnackbar(
                message = context.getString(state.messageResId),
                actionLabel = context.getString(R.string.action_retry),
                withDismissAction = true,
                duration = SnackbarDuration.Long,
            )
            if (result == SnackbarResult.ActionPerformed) {
                updateComicList()
            }
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    updateComicList()
                }
                else -> {
                    updateState(HomeState.Pending)
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            MediumTopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.screen_home_top_bar_title
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Search,
                            contentDescription = stringResource(
                                id = R.string.action_search
                            )
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->

        AnimatedContent(
            targetState = comicList.isNotEmpty(),
            transitionSpec = {
                val enterTransition = when {
                    targetState -> {
                        slideIntoContainer(
                            towards = SlideDirection.Up,
                            animationSpec = tween(
                                durationMillis = Duration_Long2,
                                delayMillis = Duration_ExtraLong4,
                            )
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = Duration_Long2,
                                delayMillis = Duration_ExtraLong4,
                            )
                        )
                    }
                    else -> {
                        fadeIn(animationSpec = tween(durationMillis = Duration_Long2))
                    }
                }

                val exitTransition = when {
                    targetState -> {
                        fadeOut(
                            animationSpec = tween(
                                durationMillis = Duration_Long2,
                                delayMillis = Duration_ExtraLong4,
                            )
                        )
                    }
                    else -> {
                        fadeOut(animationSpec = tween(durationMillis = Duration_Long2))
                    }
                }

                enterTransition.togetherWith(exitTransition)
            },
            label = HomeComicListLoadingStateAnimation,
        ) { isComicListNotEmpty ->
            when {
                isComicListNotEmpty -> {
                    @OptIn(ExperimentalMaterial3Api::class)
                    LazyColumn(
                        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                        state = lazyListState,
                        contentPadding = innerPadding,
                    ) {
                        items(items = comicList, key = HomeComicModel::id) { comic ->
                            HomeComicListItem(comic = comic)
                        }

                        item {
                            if (comicList.isNotEmpty()) {
                                Row(
                                    modifier = Modifier.padding(vertical = Spacing_8)
                                ) {
                                    Spacer(modifier = Modifier.weight(FillSpace))

                                    CircularProgressIndicator()

                                    Spacer(modifier = Modifier.weight(FillSpace))
                                }
                            }
                        }
                    }
                }
                else -> {
                    Column {
                        Spacer(modifier = Modifier.weight(FillSpace))

                        Row {
                            Spacer(modifier = Modifier.weight(FillSpace))

                            CircularProgressIndicator()

                            Spacer(modifier = Modifier.weight(FillSpace))
                        }

                        Spacer(modifier = Modifier.weight(FillSpace))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    var state by remember { mutableStateOf<HomeState>(HomeState.Loading) }
    val comicList = remember { mutableStateListOf<HomeComicModel>() }

    HomeContent(
        state = state,
        comicList = comicList,
        updateState = {},
        updateComicList = {},
        refreshComicList = {},
    )
}
package me.ks.chan.pica.plus.ui.screen.comic.page.episode

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.datetime.Clock
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.composable.scaffold.ProvideTopAppBarActions
import me.ks.chan.pica.plus.ui.screen.comic.page.episode.composable.ComicEpisodeListItem
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicEpisodeModel
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicEpisodeState
import me.ks.chan.pica.plus.ui.theme.Spacing_8

@Composable
fun ComicEpisodePage(
    episodeState: ComicEpisodeState,
    snackbarHostState: SnackbarHostState,
    collectEpisodeRepository: () -> Unit,
) {
    if (episodeState is ComicEpisodeState.Success) {
        ProvideTopAppBarActions { /*TODO*/ }
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = episodeState) {
        if (episodeState is ComicEpisodeState.Error) {
            snackbarHostState.showSnackbar(
                message = context.getString(episodeState.messageResId),
                actionLabel = context.getString(R.string.action_retry),
            )
            collectEpisodeRepository()
        }
    }

    if (episodeState is ComicEpisodeState.Success) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = Spacing_8),
        ) {
            items(items = episodeState.episodeList) { episode ->
                ComicEpisodeListItem(
                    episode = episode,
                    onClick = { /*TODO*/ },
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val snackbarHostState = remember(::SnackbarHostState)

    ComicEpisodePage(
        episodeState = ComicEpisodeState.Success(
            episodeList = List(10) { i ->
                val index = i.inc()
                ComicEpisodeModel(
                    index = index.inc(),
                    id = index.toString(),
                    title = "Title $index",
                    update = Clock.System.now().toEpochMilliseconds(),
                )
            }.asReversed()
        ),
        snackbarHostState = snackbarHostState,
        collectEpisodeRepository = {},
    )
}
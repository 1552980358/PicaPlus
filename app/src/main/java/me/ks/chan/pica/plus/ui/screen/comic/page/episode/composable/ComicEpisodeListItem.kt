package me.ks.chan.pica.plus.ui.screen.comic.page.episode.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import me.ks.chan.pica.plus.ui.screen.comic.viewmodel.ComicEpisodeModel
import me.ks.chan.pica.plus.ui.theme.Sizing_40
import me.ks.chan.pica.plus.util.kotlin.Colon
import me.ks.chan.pica.plus.util.kotlin.Hyphen
import me.ks.chan.pica.plus.util.kotlin.Slash
import me.ks.chan.pica.plus.util.kotlin.Space

@Composable
fun ComicEpisodeListItem(
    episode: ComicEpisodeModel,
    onClick: (String) -> Unit,
) {
    ListItem(
        modifier = Modifier.clickable { onClick(episode.id) },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(size = Sizing_40)
                    .background(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = episode.index.toString())
            }
        },
        headlineContent = { Text(text = episode.title) },
        supportingContent = {
            Text(text = episode.update.let(::asTimeText))
        },
    )
}

private fun asTimeText(timeMillis: Long): String {
    return timeMillis.let(Instant::fromEpochMilliseconds)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .format(
            format = LocalDateTime.Format {
                // 2024/02/28 - 10:01:10
                year()
                char(Slash)
                monthNumber()
                char(Slash)
                dayOfMonth()
                char(Space)
                char(Hyphen)
                char(Space)
                hour()
                char(Colon)
                minute()
                char(Colon)
                second()
            }
        )
}

@Preview
@Composable
private fun Preview() {
    ComicEpisodeListItem(
        onClick = {},
        episode = ComicEpisodeModel(
            index = 1,
            id = "1",
            title = "Title",
            update = Clock.System.now().toEpochMilliseconds(),
        )
    )
}
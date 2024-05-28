package me.ks.chan.pica.plus.ui.screen.comic.viewmodel

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.icon.filled.Info as InfoFilled
import me.ks.chan.pica.plus.ui.icon.filled.ViewList as ViewListFilled
import me.ks.chan.pica.plus.ui.icon.round.Info as InfoRound
import me.ks.chan.pica.plus.ui.icon.round.ViewList as ViewListRound

enum class ComicTab(
    @StringRes val labelResId: Int,
    val iconDefault: ImageVector,
    val iconSelected: ImageVector,
) {
    Detail(
        labelResId = R.string.screen_comic_tab_detail_label,
        iconDefault = InfoRound,
        iconSelected = InfoFilled,
    ),

    Episode(
        labelResId = R.string.screen_comic_tab_episode_label,
        iconDefault = ViewListRound,
        iconSelected = ViewListFilled,
    )
}
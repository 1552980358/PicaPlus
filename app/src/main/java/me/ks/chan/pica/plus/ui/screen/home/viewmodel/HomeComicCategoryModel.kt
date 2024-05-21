package me.ks.chan.pica.plus.ui.screen.home.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.ks.chan.pica.plus.repository.pica.PicaComicCategory

data class HomeComicCategoryModel(
    private val category: String,
    private val titleResId: Int? =
        PicaComicCategory.entries.find { it.raw == category }?.titleResId
) {
    val title: String
        @Composable get() = titleResId?.let { stringResource(it) } ?: category
}

/**
 * Constructor for preview usage only
 **/
fun HomeComicCategoryModel(category: PicaComicCategory): HomeComicCategoryModel {
    return HomeComicCategoryModel(category.raw, category.titleResId)
}

package me.ks.chan.pica.plus.ui.screen.categroy.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.ks.chan.pica.plus.repository.pica.PicaComicCategory
import me.ks.chan.pica.plus.util.kotlin.Blank

data class CategoryModel(
    val category: String,
    val thumb: String,
    private val picaComicCategory: PicaComicCategory? =
        PicaComicCategory.entries.find { it.raw == category }
) {

    val title: String
        @Composable get() = picaComicCategory
            ?.let { stringResource(id = it.titleResId) }
            ?: category

}

/**
 * Function implemented for preview only,
 * will be removed automatically in release build
 **/
fun CategoryModel(picaComicCategory: PicaComicCategory): CategoryModel {
    return CategoryModel(
        category = picaComicCategory.raw,
        thumb = Blank,
        picaComicCategory = picaComicCategory,
    )
}
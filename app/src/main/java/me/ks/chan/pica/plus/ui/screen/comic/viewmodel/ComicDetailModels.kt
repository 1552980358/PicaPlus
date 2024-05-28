package me.ks.chan.pica.plus.ui.screen.comic.viewmodel

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.repository.pica.PicaComicCategory

data class ComicModel(
    val id: String,
    val title: String,
    val description: String,
    val thumb: String,
    val author: String,
    val chineseTeam: String?,
    val categoryList: List<ComicCategoryModel>,
    val tagList: List<String>,
    val pages: Int,
    val episodes: Int,
    val finished: Boolean,
    val create: Long,
    val update: Long,
    val views: Int,
    val likes: Int,
    val comments: Int,
    val favourite: Boolean,
    val like: Boolean,
    val comment: Boolean,
)

data class ComicCategoryModel(
    private val category: String,
    private val titleResId: Int? =
        PicaComicCategory.entries.find { it.raw == category }?.titleResId
) {
    val title: String
        @Composable get() = titleResId?.let { stringResource(it) } ?: category
}

/**
 * Preview only constructor method
 **/
fun ComicCategoryModel(category: PicaComicCategory) =
    ComicCategoryModel(category.raw, category.titleResId)

data class ComicUploaderModel(
    val id: String,
    val nickname: String,
    val avatar: String?,
    val gender: ComicUploaderGenderModel,
    val experience: Int,
    val level: Int,
    val characterList: List<String>,
    val role: String?,
    val title: String?,
    val slogan: String?,
)

enum class ComicUploaderGenderModel(
    @StringRes
    val textResId: Int,
) {
    Gentleman(R.string.screen_comic_detail_uploader_gender_gentleman),
    Lady(R.string.screen_comic_detail_uploader_gender_lady),
    Bot(R.string.screen_comic_detail_uploader_gender_bot),
}
package me.ks.chan.pica.plus.ui.screen.home.viewmodel

data class HomeComicModel(
    val id: String,
    val title: String,
    val author: String?,
    val thumb: String,
    val categoryList: List<HomeComicCategoryModel>,
    val pages: Int,
    val serializing: Boolean,
    val likes: Int,
    val views: Int,
)
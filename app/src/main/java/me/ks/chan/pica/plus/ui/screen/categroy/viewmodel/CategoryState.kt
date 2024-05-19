package me.ks.chan.pica.plus.ui.screen.categroy.viewmodel

sealed interface CategoryState {

    data object Loading: CategoryState

    data class Success(val categoryList: List<CategoryModel>): CategoryState

    data class Error(val type: Type): CategoryState {
        enum class Type {
            Network,
            InvalidResponse,
            Unknown,
        }
    }

}
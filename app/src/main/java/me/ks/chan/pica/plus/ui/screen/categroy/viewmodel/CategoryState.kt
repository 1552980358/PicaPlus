package me.ks.chan.pica.plus.ui.screen.categroy.viewmodel

sealed interface CategoryState {

    data object Loading: CategoryState

    data class Success(val categoryList: List<CategoryModel>): CategoryState

    sealed interface Error: CategoryState {

        data object Network: Error

        data object InvalidResponse: Error

        data object InvalidState: Error

        data object Unknown: Error

    }

}
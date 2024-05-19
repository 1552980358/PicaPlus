package me.ks.chan.pica.plus.repository.pica.categories

sealed interface PicaCategoriesRepositoryResult {

    data class Success(
        val categoryList: List<Category>
    ): PicaCategoriesRepositoryResult {
        data class Category(
            val title: String,
            val thumb: String,
            val active: Boolean,
            val web: Boolean,
            val link: String?,
        )
    }

    sealed interface Error: PicaCategoriesRepositoryResult {

        data object InvalidResponse: Error

        data object InvalidState: Error

    }

}
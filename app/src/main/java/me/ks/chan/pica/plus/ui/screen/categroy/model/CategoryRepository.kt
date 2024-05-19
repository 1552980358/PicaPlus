package me.ks.chan.pica.plus.ui.screen.categroy.model

import kotlinx.coroutines.flow.catch
import kotlinx.serialization.SerializationException
import me.ks.chan.pica.plus.repository.pica.categories.PicaCategoriesRepository
import me.ks.chan.pica.plus.repository.pica.categories.PicaCategoriesRepositoryResult
import me.ks.chan.pica.plus.ui.screen.categroy.viewmodel.CategoryModel
import me.ks.chan.pica.plus.ui.screen.categroy.viewmodel.CategoryState
import okio.IOException

object CategoryRepository {

    suspend fun collect(updateState: (CategoryState) -> Unit) {
        PicaCategoriesRepository.repositoryFlow
            .catch { cause ->
                cause.let(::collectErrorState)
                    .let(updateState)
            }
            .collect { result ->
                result.let(::collectResultState)
                    .let(updateState)
            }
    }

}

private fun collectErrorState(cause: Throwable): CategoryState {
    return when (cause) {
        is IOException -> {
            CategoryState.Error.Network
        }
        is SerializationException -> {
            CategoryState.Error.InvalidResponse
        }
        else -> {
            CategoryState.Error.Unknown
        }
    }
}

private fun collectResultState(result: PicaCategoriesRepositoryResult): CategoryState {
    return when (result) {
        is PicaCategoriesRepositoryResult.Success -> {
            result.categoryList
                .filter { !it.web && it.link.isNullOrBlank() }
                .map(::asCategoryModel)
                .let(CategoryState::Success)
        }
        is PicaCategoriesRepositoryResult.Error -> {
            result.let(::asCategoryStateError)
        }
    }
}

private fun asCategoryModel(
    category: PicaCategoriesRepositoryResult.Success.Category
): CategoryModel {
    return CategoryModel(
        category = category.title,
        thumb = category.thumb,
    )
}

private fun asCategoryStateError(
    error: PicaCategoriesRepositoryResult.Error
): CategoryState.Error {
    return when (error) {
        PicaCategoriesRepositoryResult.Error.InvalidResponse -> {
            CategoryState.Error.InvalidResponse
        }
        PicaCategoriesRepositoryResult.Error.InvalidState -> {
            CategoryState.Error.InvalidState
        }
    }
}
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
    return cause.let(::asCategoryStateErrorType)
        .let(CategoryState::Error)
}

private fun asCategoryStateErrorType(cause: Throwable): CategoryState.Error.Type {
    return when (cause) {
        is IOException -> {
            CategoryState.Error.Type.Network
        }
        is SerializationException -> {
            CategoryState.Error.Type.InvalidResponse
        }
        else -> {
            CategoryState.Error.Type.Unknown
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
            result.type
                .let(::asCategoryStateErrorType)
                .let(CategoryState::Error)
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

private fun asCategoryStateErrorType(
    type: PicaCategoriesRepositoryResult.Error.Type
): CategoryState.Error.Type {
    return when (type) {
        PicaCategoriesRepositoryResult.Error.Type.InvalidResponse -> {
            CategoryState.Error.Type.InvalidResponse
        }
        PicaCategoriesRepositoryResult.Error.Type.Unknown -> {
            CategoryState.Error.Type.Unknown
        }
    }
}
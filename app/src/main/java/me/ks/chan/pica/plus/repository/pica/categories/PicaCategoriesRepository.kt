package me.ks.chan.pica.plus.repository.pica.categories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ks.chan.pica.plus.repository.pica.categories.PicaCategoriesRepositoryResult.Error
import me.ks.chan.pica.plus.repository.pica.categories.PicaCategoriesRepositoryResult.Success
import me.ks.chan.pica.plus.repository.pica.PicaImage
import me.ks.chan.pica.plus.repository.pica.PicaRepository
import me.ks.chan.pica.plus.repository.pica.PicaRepositoryDataResponse
import me.ks.chan.pica.plus.util.okhttp.RequestSuccess
import me.ks.chan.pica.plus.util.okhttp.deserialize

@Serializable
private data class Data(
    @SerialName("categories")
    val categoryList: List<Category>
) {
    @Serializable
    data class Category(
        @SerialName("_id")
        val id: String? = null,
        @SerialName("title")
        val title: String,
        @SerialName("thumb")
        val thumb: PicaImage,
        @SerialName("description")
        val description: String? = null,
        @SerialName("active")
        val active: Boolean = true,
        @SerialName("isWeb")
        val web: Boolean = false,
        @SerialName("link")
        val link: String? = null,
    )
}

@Serializable
private data class ResponseBody(
    override val code: Int,
    override val message: String,
    override val data: Data
): PicaRepositoryDataResponse<Data>()

private const val CategoriesApiPath = "categories"

object PicaCategoriesRepository {

    val repositoryFlow: Flow<PicaCategoriesRepositoryResult>
        get() = flow {
            val response = PicaRepository.get(CategoriesApiPath)

            val result = when (response.code) {
                RequestSuccess -> {
                    response.deserialize<ResponseBody>()
                        ?.data
                        ?.categoryList
                        ?.map(::Category)
                        ?.let(::Success)
                        ?: Error(Error.Type.InvalidResponse)
                }
                else -> {
                    Error(Error.Type.Unknown)
                }
            }

            emit(result)
        }

}

private fun Category(category: Data.Category): Success.Category {
    return Success.Category(
        title = category.title,
        thumb = category.thumb.url,
        active = category.active,
        web = category.web,
        link = category.link,
    )
}
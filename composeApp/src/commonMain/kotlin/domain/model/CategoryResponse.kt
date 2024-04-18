package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
	val categories: List<CategoriesItem?>? = null
)

@Serializable
data class CategoriesItem(
	val strCategory: String? = null,
	val strCategoryDescription: String? = null,
	val idCategory: String? = null,
	val strCategoryThumb: String? = null
)

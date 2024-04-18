package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FilterCategoryResponse(
	val meals: List<FilterCategoryItem?>? = null
)

@Serializable
data class FilterCategoryItem(
	val strMealThumb: String? = null,
	val idMeal: String? = null,
	val strMeal: String? = null
)


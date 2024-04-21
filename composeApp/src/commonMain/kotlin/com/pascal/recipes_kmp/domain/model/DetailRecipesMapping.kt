package com.pascal.recipes_kmp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DetailRecipesMapping(
    val strImageSource: String? = null,
    val strCategory: String? = null,
    val strArea: String? = null,
    val strCreativeCommonsConfirmed: String? = null,
    val strTags: String? = null,
    val idMeal: String? = null,
    val strInstructions: String? = null,
    val strMealThumb: String? = null,
    val strYoutube: String? = null,
    val strMeal: String? = null,
    val dateModified: String? = null,
    val strDrinkAlternate: String? = null,
    val strSource: String? = null,
    val isFavorite: Boolean? = false,
    val listIngredient: MutableList<IngredientMapping>? = null
)

@Serializable
data class IngredientMapping(
    val strIngredient: String? = null,
    val strMeasure: String? = null
)

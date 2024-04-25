package com.pascal.recipes_kmp.presentation.screen.detail

import com.pascal.recipes_kmp.data.repository.Repository
import com.pascal.recipes_kmp.domain.model.DetailRecipesMapping
import com.pascal.recipes_kmp.domain.model.IngredientMapping
import com.pascal.recipes_kmp.domain.usecases.UiState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DetailViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _detailRecipes = MutableStateFlow<UiState<DetailRecipesMapping?>>(UiState.Loading)
    val detailRecipes: StateFlow<UiState<DetailRecipesMapping?>> = _detailRecipes.asStateFlow()

    suspend fun loadDetailRecipes(query: String) {
        try {
            val recipe = repository.getDetailRecipe(query).meals?.firstOrNull()
            val isFavorite = false

            val listIngredient = mutableListOf<IngredientMapping>()
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient1, recipe?.strMeasure1)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient2, recipe?.strMeasure2)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient3, recipe?.strMeasure3)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient4, recipe?.strMeasure4)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient5, recipe?.strMeasure5)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient6, recipe?.strMeasure6)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient7, recipe?.strMeasure7)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient8, recipe?.strMeasure8)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient9, recipe?.strMeasure9)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient10, recipe?.strMeasure10)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient11, recipe?.strMeasure11)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient12, recipe?.strMeasure12)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient13, recipe?.strMeasure13)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient14, recipe?.strMeasure14)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient15, recipe?.strMeasure15)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient16, recipe?.strMeasure16)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient17, recipe?.strMeasure17)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient18, recipe?.strMeasure18)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient19, recipe?.strMeasure19)
            addIngredientIfNotNull(listIngredient, recipe?.strIngredient20, recipe?.strMeasure20)

            val mapping = DetailRecipesMapping(
                strImageSource = recipe?.strImageSource,
                strCategory = recipe?.strCategory,
                strArea = recipe?.strArea,
                strCreativeCommonsConfirmed = recipe?.strCreativeCommonsConfirmed?.toString(),
                strTags = recipe?.strTags,
                idMeal = recipe?.idMeal,
                strInstructions = recipe?.strInstructions,
                strMealThumb = recipe?.strMealThumb,
                strYoutube = recipe?.strYoutube,
                strMeal = recipe?.strMeal,
                dateModified = recipe?.dateModified?.toString(),
                strDrinkAlternate = recipe?.strDrinkAlternate?.toString(),
                strSource = recipe?.strSource,
                isFavorite = isFavorite,
                listIngredient = listIngredient
            )

            _detailRecipes.value = UiState.Success(mapping)
        } catch (e: Exception) {
            _detailRecipes.value = UiState.Error(e.toString())
        }
    }

    fun updateFavorite(favChecked: Boolean) {

    }

    private fun addIngredientIfNotNull(
        list: MutableList<IngredientMapping>,
        ingredient: String?,
        measure: String?
    ) {
        if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
            list.add(IngredientMapping(ingredient, measure))
        }
    }
}
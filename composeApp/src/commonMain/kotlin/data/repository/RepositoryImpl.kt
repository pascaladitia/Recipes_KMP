package data.repository

import domain.model.CategoryResponse
import domain.model.FilterCategoryResponse
import domain.model.ListRecipesResponse
import domain.model.dashboard.ResponseDashboard

interface RepositoryImpl {
    suspend fun dashboard() : ResponseDashboard
    suspend fun getCategory() : CategoryResponse
    suspend fun getFilterCategory(query: String) : FilterCategoryResponse
    suspend fun getListRecipe(query: String) : ListRecipesResponse
    suspend fun getSearchRecipe(query: String) : ListRecipesResponse
    suspend fun getDetailRecipe(query: String) : ListRecipesResponse
}
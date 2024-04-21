package com.pascal.recipes_kmp.data.repository

import com.pascal.recipes_kmp.domain.model.CategoryResponse
import com.pascal.recipes_kmp.domain.model.FilterCategoryResponse
import com.pascal.recipes_kmp.domain.model.ListRecipesResponse
import com.pascal.recipes_kmp.domain.model.dashboard.ResponseDashboard

interface RepositoryImpl {
    suspend fun dashboard() : ResponseDashboard
    suspend fun getCategory() : CategoryResponse
    suspend fun getFilterCategory(query: String) : FilterCategoryResponse
    suspend fun getListRecipe(query: String) : ListRecipesResponse
    suspend fun getSearchRecipe(query: String) : ListRecipesResponse
    suspend fun getDetailRecipe(query: String) : ListRecipesResponse
}
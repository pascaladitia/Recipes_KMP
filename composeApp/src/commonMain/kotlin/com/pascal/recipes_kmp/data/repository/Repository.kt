package com.pascal.recipes_kmp.data.repository

import com.pascal.recipes_kmp.data.remote.KtorClientApi
import com.pascal.recipes_kmp.domain.model.CategoryResponse
import com.pascal.recipes_kmp.domain.model.FilterCategoryResponse
import com.pascal.recipes_kmp.domain.model.ListRecipesResponse
import com.pascal.recipes_kmp.domain.model.dashboard.ResponseDashboard
import org.koin.core.annotation.Single

@Single
class Repository : RepositoryImpl {

    override suspend fun dashboard(): ResponseDashboard {
        return KtorClientApi.dashboard()
    }

    override suspend fun getCategory(): CategoryResponse {
        return KtorClientApi.getCategory()
    }

    override suspend fun getFilterCategory(query: String): FilterCategoryResponse {
        return KtorClientApi.getFilterCategory(query)
    }

    override suspend fun getListRecipe(query: String): ListRecipesResponse {
        return KtorClientApi.getListRecipe(query)
    }

    override suspend fun getSearchRecipe(query: String): ListRecipesResponse {
        return KtorClientApi.getSearchRecipe(query)
    }

    override suspend fun getDetailRecipe(query: String): ListRecipesResponse {
        return KtorClientApi.getDetailRecipe(query)
    }

}
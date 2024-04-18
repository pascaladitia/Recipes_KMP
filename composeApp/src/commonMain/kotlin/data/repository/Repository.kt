package data.repository

import data.remote.KtorClientApi
import domain.model.CategoryResponse
import domain.model.FilterCategoryResponse
import domain.model.ListRecipesResponse
import domain.model.dashboard.ResponseDashboard
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
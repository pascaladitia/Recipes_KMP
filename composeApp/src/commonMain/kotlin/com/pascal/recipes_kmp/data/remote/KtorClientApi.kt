package com.pascal.recipes_kmp.data.remote

import com.pascal.recipes_kmp.BuildKonfig
import com.pascal.recipes_kmp.domain.model.CategoryResponse
import com.pascal.recipes_kmp.domain.model.FilterCategoryResponse
import com.pascal.recipes_kmp.domain.model.ListRecipesResponse
import com.pascal.recipes_kmp.domain.model.dashboard.ResponseDashboard
import com.pascal.recipes_kmp.utils.Constant.TIMEOUT
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single
object KtorClientApi {
    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    co.touchlab.kermit.Logger.d(tag = "KtorClient", null) {
                        message
                    }
                }
            }
        }

        defaultRequest {
            header("Content-Type", "application/json")
        }

        install(HttpTimeout) {
            connectTimeoutMillis = TIMEOUT
            requestTimeoutMillis = TIMEOUT
            socketTimeoutMillis = TIMEOUT
        }
    }

    suspend fun dashboard(): ResponseDashboard {
        return client.get("http:///dashboard").body()
    }

    suspend fun getCategory(): CategoryResponse {
        return client.get("${BuildKonfig.BASE_URL}/categories.php").body()
    }

    suspend fun getFilterCategory(query: String): FilterCategoryResponse {
        return client.get("${BuildKonfig.BASE_URL}/filter.php"){
            parameter("c", query)
        }.body()
    }

    suspend fun getListRecipe(query: String): ListRecipesResponse {
        return client.get("${BuildKonfig.BASE_URL}/search.php"){
            parameter("f", query)
        }.body()
    }

    suspend fun getSearchRecipe(query: String): ListRecipesResponse {
        return client.get("${BuildKonfig.BASE_URL}/search.php"){
            parameter("s", query)
        }.body()
    }

    suspend fun getDetailRecipe(query: String): ListRecipesResponse {
        return client.get("${BuildKonfig.BASE_URL}/lookup.php"){
            parameter("i", query)
        }.body()
    }
}
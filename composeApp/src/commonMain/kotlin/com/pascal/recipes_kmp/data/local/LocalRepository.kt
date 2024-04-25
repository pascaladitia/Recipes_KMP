package com.pascal.recipes_kmp.data.local

import com.pascal.recipes_kmp.db.RecipesDatabase
import org.koin.core.annotation.Single
import sqldelight.db.FavoriteEntity

@Single
class LocalRepository(
    db: RecipesDatabase,
) : LocalRepositoryImpl {
    private val queries = db.recipesEntityQueries

    override suspend fun getFavoriteById(id: Long): FavoriteEntity? {
        return queries.getFavoriteById(id).executeAsOneOrNull()
    }

    override fun getAllFavorites(): List<FavoriteEntity> {
        return queries.getAllFavorites().executeAsList()
    }

    override suspend fun deleteFavoriteById(id: Long) {
        return queries.deleteFavoriteById(id)
    }

    override suspend fun insertFavorite(item: FavoriteEntity) {
        return queries.insertFavorites(
            id = item.id,
            name = item.name,
            imagePath = item.imagePath,
            category = item.category,
            tags = item.tags,
            youtube_url = item.youtube_url,
        )
    }
}
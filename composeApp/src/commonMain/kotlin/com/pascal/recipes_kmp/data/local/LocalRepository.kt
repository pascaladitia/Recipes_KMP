package com.pascal.recipes_kmp.data.local

import com.pascal.recipes_kmp.db.RecipesDatabase
import org.koin.core.annotation.Single
import sqldelight.db.FavoriteEntity
import sqldelight.db.ProfileEntity

@Single
class LocalRepository(
    db: RecipesDatabase,
) : LocalRepositoryImpl {

    private val favorite = db.favoriteEntityQueries
    private val profile = db.profileEntityQueries

    override suspend fun getFavoriteById(id: Long): FavoriteEntity? {
        return favorite.getFavoriteById(id).executeAsOneOrNull()
    }

    override fun getAllFavorites(): List<FavoriteEntity> {
        return favorite.getAllFavorites().executeAsList()
    }

    override suspend fun deleteFavoriteById(id: Long) {
        return favorite.deleteFavoriteById(id)
    }

    override suspend fun insertFavorite(item: FavoriteEntity) {
        return favorite.insertFavorites(
            id = item.id,
            name = item.name,
            imagePath = item.imagePath,
            category = item.category,
            tags = item.tags,
            youtube_url = item.youtube_url,
        )
    }

    override suspend fun getProfileById(id: Long): ProfileEntity? {
        return profile.getProfileById(id).executeAsOneOrNull()
    }

    override fun getAllProfiles(): List<ProfileEntity> {
        return profile.getAllProfiles().executeAsList()
    }

    override suspend fun deleteProfileById(id: Long) {
        return profile.deleteProfileById(id)
    }

    override suspend fun insertProfile(item: ProfileEntity) {
        return profile.insertProfiles(
            id = item.id,
            name = item.name,
            imagePath = item.imagePath,
            imageProfilePath = item.imageProfilePath,
            email = item.email,
            phone = item.phone,
            address = item.address,
        )
    }
}
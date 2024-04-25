package com.pascal.recipes_kmp.data.local

import sqldelight.db.FavoriteEntity
import sqldelight.db.ProfileEntity

interface LocalRepositoryImpl {
    suspend fun getFavoriteById(id: Long): FavoriteEntity?
    fun getAllFavorites(): List<FavoriteEntity>
    suspend fun deleteFavoriteById(id: Long)
    suspend fun insertFavorite(item: FavoriteEntity)

    suspend fun getProfileById(id: Long): ProfileEntity?
    fun getAllProfiles(): List<ProfileEntity>
    suspend fun deleteProfileById(id: Long)
    suspend fun insertProfile(item: ProfileEntity)
}
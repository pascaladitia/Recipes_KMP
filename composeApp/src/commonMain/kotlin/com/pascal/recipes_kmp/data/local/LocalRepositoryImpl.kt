package com.pascal.recipes_kmp.data.local

import sqldelight.db.FavoriteEntity

interface LocalRepositoryImpl {
    suspend fun getFavoriteById(id: Long): FavoriteEntity?
    fun getAllFavorites(): List<FavoriteEntity>
    suspend fun deleteFavoriteById(id: Long)
    suspend fun insertFavorite(item: FavoriteEntity)
}
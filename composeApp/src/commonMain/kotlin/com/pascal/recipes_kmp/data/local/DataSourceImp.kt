package com.pascal.recipes_kmp.data.local

import com.pascal.recipes_kmp.db.RecipesDatabase
import sqldelight.db.RecipesEntity

class DataSourceImp(
    db: RecipesDatabase,
) : DataSource {
    private val queries = db.recipesEntityQueries
    override suspend fun getVideoById(id: Long): RecipesEntity? {
        return queries.getVideoById(id).executeAsOneOrNull()
    }

    override fun getAllVideos(): List<RecipesEntity> {
        return queries.getAllVideos().executeAsList()
    }

    override suspend fun deleteVideoById(id: Long) {
        return queries.deleteVideoById(id)
    }

    override suspend fun insertVideo(
        id: Long?,
        title: String,
        videoThumbnail: String,
        videoDesc: String,
        isVerified: Long,
        channelSubs: String,
        likes: String,
        channelName: String,
        channelImage: String,
        views: String,
        pubDate: String,
        duration: String,
    ) {
        return queries.insertVideos(
            id = null,
            title,
            videoThumbnail,
            videoDesc,
            isVerified,
            channelSubs,
            likes,
            channelName,
            channelImage,
            views,
            pubDate,
            duration
        )
    }
}
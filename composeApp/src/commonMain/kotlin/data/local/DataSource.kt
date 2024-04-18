package data.local

import sqldelight.db.RecipesEntity

interface DataSource {
    suspend fun getVideoById(id: Long): RecipesEntity?
    fun getAllVideos(): List<RecipesEntity>
    suspend fun deleteVideoById(id: Long)
    suspend fun insertVideo(
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
    )
}
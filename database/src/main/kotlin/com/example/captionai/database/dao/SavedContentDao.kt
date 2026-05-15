package com.example.captionai.database.dao

import androidx.room.*
import com.example.captionai.database.entity.SavedContentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(content: SavedContentEntity)

    @Query("SELECT * FROM saved_content ORDER BY timestamp DESC")
    fun getAllSavedContent(): Flow<List<SavedContentEntity>>

    @Delete
    suspend fun deleteContent(content: SavedContentEntity)
}

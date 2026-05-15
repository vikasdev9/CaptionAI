package com.example.captionai.database.dao

import androidx.room.*
import com.example.captionai.database.entity.PlannerItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlannerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PlannerItemEntity)

    @Query("SELECT * FROM planner_items ORDER BY date ASC")
    fun getAllItems(): Flow<List<PlannerItemEntity>>

    @Update
    suspend fun updateItem(item: PlannerItemEntity)

    @Delete
    suspend fun deleteItem(item: PlannerItemEntity)
}

package com.example.captionai.database.dao

import androidx.room.*
import com.example.captionai.database.entity.PlannerNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlannerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: PlannerNoteEntity)

    @Query("SELECT * FROM planner_notes ORDER BY date ASC")
    fun getAllNotes(): Flow<List<PlannerNoteEntity>>

    @Update
    suspend fun updateNote(note: PlannerNoteEntity)

    @Delete
    suspend fun deleteNote(note: PlannerNoteEntity)
}

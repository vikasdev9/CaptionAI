package com.example.captionai.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.captionai.database.dao.PlannerDao
import com.example.captionai.database.dao.SavedContentDao
import com.example.captionai.database.entity.PlannerNoteEntity
import com.example.captionai.database.entity.SavedContentEntity

@Database(
    entities = [SavedContentEntity::class, PlannerNoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CaptionAIDatabase : RoomDatabase() {
    abstract fun savedContentDao(): SavedContentDao
    abstract fun plannerDao(): PlannerDao
}

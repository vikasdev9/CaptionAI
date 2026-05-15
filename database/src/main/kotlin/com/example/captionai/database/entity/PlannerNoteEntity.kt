package com.example.captionai.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planner_notes")
data class PlannerNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val date: Long,
    val isCompleted: Boolean = false
)

package com.example.captionai.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planner_items")
data class PlannerItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val date: Long,
    val status: String, // Enum name
    val type: String, // Enum name
    val reminderEnabled: Boolean = false
)

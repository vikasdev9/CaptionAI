package com.example.captionai.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_content")
data class SavedContentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // CAPTION, HASHTAG, BIO, REEL_IDEA
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

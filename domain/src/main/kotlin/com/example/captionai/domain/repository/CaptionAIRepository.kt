package com.example.captionai.domain.repository

import com.example.captionai.core.ResultState
import com.example.captionai.domain.model.GeneratedContent
import kotlinx.coroutines.flow.Flow

interface CaptionAIRepository {
    // AI Generation
    suspend fun generateContent(prompt: String): ResultState<String>

    // Saved Content (Room)
    suspend fun saveContent(content: GeneratedContent)
    fun getSavedContent(): Flow<List<GeneratedContent>>
    suspend fun deleteContent(content: GeneratedContent)
}

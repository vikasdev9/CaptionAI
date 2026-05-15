package com.example.captionai.domain.repository

import com.example.captionai.core.ResultState
import com.example.captionai.domain.model.ContentType
import com.example.captionai.domain.model.GeneratedContent
import com.example.captionai.domain.model.PlannerNote
import kotlinx.coroutines.flow.Flow

interface CaptionAIRepository {
    // AI Generation
    suspend fun generateContent(prompt: String): ResultState<String>

    // Saved Content (Room)
    suspend fun saveContent(content: GeneratedContent)
    fun getSavedContent(): Flow<List<GeneratedContent>>
    suspend fun deleteContent(content: GeneratedContent)

    // Planner (Room)
    suspend fun addPlannerNote(note: PlannerNote)
    fun getPlannerNotes(): Flow<List<PlannerNote>>
    suspend fun updatePlannerNote(note: PlannerNote)
    suspend fun deletePlannerNote(note: PlannerNote)
}

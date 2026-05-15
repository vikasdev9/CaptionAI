package com.example.captionai.data.repository

import com.example.captionai.core.ResultState
import com.example.captionai.data.mapper.toDomain
import com.example.captionai.data.mapper.toEntity
import com.example.captionai.database.dao.SavedContentDao
import com.example.captionai.domain.model.GeneratedContent
import com.example.captionai.domain.repository.CaptionAIRepository
import com.example.captionai.network.GeminiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CaptionAIRepositoryImpl @Inject constructor(
    private val geminiService: GeminiService,
    private val savedContentDao: SavedContentDao
) : CaptionAIRepository {

    override suspend fun generateContent(prompt: String): ResultState<String> {
        return try {
            val response = geminiService.generateContent(prompt)
            if (response.startsWith("Error:")) {
                ResultState.Error(response)
            } else {
                ResultState.Success(response)
            }
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun saveContent(content: GeneratedContent) {
        savedContentDao.insertContent(content.toEntity())
    }

    override fun getSavedContent(): Flow<List<GeneratedContent>> {
        return savedContentDao.getAllSavedContent().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun deleteContent(content: GeneratedContent) {
        savedContentDao.deleteContent(content.toEntity())
    }
}

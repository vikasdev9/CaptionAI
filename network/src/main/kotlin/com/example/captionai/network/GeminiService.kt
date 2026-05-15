package com.example.captionai.network

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiService @Inject constructor(
    private val generativeModel: GenerativeModel
) {
    suspend fun generateContent(prompt: String): String = withContext(Dispatchers.IO) {
        try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "No content generated"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}

package com.example.captionai.domain.repository

import com.example.captionai.domain.model.User
import kotlinx.coroutines.flow.Flow
import android.net.Uri

interface ProfileRepository {
    fun getUserProfile(): Flow<User?>
    suspend fun updateUserProfile(name: String, handle: String)
    suspend fun uploadProfileImage(uri: Uri): String
    suspend fun logout()
}

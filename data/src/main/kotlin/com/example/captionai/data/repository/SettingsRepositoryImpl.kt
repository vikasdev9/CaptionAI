package com.example.captionai.data.repository

import com.example.captionai.data.local.SettingsPreferences
import com.example.captionai.domain.repository.SettingsRepository
import com.example.captionai.firebase.FirebaseAuthManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val preferences: SettingsPreferences,
    private val firebaseAuthManager: FirebaseAuthManager
) : SettingsRepository {

    override fun isDarkMode(): Flow<Boolean> = preferences.isDarkMode

    override suspend fun setDarkMode(enabled: Boolean) {
        preferences.setDarkMode(enabled)
    }

    override fun isNotificationsEnabled(): Flow<Boolean> = preferences.isNotificationsEnabled

    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        preferences.setNotificationsEnabled(enabled)
    }

    override fun getLanguage(): Flow<String> = preferences.language

    override suspend fun setLanguage(language: String) {
        preferences.setLanguage(language)
    }

    override suspend fun logout() {
        firebaseAuthManager.signOut()
    }
}

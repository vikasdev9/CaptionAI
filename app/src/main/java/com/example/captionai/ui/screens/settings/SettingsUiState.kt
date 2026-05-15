package com.example.captionai.ui.screens.settings

data class SettingsUiState(
    val isDarkMode: Boolean = true,
    val isNotificationsEnabled: Boolean = true,
    val selectedLanguage: String = "English",
    val appVersion: String = "1.0.0",
    val showLanguageBottomSheet: Boolean = false
)

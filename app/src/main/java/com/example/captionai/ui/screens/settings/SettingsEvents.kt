package com.example.captionai.ui.screens.settings

sealed class SettingsEvents {
    data class ToggleDarkMode(val enabled: Boolean) : SettingsEvents()
    data class ToggleNotifications(val enabled: Boolean) : SettingsEvents()
    data class ChangeLanguage(val language: String) : SettingsEvents()
    object ToggleLanguageBottomSheet : SettingsEvents()
    object Logout : SettingsEvents()
    object NavigateBack : SettingsEvents()
    object NavigateToPremium : SettingsEvents()
    object NavigateToPrivacy : SettingsEvents()
}

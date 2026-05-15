package com.example.captionai.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.captionai.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        observeSettings()
    }

    private fun observeSettings() {
        combine(
            repository.isDarkMode(),
            repository.isNotificationsEnabled(),
            repository.getLanguage()
        ) { darkMode, notifications, language ->
            _uiState.update {
                it.copy(
                    isDarkMode = darkMode,
                    isNotificationsEnabled = notifications,
                    selectedLanguage = language
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: SettingsEvents) {
        when (event) {
            is SettingsEvents.ToggleDarkMode -> {
                viewModelScope.launch {
                    repository.setDarkMode(event.enabled)
                }
            }
            is SettingsEvents.ToggleNotifications -> {
                viewModelScope.launch {
                    repository.setNotificationsEnabled(event.enabled)
                }
            }
            is SettingsEvents.ChangeLanguage -> {
                viewModelScope.launch {
                    repository.setLanguage(event.language)
                    _uiState.update { it.copy(showLanguageBottomSheet = false) }
                }
            }
            is SettingsEvents.ToggleLanguageBottomSheet -> {
                _uiState.update { it.copy(showLanguageBottomSheet = !it.showLanguageBottomSheet) }
            }
            is SettingsEvents.Logout -> {
                viewModelScope.launch {
                    repository.logout()
                }
            }
            else -> { /* Handle navigation in UI */ }
        }
    }
}

package com.example.captionai.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.captionai.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        repository.getUserProfile()
            .onEach { user ->
                if (user != null) {
                    _uiState.value = ProfileUiState.Success(user)
                } else {
                    _uiState.value = ProfileUiState.Error("Failed to load profile")
                }
            }
            .catch { e ->
                _uiState.value = ProfileUiState.Error(e.message ?: "An unknown error occurred")
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: ProfileEvents) {
        when (event) {
            is ProfileEvents.UpdateProfile -> {
                viewModelScope.launch {
                    repository.updateUserProfile(event.name, event.handle)
                }
            }
            is ProfileEvents.UploadImage -> {
                viewModelScope.launch {
                    repository.uploadProfileImage(event.uri)
                }
            }
            is ProfileEvents.Logout -> {
                viewModelScope.launch {
                    repository.logout()
                }
            }
            is ProfileEvents.LoadProfile -> {
                loadProfile()
            }
        }
    }
}

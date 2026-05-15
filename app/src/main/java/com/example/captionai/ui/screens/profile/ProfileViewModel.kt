package com.example.captionai.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.captionai.domain.model.User
import com.example.captionai.domain.model.UserStats
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

    private val dummyUser = User(
        id = "1",
        name = "Alex Morgan",
        handle = "@alex.creates",
        email = "alex@example.com",
        profileImageUrl = "", // Will use placeholder
        isPremium = true,
        trialDaysLeft = 3,
        stats = UserStats(
            generations = 248,
            saved = 36,
            streak = 12
        )
    )

    init {
        loadProfile()
    }

    private fun loadProfile() {
        repository.getUserProfile()
            .onEach { user ->
                if (user != null) {
                    _uiState.value = ProfileUiState.Success(user)
                } else {
                    // Provide dummy data if user is null (e.g., not logged in or firestore empty)
                    _uiState.value = ProfileUiState.Success(dummyUser)
                }
            }
            .catch { e ->
                // Fallback to dummy data on error so you can see the UI
                _uiState.value = ProfileUiState.Success(dummyUser)
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

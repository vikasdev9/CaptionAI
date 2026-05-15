package com.example.captionai.ui.screens.profile

import com.example.captionai.domain.model.User

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val user: User) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}

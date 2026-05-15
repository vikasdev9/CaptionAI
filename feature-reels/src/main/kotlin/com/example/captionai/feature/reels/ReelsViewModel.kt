package com.example.captionai.feature.reels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.captionai.core.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReelsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<ResultState<String>>(ResultState.Idle)
    val uiState: StateFlow<ResultState<String>> = _uiState

    fun generateReelIdeas(niche: String, category: String) {
        viewModelScope.launch {
            _uiState.value = ResultState.Loading
            delay(1500)
            _uiState.value = ResultState.Success("Idea: A 'Day in the Life' reel as a $niche creator focusing on $category.\n\nHook: 'Stop scrolling! Here is why you are failing at $niche...'\n\nCTA: Follow for more daily $niche tips!")
        }
    }

    fun saveIdea(idea: String) {
        // Save logic
    }
}

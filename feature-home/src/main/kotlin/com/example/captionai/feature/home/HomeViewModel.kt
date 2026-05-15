package com.example.captionai.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.captionai.domain.model.GeneratedContent
import com.example.captionai.domain.repository.CaptionAIRepository
import com.example.captionai.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val userName: String = "Alex",
    val isLoading: Boolean = false,
    val aiSuggestion: String = "Generate today’s reel script for your niche",
    val trendingTags: List<TrendingTag> = listOf(
        TrendingTag("#reelsviral", "2.4M posts"),
        TrendingTag("#aesthetic", "1.8M posts"),
        TrendingTag("#contentcreator", "920K posts")
    ),
    val recentGenerations: List<GeneratedContent> = emptyList(),
    val searchQuery: String = ""
)

data class TrendingTag(val tag: String, val count: String)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val captionRepository: CaptionAIRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadHomeData()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            // Simulate network delay or actual fetch
            loadHomeData()
            kotlinx.coroutines.delay(1000)
            _isRefreshing.value = false
        }
    }

    private fun loadHomeData() {
        // Load Profile
        profileRepository.getUserProfile()
            .onEach { user ->
                _uiState.update { it.copy(userName = user?.name ?: "Alex") }
            }
            .launchIn(viewModelScope)

        // Load Recent Generations
        captionRepository.getSavedContent()
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .onEach { recent ->
                _uiState.update { it.copy(recentGenerations = recent.take(5), isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }
}

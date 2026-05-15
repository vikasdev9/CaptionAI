package com.example.captionai.feature.hashtag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.captionai.core.ResultState
import com.example.captionai.domain.model.ContentType
import com.example.captionai.domain.model.GeneratedContent
import com.example.captionai.domain.repository.CaptionAIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HashtagViewModel @Inject constructor(
    private val repository: CaptionAIRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ResultState<String>>(ResultState.Idle)
    val uiState: StateFlow<ResultState<String>> = _uiState

    fun generateHashtags(niche: String) {
        viewModelScope.launch {
            _uiState.value = ResultState.Loading
            val prompt = "Generate a list of trending and relevant hashtags for the niche: $niche. Provide them in a single block."
            _uiState.value = repository.generateContent(prompt)
        }
    }

    fun saveHashtags(content: String) {
        viewModelScope.launch {
            repository.saveContent(
                GeneratedContent(
                    type = ContentType.HASHTAG,
                    content = content
                )
            )
        }
    }
}

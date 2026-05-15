package com.example.captionai.feature.bio

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
class BioViewModel @Inject constructor(
    private val repository: CaptionAIRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ResultState<String>>(ResultState.Idle)
    val uiState: StateFlow<ResultState<String>> = _uiState

    fun generateBio(profession: String, personality: String) {
        viewModelScope.launch {
            _uiState.value = ResultState.Loading
            val prompt = "Generate a professional and creative Instagram bio for: Profession: $profession, Personality: $personality. Include relevant emojis."
            _uiState.value = repository.generateContent(prompt)
        }
    }

    fun saveBio(content: String) {
        viewModelScope.launch {
            repository.saveContent(
                GeneratedContent(
                    type = ContentType.BIO,
                    content = content
                )
            )
        }
    }
}

package com.example.captionai.feature.caption

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
class CaptionViewModel @Inject constructor(
    private val repository: CaptionAIRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ResultState<String>>(ResultState.Idle)
    val uiState: StateFlow<ResultState<String>> = _uiState

    fun generateCaption(topic: String, tone: String) {
        viewModelScope.launch {
            _uiState.value = ResultState.Loading
            val prompt = "Generate an Instagram caption for: Topic: $topic, Tone: $tone. Keep it engaging and relevant."
            _uiState.value = repository.generateContent(prompt)
        }
    }

    fun saveCaption(content: String) {
        viewModelScope.launch {
            repository.saveContent(
                GeneratedContent(
                    type = ContentType.CAPTION,
                    content = content
                )
            )
        }
    }
}

package com.example.captionai.feature.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.captionai.domain.model.PlannerNote
import com.example.captionai.domain.repository.CaptionAIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor(
    private val repository: CaptionAIRepository
) : ViewModel() {

    val notes: StateFlow<List<PlannerNote>> = repository.getPlannerNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addNote(title: String, description: String, date: Long) {
        viewModelScope.launch {
            repository.addPlannerNote(
                PlannerNote(
                    title = title,
                    description = description,
                    date = date
                )
            )
        }
    }

    fun deleteNote(note: PlannerNote) {
        viewModelScope.launch {
            repository.deletePlannerNote(note)
        }
    }

    fun toggleNoteCompletion(note: PlannerNote) {
        viewModelScope.launch {
            repository.updatePlannerNote(note.copy(isCompleted = !note.isCompleted))
        }
    }
}

package com.example.captionai.feature.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.captionai.core.PlannerContentType
import com.example.captionai.domain.model.PlannerItem
import com.example.captionai.core.PlannerStatus
import com.example.captionai.domain.repository.PlannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class PlannerUiState(
    val items: List<PlannerItem> = emptyList(),
    val selectedDate: Calendar = Calendar.getInstance(),
    val isLoading: Boolean = false,
    val scheduledCount: Int = 0,
    val draftsCount: Int = 0,
    val postedCount: Int = 0
)

@HiltViewModel
class PlannerViewModel @Inject constructor(
    private val repository: PlannerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlannerUiState())
    val uiState: StateFlow<PlannerUiState> = _uiState.asStateFlow()

    init {
        observePlannerItems()
        seedDummyData()
    }

    private fun seedDummyData() {
        viewModelScope.launch {
            val dummyItems = listOf(
                PlannerItem(
                    title = "Morning routine reel",
                    description = "Focus on aesthetic transitions",
                    date = getTodayAt(9, 0),
                    type = PlannerContentType.REEL,
                    status = PlannerStatus.SCHEDULED
                ),
                PlannerItem(
                    title = "Carousel: 5 caption tips",
                    description = "Educational carousel for creators",
                    date = getTodayAt(13, 30),
                    type = PlannerContentType.POST,
                    status = PlannerStatus.DRAFT
                ),
                PlannerItem(
                    title = "Behind the scenes Q&A",
                    description = "Interactive story for engagement",
                    date = getTodayAt(18, 0),
                    type = PlannerContentType.STORY,
                    status = PlannerStatus.SCHEDULED
                )
            )
            dummyItems.forEach { repository.insertItem(it) }
        }
    }

    private fun getTodayAt(hour: Int, minute: Int): Long {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }.timeInMillis
    }

    private fun observePlannerItems() {
        repository.getAllItems()
            .onEach { items ->
                _uiState.update { state ->
                    state.copy(
                        items = items,
                        scheduledCount = items.count { it.status == PlannerStatus.SCHEDULED },
                        draftsCount = items.count { it.status == PlannerStatus.DRAFT },
                        postedCount = items.count { it.status == PlannerStatus.POSTED }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onDateSelected(calendar: Calendar) {
        _uiState.update { it.copy(selectedDate = calendar) }
    }

    fun addItem(title: String, description: String, date: Long, type: PlannerContentType, status: PlannerStatus) {
        viewModelScope.launch {
            val newItem = PlannerItem(
                title = title,
                description = description,
                date = date,
                type = type,
                status = status
            )
            repository.insertItem(newItem)
        }
    }

    fun updateItem(item: PlannerItem) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }

    fun deleteItem(item: PlannerItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }
}

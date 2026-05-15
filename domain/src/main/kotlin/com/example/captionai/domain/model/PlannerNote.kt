package com.example.captionai.domain.model

data class PlannerNote(
    val id: Int = 0,
    val title: String,
    val description: String,
    val date: Long,
    val isCompleted: Boolean = false
)

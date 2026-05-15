package com.example.captionai.domain.model

import com.example.captionai.core.PlannerContentType
import com.example.captionai.core.PlannerStatus

data class PlannerItem(
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val date: Long, // Date and time
    val status: PlannerStatus = PlannerStatus.SCHEDULED,
    val type: PlannerContentType = PlannerContentType.POST,
    val reminderEnabled: Boolean = false
)

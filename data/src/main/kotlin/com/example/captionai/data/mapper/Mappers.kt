package com.example.captionai.data.mapper

import com.example.captionai.database.entity.PlannerItemEntity
import com.example.captionai.database.entity.SavedContentEntity
import com.example.captionai.domain.model.ContentType
import com.example.captionai.domain.model.GeneratedContent
import com.example.captionai.domain.model.PlannerItem
import com.example.captionai.core.PlannerStatus
import com.example.captionai.core.PlannerContentType

fun SavedContentEntity.toDomain() = GeneratedContent(
    id = id,
    type = ContentType.valueOf(type),
    content = content,
    timestamp = timestamp
)

fun GeneratedContent.toEntity() = SavedContentEntity(
    id = id,
    type = type.name,
    content = content,
    timestamp = timestamp
)

fun PlannerItemEntity.toDomain() = PlannerItem(
    id = id,
    title = title,
    description = description,
    date = date,
    status = PlannerStatus.valueOf(status),
    type = PlannerContentType.valueOf(type),
    reminderEnabled = reminderEnabled
)

fun PlannerItem.toEntity() = PlannerItemEntity(
    id = id,
    title = title,
    description = description,
    date = date,
    status = status.name,
    type = type.name,
    reminderEnabled = reminderEnabled
)

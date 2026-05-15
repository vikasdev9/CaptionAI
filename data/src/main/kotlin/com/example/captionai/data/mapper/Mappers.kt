package com.example.captionai.data.mapper

import com.example.captionai.database.entity.PlannerNoteEntity
import com.example.captionai.database.entity.SavedContentEntity
import com.example.captionai.domain.model.ContentType
import com.example.captionai.domain.model.GeneratedContent
import com.example.captionai.domain.model.PlannerNote

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

fun PlannerNoteEntity.toDomain() = PlannerNote(
    id = id,
    title = title,
    description = description,
    date = date,
    isCompleted = isCompleted
)

fun PlannerNote.toEntity() = PlannerNoteEntity(
    id = id,
    title = title,
    description = description,
    date = date,
    isCompleted = isCompleted
)

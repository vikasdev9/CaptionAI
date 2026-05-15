package com.example.captionai.domain.repository

import com.example.captionai.domain.model.PlannerItem
import kotlinx.coroutines.flow.Flow

interface PlannerRepository {
    suspend fun insertItem(item: PlannerItem)
    fun getAllItems(): Flow<List<PlannerItem>>
    suspend fun updateItem(item: PlannerItem)
    suspend fun deleteItem(item: PlannerItem)
}

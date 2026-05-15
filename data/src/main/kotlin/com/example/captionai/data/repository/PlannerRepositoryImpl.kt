package com.example.captionai.data.repository

import com.example.captionai.database.dao.PlannerDao
import com.example.captionai.data.mapper.toDomain
import com.example.captionai.data.mapper.toEntity
import com.example.captionai.domain.model.PlannerItem
import com.example.captionai.domain.repository.PlannerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlannerRepositoryImpl @Inject constructor(
    private val plannerDao: PlannerDao
) : PlannerRepository {

    override suspend fun insertItem(item: PlannerItem) {
        plannerDao.insertItem(item.toEntity())
    }

    override fun getAllItems(): Flow<List<PlannerItem>> {
        return plannerDao.getAllItems().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun updateItem(item: PlannerItem) {
        plannerDao.updateItem(item.toEntity())
    }

    override suspend fun deleteItem(item: PlannerItem) {
        plannerDao.deleteItem(item.toEntity())
    }
}

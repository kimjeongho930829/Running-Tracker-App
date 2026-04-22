package com.jhkim.runningtracker.data.repository

import com.jhkim.runningtracker.domain.model.Run
import com.jhkim.runningtracker.domain.repository.RunRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class MockRunRepositoryImpl: RunRepository {

    private val _runs = MutableStateFlow<List<Run>>(
        value = listOf(
            Run(
                id = 1,
                distanceInMeters = 5000.0,
                timeInMillis = 1500000L,
                timestamp = System.currentTimeMillis(),
                avgSpeedInKMH = 12.0f,
            ),
            Run(
                id = 2,
                distanceInMeters = 8000.0,
                timeInMillis = 2400000L,
                timestamp = System.currentTimeMillis(),
                avgSpeedInKMH = 13.0f,
            ),
            Run(
                id = 3,
                distanceInMeters = 3000.0,
                timeInMillis = 900000L,
                timestamp = System.currentTimeMillis(),
                avgSpeedInKMH = 10.0f,
            ),
            Run(
                id = 4,
                distanceInMeters = 2000.0,
                timeInMillis = 600000L,
                timestamp = System.currentTimeMillis(),
                avgSpeedInKMH = 14.0f,
            )
        )
    )

    override suspend fun insertRun(run: Run) {
        _runs.update {
            it + run.copy(id = (it.maxByOrNull { r -> r.id ?: 0 }?.id ?: 0) + 1)
        }
    }

    override suspend fun deleteRun(run: Run) {
        _runs.update { it.filter { r -> r.id != run.id } }
    }

    override fun getAllRunsSortedByDate(): Flow<List<Run>> =
        _runs.map { it.sortedByDescending { r -> r.timestamp } }


    override fun getAllRunsSortedByTimeInMillis(): Flow<List<Run>> =
        _runs.map { it.sortedByDescending { r -> r.timeInMillis } }


    override fun getAllRunsSortedByDistance(): Flow<List<Run>> =
    _runs.map { it.sortedByDescending { r -> r.distanceInMeters } }


    override fun getAllRunsSortedByAvgSpeed(): Flow<List<Run>> =
        _runs.map { it.sortedByDescending { r -> r.avgSpeedInKMH } }

}
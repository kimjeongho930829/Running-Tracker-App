package com.jhkim.runningtracker.data.repository

import com.jhkim.runningtracker.data.database.RunDao
import com.jhkim.runningtracker.data.database.RunEntity
import com.jhkim.runningtracker.domain.model.Run
import com.jhkim.runningtracker.domain.repository.RunRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomRunRepositoryImpl(
    private val runDao: RunDao,
): RunRepository {
    override suspend fun insertRun(run: Run) {
        runDao.insertRun(RunEntity.fromRun(run))
    }

    override suspend fun deleteRun(run: Run) {
        runDao.deleteRun(RunEntity.fromRun(run))
    }

    override fun getAllRunsSortedByDate(): Flow<List<Run>> =
        runDao.getAllRunsSortedByDate()
            .map { entities -> entities.map { it.toRun() } }

    override fun getAllRunsSortedByTimeInMillis(): Flow<List<Run>>  =
        runDao.getAllRunsSortedByTimeInMillis()
            .map { entities -> entities.map { it.toRun() } }

    override fun getAllRunsSortedByDistance(): Flow<List<Run>>  =
        runDao.getAllRunsSortedByDistance()
            .map { entities -> entities.map { it.toRun() } }

    override fun getAllRunsSortedByAvgSpeed(): Flow<List<Run>>  =
        runDao.getAllRunsSortedByAvgSpeed()
            .map { entities -> entities.map { it.toRun() } }
}
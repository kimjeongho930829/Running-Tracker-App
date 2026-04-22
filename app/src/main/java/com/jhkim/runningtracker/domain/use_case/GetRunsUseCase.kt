package com.jhkim.runningtracker.domain.use_case

import com.jhkim.runningtracker.domain.model.Run
import com.jhkim.runningtracker.domain.model.SortType
import com.jhkim.runningtracker.domain.repository.RunRepository
import kotlinx.coroutines.flow.Flow

class GetRunsUseCase(
    private val repository: RunRepository
) {
    fun execute(sortType: SortType): Flow<List<Run>> {
        return when (sortType) {
            SortType.DATA -> repository.getAllRunsSortedByDate()
            SortType.RUNNING_TIME -> repository.getAllRunsSortedByTimeInMillis()
            SortType.DISTANCE -> repository.getAllRunsSortedByDistance()
            SortType.AVG_SPEED -> repository.getAllRunsSortedByAvgSpeed()
        }
    }
}
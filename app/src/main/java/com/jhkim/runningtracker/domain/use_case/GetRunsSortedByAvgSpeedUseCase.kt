package com.jhkim.runningtracker.domain.use_case

import com.jhkim.runningtracker.domain.model.Run
import com.jhkim.runningtracker.domain.repository.RunRepository
import kotlinx.coroutines.flow.Flow

class GetRunsSortedByAvgSpeedUseCase(
    private val repository: RunRepository
) {
    fun execute(): Flow<List<Run>> {
        return repository.getAllRunsSortedByAvgSpeed()
    }
}
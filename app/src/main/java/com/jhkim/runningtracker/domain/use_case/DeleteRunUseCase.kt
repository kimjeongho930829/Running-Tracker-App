package com.jhkim.runningtracker.domain.use_case

import com.jhkim.runningtracker.domain.model.Run
import com.jhkim.runningtracker.domain.repository.RunRepository

class DeleteRunUseCase(
    private val repository: RunRepository
) {
    suspend fun execute(run: Run) {
        repository.deleteRun(run)
    }
}
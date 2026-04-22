package com.jhkim.runningtracker.core.di

import com.jhkim.runningtracker.data.repository.MockRunRepositoryImpl
import com.jhkim.runningtracker.domain.repository.RunRepository
import com.jhkim.runningtracker.domain.use_case.DeleteRunUseCase
import com.jhkim.runningtracker.domain.use_case.GetRunsSortedByAvgSpeedUseCase
import com.jhkim.runningtracker.domain.use_case.GetRunsSortedByDateUseCase
import com.jhkim.runningtracker.domain.use_case.GetRunsSortedByDistanceUseCase
import com.jhkim.runningtracker.domain.use_case.GetRunsSortedByTimeUseCase
import com.jhkim.runningtracker.domain.use_case.SaveRunUseCase
import org.koin.dsl.module

val appModule = module {
    single<RunRepository> { MockRunRepositoryImpl() }

    single { DeleteRunUseCase(get()) }
    single { GetRunsSortedByAvgSpeedUseCase(get()) }
    single { GetRunsSortedByDateUseCase(get()) }
    single { GetRunsSortedByDistanceUseCase(get()) }
    single { GetRunsSortedByTimeUseCase(get()) }
    single { SaveRunUseCase(get()) }
}
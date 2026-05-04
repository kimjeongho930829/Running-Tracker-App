package com.jhkim.runningtracker.core.di

import com.jhkim.runningtracker.data.repository.MockRunRepositoryImpl
import com.jhkim.runningtracker.domain.repository.RunRepository
import com.jhkim.runningtracker.domain.use_case.DeleteRunUseCase
import com.jhkim.runningtracker.domain.use_case.GetRunsUseCase
import com.jhkim.runningtracker.domain.use_case.SaveRunUseCase
import com.jhkim.runningtracker.presentation.MainViewModel
import com.jhkim.runningtracker.presentation.service.RunningTimer
import com.jhkim.runningtracker.presentation.service.TrackingManager
import com.jhkim.runningtracker.presentation.service.TrackingNotificationHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<RunRepository> { MockRunRepositoryImpl() }

    single { DeleteRunUseCase(get()) }
    single { GetRunsUseCase(get()) }
    single { SaveRunUseCase(get()) }

    single { RunningTimer() }
    single { TrackingManager(
        locationClient = get(),
        runningTimer = get(),
    ) }
    single { TrackingNotificationHelper(androidContext()) }

    viewModel {
        MainViewModel(
            saveRunUseCase = get(),
            deleteRunUseCase = get(),
            getRunsUseCase = get(),
            trackingManager = get(),
        )
    }
}
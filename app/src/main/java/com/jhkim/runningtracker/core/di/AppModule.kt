package com.jhkim.runningtracker.core.di

import com.jhkim.runningtracker.data.repository.MockRunRepositoryImpl
import com.jhkim.runningtracker.domain.repository.RunRepository
import com.jhkim.runningtracker.domain.use_case.DeleteRunUseCase
import com.jhkim.runningtracker.domain.use_case.GetRunsUseCase
import com.jhkim.runningtracker.domain.use_case.SaveRunUseCase
import org.koin.dsl.module

val appModule = module {
    single<RunRepository> { MockRunRepositoryImpl() }

    single { DeleteRunUseCase(get()) }
    single { GetRunsUseCase(get()) }
    single { SaveRunUseCase(get()) }
}
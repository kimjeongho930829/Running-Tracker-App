package com.jhkim.runningtracker.core.di

import com.jhkim.runningtracker.data.location.MockLocationClient
import com.jhkim.runningtracker.data.repository.MockRunRepositoryImpl
import com.jhkim.runningtracker.domain.location.LocationClient
import com.jhkim.runningtracker.domain.repository.RunRepository
import org.koin.dsl.module

val flavorModule = module {
    single<RunRepository> { MockRunRepositoryImpl() }
    single<LocationClient> { MockLocationClient() }
}
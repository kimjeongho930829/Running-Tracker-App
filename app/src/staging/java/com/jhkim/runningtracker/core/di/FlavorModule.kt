package com.jhkim.runningtracker.core.di

import androidx.room.Room
import com.jhkim.runningtracker.data.database.RunDao
import com.jhkim.runningtracker.data.database.RunDatabase
import com.jhkim.runningtracker.data.location.MockLocationClient
import com.jhkim.runningtracker.data.repository.RoomRunRepositoryImpl
import com.jhkim.runningtracker.domain.location.LocationClient
import com.jhkim.runningtracker.domain.repository.RunRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val flavorModule = module {
    single<RunDatabase> {
        Room.inMemoryDatabaseBuilder(
            androidContext(),
            RunDatabase::class.java
        ).build()
    }
    single<RunDao> { get<RunDatabase>().getRunDao() }
    single<RunRepository> { RoomRunRepositoryImpl(get()) }
    single<LocationClient> { MockLocationClient() }
}
package com.jhkim.runningtracker.core.di

import com.jhkim.runningtracker.presentation.component.LogMapRenderer
import com.jhkim.runningtracker.presentation.component.MapRenderer
import org.koin.dsl.module

val mapModule = module {
    single<MapRenderer> { LogMapRenderer() }
}
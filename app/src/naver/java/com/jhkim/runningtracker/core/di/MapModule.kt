package com.jhkim.runningtracker.core.di

import com.jhkim.runningtracker.presentation.component.MapRenderer
import com.jhkim.runningtracker.presentation.component.NaverMapRenderer
import org.koin.dsl.module

val mapModule = module {
    single<MapRenderer> { NaverMapRenderer() }
}
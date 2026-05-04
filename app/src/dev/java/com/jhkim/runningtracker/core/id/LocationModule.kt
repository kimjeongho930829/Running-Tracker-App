package com.jhkim.runningtracker.core.id

import com.jhkim.runningtracker.data.location.MockLocationClient
import com.jhkim.runningtracker.domain.location.LocationClient
import org.koin.dsl.module

val locationModule = module {
    single<LocationClient> { MockLocationClient() }
}
package com.jhkim.runningtracker.domain.location

import com.jhkim.runningtracker.domain.model.LocationPoint
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval: Long): Flow<LocationPoint>
}
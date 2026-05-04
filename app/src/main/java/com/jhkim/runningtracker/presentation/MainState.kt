package com.jhkim.runningtracker.presentation

import com.jhkim.runningtracker.domain.model.LocationPoint
import com.jhkim.runningtracker.domain.model.GpsStatus
import com.jhkim.runningtracker.domain.model.Run
import com.jhkim.runningtracker.domain.model.SortType
import com.jhkim.runningtracker.presentation.service.TrackingState

data class MainState(
    val runs: List<Run> = listOf(),
    val sortType: SortType = SortType.DATA,
    val gpsStatus: GpsStatus = GpsStatus.Acquired,
    val trackingState: TrackingState = TrackingState(),
    val selectedRun: Run? = null,
    val displayPathPoints: List<LocationPoint> = listOf(),

    val isGpsMockingEnabled: Boolean = false
)

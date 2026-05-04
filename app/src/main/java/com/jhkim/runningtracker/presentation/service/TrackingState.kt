package com.jhkim.runningtracker.presentation.service

import com.jhkim.runningtracker.domain.model.LocationPoint

data class TrackingState(
    val isTracking: Boolean = false,
    val pathPoints: List<LocationPoint> = listOf(),
    val distanceInMeters: Double = 0.0,
    val timeInMillis: Long = 0L,
    val avgSpeedInKMH: Float = 0f,
)

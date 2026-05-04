package com.jhkim.runningtracker.domain.model

data class Run(
    val id: Int? = null,
    val distanceInMeters: Double = 0.0,
    val timeInMillis: Long = 0L,
    val timestamp: Long = 0L,
    val avgSpeedInKMH: Float = 0f,
    val pathPoints: List<LocationPoint> = listOf(),
)

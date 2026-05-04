package com.jhkim.runningtracker.core.util

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

object TrackingCalculator {
    fun calculateDistance(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double,
    ): Double {
        val r = 6371000.0 // Earth radius in meters
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }

    // km/h
    fun calculateAvgSpeed(
        distanceInMeters: Double,
        timeInMillis: Long,
    ) : Float {
        val timeInSeconds = timeInMillis / 1000f
        return if (timeInSeconds > 0) {
            ((distanceInMeters / timeInSeconds) * 3.6).toFloat()
        } else {
            0f
        }
    }
}
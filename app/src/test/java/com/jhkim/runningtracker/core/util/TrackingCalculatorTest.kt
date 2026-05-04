package com.jhkim.runningtracker.core.util

import org.junit.Assert.*

import org.junit.Test

class TrackingCalculatorTest {
    @Test
    fun calculateAvgSpeed() {
        val expected = 10f
        val result = TrackingCalculator.calculateAvgSpeed(
            distanceInMeters = 10000.0,
            timeInMillis = 3600000,
        )

        assertEquals(expected, result, 0.1f)
    }
}
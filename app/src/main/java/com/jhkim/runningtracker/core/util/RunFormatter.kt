package com.jhkim.runningtracker.core.util

import java.util.Locale
import java.util.concurrent.TimeUnit

object RunFormatter {
    fun formatDistance(meters: Double): String {
        return String.format(Locale.getDefault(), "%.2f km", meters / 1000.0)
    }

    fun formatTime(ms: Long): String {
        val hour = TimeUnit.MILLISECONDS.toHours(ms)
        val min = TimeUnit.MILLISECONDS.toMinutes(ms) % 60
        val sec = TimeUnit.MILLISECONDS.toSeconds(ms) % 60
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, min, sec)
    }

    fun formatSpeed(speedKMH: Float): String {
        return String.format(Locale.getDefault(), "%.1f km/h", speedKMH)
    }
}
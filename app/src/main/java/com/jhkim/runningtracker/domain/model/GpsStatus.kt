package com.jhkim.runningtracker.domain.model

sealed interface GpsStatus {
    data object Disabled: GpsStatus
    data object Enabled: GpsStatus
    data object Acquired: GpsStatus
    data object Lost: GpsStatus
}
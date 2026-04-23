package com.jhkim.runningtracker.presentation.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TrackingManager {
    private val _state = MutableStateFlow(TrackingState())
    val state = _state.asStateFlow()

    fun updateTrackingState(isTracking: Boolean) {
        _state.update {
            TrackingState(isTracking = isTracking)
        }
    }
}
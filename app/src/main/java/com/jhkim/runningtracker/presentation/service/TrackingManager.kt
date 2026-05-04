package com.jhkim.runningtracker.presentation.service

import com.jhkim.runningtracker.domain.location.LocationClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class TrackingManager(
    private val locationClient: LocationClient,
    private val runningTimer: RunningTimer,
) {
    private val _state = MutableStateFlow(TrackingState())
    val state = _state.asStateFlow()

    private var timerJob: Job? = null
    private var locationJob: Job? = null

    fun startTracking(scope: CoroutineScope) {
        _state.update {
            TrackingState(isTracking = true)
        }
        startLocationTracking(scope)
        startTimer(scope)
    }

    fun stopTracking() {
        _state.update {
            TrackingState(isTracking = false)
        }
        timerJob?.cancel()
        locationJob?.cancel()
        timerJob = null
        locationJob = null
        runningTimer.stop()
    }

    private fun startTimer(scope: CoroutineScope) {
        runningTimer.start(scope)
        timerJob?.cancel()
        timerJob =  runningTimer.timeInMills
            .onEach { time ->
                _state.update {
                    if (it.isTracking) {
                        it.copy(timeInMillis =  time)
                    } else it
                }
            }.launchIn(scope)
    }

    private fun startLocationTracking(scope: CoroutineScope) {
        locationJob?.cancel()
        locationJob = locationClient.getLocationUpdates(1000L)
            .onEach { point ->
                _state.update { currentState ->
                    if (!currentState.isTracking) return@update currentState

                    currentState.copy(pathPoints = (currentState.pathPoints + point).toList())

                }
            }.launchIn(scope)
    }
}
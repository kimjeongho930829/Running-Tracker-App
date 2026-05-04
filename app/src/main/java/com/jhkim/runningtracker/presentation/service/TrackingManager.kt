package com.jhkim.runningtracker.presentation.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class TrackingManager(
    private val runningTimer: RunningTimer,
) {
    private val _state = MutableStateFlow(TrackingState())
    val state = _state.asStateFlow()

    private var timerJob: Job? = null

    fun startTracking(scope: CoroutineScope) {
        _state.update {
            TrackingState(isTracking = true)
        }
        startTimer(scope)
    }

    fun stopTracking() {
        _state.update {
            TrackingState(isTracking = false)
        }
        timerJob?.cancel()
        timerJob = null
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
}
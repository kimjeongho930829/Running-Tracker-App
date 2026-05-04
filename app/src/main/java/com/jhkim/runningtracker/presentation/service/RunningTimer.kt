package com.jhkim.runningtracker.presentation.service

import android.os.SystemClock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RunningTimer {
    private val _timeInMills = MutableStateFlow(0L)
    val timeInMills = _timeInMills.asStateFlow()

    private var timerJop: Job? = null
    private var startTime = 0L

    fun start(scope: CoroutineScope) {
        startTime = SystemClock.elapsedRealtime()
        timerJop = scope.launch {
            while (true) {
                _timeInMills.value = SystemClock.elapsedRealtime() - startTime
                delay(50L)
            }
        }
    }

    fun stop() {
        timerJop?.cancel()
        timerJop = null
        _timeInMills.value = 0L
        startTime = 0L
    }
}
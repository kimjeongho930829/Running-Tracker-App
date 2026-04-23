@file:OptIn(ExperimentalCoroutinesApi::class)

package com.jhkim.runningtracker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhkim.runningtracker.domain.model.Run
import com.jhkim.runningtracker.domain.model.SortType
import com.jhkim.runningtracker.domain.use_case.DeleteRunUseCase
import com.jhkim.runningtracker.domain.use_case.GetRunsUseCase
import com.jhkim.runningtracker.domain.use_case.SaveRunUseCase
import com.jhkim.runningtracker.presentation.service.TrackingManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val saveRunUseCase: SaveRunUseCase,
    private val deleteRunUseCase: DeleteRunUseCase,
    private val getRunsUseCase: GetRunsUseCase,
    private val trackingManager: TrackingManager,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<MainEvent>()
    val event: SharedFlow<MainEvent> = _event.asSharedFlow()

    init {
        observeRunsSortType()
        observeTrackingState()
    }

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.ChangeSortType -> changeSortType(action.sortType)
            MainAction.ClickRun -> clickRun()
            is MainAction.DeleteRun -> deleteRun(action.run)
            MainAction.FinishRun -> finishRun()
            is MainAction.SelectRun -> selectRun(action.run)
            MainAction.ToggleGpsStatus -> TODO()
        }
    }

    private fun clickRun() {
        val isTracking = state.value.trackingState.isTracking
        viewModelScope.launch {
            if (!isTracking) {
                _state.update { it.copy(selectedRun = null) }
                _event.emit(MainEvent.StartTracking)

                // TODO: Refactoring
                trackingManager.updateTrackingState(true)
            } else {
                _event.emit(MainEvent.StopTracking)

                // TODO: Refactoring
                trackingManager.updateTrackingState(false)
            }
        }
    }

    private fun finishRun() {
        val trackingState = state.value.trackingState
        val run = Run(
            distanceInMeters = trackingState.distanceInMeters,
            timeInMillis = trackingState.timeInMillis,
            timestamp = System.currentTimeMillis(),
            avgSpeedInKMH = trackingState.avgSpeedInKMH,
            pathPoints = trackingState.pathPoints,
        )
        viewModelScope.launch {
            _event.emit(MainEvent.StopTracking)
            saveRunUseCase.execute(run)
            _event.emit(MainEvent.RunSaved)
        }
    }

    private fun deleteRun(run: Run) {
        viewModelScope.launch {
            deleteRunUseCase.execute(run)
            if (state.value.selectedRun?.id == run.id) {
                _state.update { it.copy(selectedRun = null) }
            }
        }
    }

    private fun selectRun(run: Run) {
        _state.update {
            it.copy(
                selectedRun = run,
                displayPathPoints =
                    if (!it.trackingState.isTracking)
                        run.pathPoints
                    else
                        it.displayPathPoints
            )
        }
    }

    private fun changeSortType(sortType: SortType) {
        if (state.value.sortType != sortType) {
            _state.update { it.copy(sortType = sortType) }
        }
    }

    private fun observeRunsSortType() {
        _state.map { it.sortType }
            .distinctUntilChanged()
            .flatMapLatest { sortType -> getRunsUseCase.execute(sortType) }
            .onEach { runs -> _state.update { it.copy(runs = runs.toList()) } }
            .launchIn(viewModelScope)
    }

    private fun observeTrackingState() {
        trackingManager.state.onEach { tracingState ->
            _state.update {
                it.copy(
                    trackingState = tracingState,
                    displayPathPoints = if (tracingState.isTracking)
                        tracingState.pathPoints
                    else it.displayPathPoints
                )
            }
        }.launchIn(viewModelScope)
    }
}
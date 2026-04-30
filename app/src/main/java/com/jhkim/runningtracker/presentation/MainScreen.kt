package com.jhkim.runningtracker.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jhkim.runningtracker.domain.model.GpsStatus
import com.jhkim.runningtracker.domain.model.Run
import com.jhkim.runningtracker.presentation.component.GpsStatusBadge
import com.jhkim.runningtracker.presentation.component.MapRenderer
import com.jhkim.runningtracker.presentation.component.LogMapRenderer
import com.jhkim.runningtracker.presentation.component.RunItem
import com.jhkim.runningtracker.presentation.component.SortTypeSelector
import com.jhkim.runningtracker.presentation.component.TrackingOverlay
import com.jhkim.runningtracker.presentation.service.TrackingState
import com.jhkim.runningtracker.ui.theme.AppTheme

@Composable
fun MainScreen(
    state: MainState,
    onAction: (MainAction) -> Unit,
    mapRenderer: MapRenderer,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.normal)
        ) {
            Text(
                text = "Running Tracker",
                style = AppTheme.typography.h1,
                color = AppTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(AppTheme.spacing.normal))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(AppTheme.colors.surface)
            ) {
                mapRenderer.DrawMap(
                    pathPoints = state.displayPathPoints
                )

                GpsStatusBadge(
                    status = state.gpsStatus,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(AppTheme.spacing.small),
                    onClick = if (state.isGpsMockingEnabled) {
                        { onAction(MainAction.ToggleGpsStatus) }
                    } else null,
                )
            }

            Spacer(modifier = Modifier.height(AppTheme.spacing.normal))

            SortTypeSelector(
                selectedSortType = state.sortType,
                onSortTypeChange = {
                    onAction(MainAction.ChangeSortType(it))
                }
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(
                    items = state.runs
                ) { run ->
                    RunItem(
                        run = run,
                        onDelete = { onAction(MainAction.DeleteRun(run)) },
                        onSelect = { onAction(MainAction.SelectRun(run)) },
                    )
                    Spacer(modifier = Modifier.height(AppTheme.spacing.small))
                }
            }
        }

        AnimatedVisibility(
            visible = state.trackingState.isTracking,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            TrackingOverlay(
                trackingState = state.trackingState,
                onFinish = {
                    onAction(MainAction.FinishRun)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    var _state by remember { mutableStateOf(
        MainState(
            gpsStatus = GpsStatus.Acquired,
            isGpsMockingEnabled = true,
            runs = listOf(
                Run(
                    id = 1,
                    distanceInMeters = 5240.0,
                    timeInMillis = 1850000,
                    timestamp = System.currentTimeMillis(),
                    avgSpeedInKMH = 10.2f,
                ),
                Run(
                    id = 2,
                    distanceInMeters = 3000.0,
                    timeInMillis = 120000,
                    timestamp = System.currentTimeMillis(),
                    avgSpeedInKMH = 9.2f,
                )
            )
        )
    ) }

    MainScreen(
        state = _state,
        onAction = { action ->
            if (action is MainAction.ToggleGpsStatus) {
                _state = _state.copy(
                    gpsStatus = when (_state.gpsStatus) {
                        GpsStatus.Acquired -> GpsStatus.Lost
                        GpsStatus.Disabled -> GpsStatus.Enabled
                        GpsStatus.Enabled -> GpsStatus.Acquired
                        GpsStatus.Lost -> GpsStatus.Disabled
                    }
                )
            }

            if (action is MainAction.ChangeSortType) {
                _state = _state.copy(
                    sortType = action.sortType
                )
            }
        },
        mapRenderer = LogMapRenderer(),
    )
}

@Preview(showBackground = true)
@Composable
private fun MainScreenTrackingPreview() {
    var _state by remember { mutableStateOf(
        MainState(
            trackingState = TrackingState(
                isTracking = true,
                distanceInMeters = 10000.0,
                timeInMillis = 11000000,
            ),
            gpsStatus = GpsStatus.Acquired,
            isGpsMockingEnabled = true,
            runs = listOf(
                Run(
                    id = 1,
                    distanceInMeters = 5240.0,
                    timeInMillis = 1850000,
                    timestamp = System.currentTimeMillis(),
                    avgSpeedInKMH = 10.2f,
                ),
                Run(
                    id = 2,
                    distanceInMeters = 3000.0,
                    timeInMillis = 120000,
                    timestamp = System.currentTimeMillis(),
                    avgSpeedInKMH = 9.2f,
                )
            )
        )
    ) }

    MainScreen(
        state = _state,
        onAction = { action ->
            if (action is MainAction.ToggleGpsStatus) {
                _state = _state.copy(
                    gpsStatus = when (_state.gpsStatus) {
                        GpsStatus.Acquired -> GpsStatus.Lost
                        GpsStatus.Disabled -> GpsStatus.Enabled
                        GpsStatus.Enabled -> GpsStatus.Acquired
                        GpsStatus.Lost -> GpsStatus.Disabled
                    }
                )
            }

            if (action is MainAction.ChangeSortType) {
                _state = _state.copy(
                    sortType = action.sortType
                )
            }
        },
        mapRenderer = LogMapRenderer(),
    )
}
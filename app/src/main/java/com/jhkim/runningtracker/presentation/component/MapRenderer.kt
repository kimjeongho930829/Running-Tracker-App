package com.jhkim.runningtracker.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.jhkim.runningtracker.domain.location.LocationPoint

@Stable
interface MapRenderer {

    @Composable
    fun DrawMap(pathPoints: List<LocationPoint>)
}
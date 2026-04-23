package com.jhkim.runningtracker.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class RunningTrackerSpacing(
    val default: Dp = 0.dp,
    val tiny: Dp = 4.dp,
    val small: Dp = 8.dp,
    val normal: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp,
)

val LocalRunningTrackerSpacing = staticCompositionLocalOf {
    RunningTrackerSpacing()
}
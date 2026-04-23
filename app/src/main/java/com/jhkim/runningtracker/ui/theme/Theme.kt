package com.jhkim.runningtracker.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun RunningTrackerTheme(
    colors: RunningTrackerColors = DarkColorPalette,
    typography: RunningTrackerTypography= DefaultTypography,
    spacing: RunningTrackerSpacing= RunningTrackerSpacing(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalRunningTrackerColors provides colors,
        LocalRunningTrackerTypography provides typography,
        LocalRunningTrackerSpacing provides spacing,
    ) {
        content()
    }
}

object AppTheme {
    val colors: RunningTrackerColors
        @Composable
        get() = LocalRunningTrackerColors.current

    val typography: RunningTrackerTypography
        @Composable
        get() = LocalRunningTrackerTypography.current

    val spacing: RunningTrackerSpacing
        @Composable
        get() = LocalRunningTrackerSpacing.current
}
package com.jhkim.runningtracker.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class RunningTrackerColors(
    val primary: Color,
    val onPrimary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val secondaryText: Color,
    val accent: Color,
    val error: Color,
    val success: Color,
    val warning: Color,
)

val DarkColorPalette = RunningTrackerColors(
    primary = Color(0xFF00E5FF),
    onPrimary = Color(0xFF000000),
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF1E293B), // Navy/Grey Surface
    onSurface = Color(0xFFF1F5F9),
    secondaryText = Color(0xFF94A3B8),
    accent = Color(0xFFFF4081),
    error = Color(0xFFEF4444),
    success = Color(0xFF10B981), // Emerald Green
    warning = Color(0xFFF59E0B),
)

val LocalRunningTrackerColors = staticCompositionLocalOf {
    DarkColorPalette
}
package com.jhkim.runningtracker.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jhkim.runningtracker.presentation.component.LogMapRenderer
import com.jhkim.runningtracker.presentation.component.MapRenderer
import com.jhkim.runningtracker.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainRoot(
    viewModel: MainViewModel = koinViewModel(),
    mapRenderer: MapRenderer = LogMapRenderer(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = !state.trackingState.isTracking,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut(),
            ) {
                FloatingActionButton(
                    onClick = {
                        viewModel.onAction(MainAction.ClickRun)
                    },
                    containerColor = AppTheme.colors.primary,
                    contentColor = AppTheme.colors.onPrimary,
                    shape = CircleShape,
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Start Run"
                    )
                }
            }
        },
        contentColor = AppTheme.colors.background
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            MainScreen(
                state = state,
                onAction = { action ->
                    viewModel.onAction(action)
                },
                mapRenderer = mapRenderer
            )
        }
    }
}
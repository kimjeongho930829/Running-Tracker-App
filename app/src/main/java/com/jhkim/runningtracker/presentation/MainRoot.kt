package com.jhkim.runningtracker.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jhkim.runningtracker.presentation.component.LogMapRenderer
import com.jhkim.runningtracker.presentation.component.MapRenderer
import com.jhkim.runningtracker.presentation.service.TrackingService
import com.jhkim.runningtracker.presentation.service.TrackingState
import com.jhkim.runningtracker.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainRoot(
    viewModel: MainViewModel = koinViewModel(),
    mapRenderer: MapRenderer = LogMapRenderer(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val permissions = mutableListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    ).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val allGranted = result.values.all { it }
        if (allGranted) {
            if (state.trackingState.isTracking) {
                val intent = Intent(context, TrackingService::class.java).apply {
                    action = TrackingService.ACTION_START
                }
                ContextCompat.startForegroundService(context, intent)
            }
        } else {
            // 권한 거부시 경고
        }
    }

    LaunchedEffect(true) {
        viewModel.event.collect{ event ->
            when (event) {
                is MainEvent.PermissionRequired -> TODO()
                MainEvent.RunSaved -> TODO()
                is MainEvent.ShowSnackbar -> TODO()
                MainEvent.StartTracking -> {
                    val hasAllPermissions = permissions.all {
                        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                    }

                    if (hasAllPermissions) {
                        val intent = Intent(context, TrackingService::class.java).apply {
                            action = TrackingService.ACTION_START
                        }
                        ContextCompat.startForegroundService(context, intent)
                    } else {
                        permissionLauncher.launch(permissions.toTypedArray())
                    }
                }
                MainEvent.StopTracking -> {
                    val intent = Intent(context, TrackingService::class.java).apply {
                        action = TrackingService.ACTION_STOP
                    }
                    context.startService(intent)
                }
            }
        }
    }

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
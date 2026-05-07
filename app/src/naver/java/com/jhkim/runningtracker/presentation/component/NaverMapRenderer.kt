package com.jhkim.runningtracker.presentation.component

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.jhkim.runningtracker.domain.model.LocationPoint
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.ArrowheadPathOverlay

class NaverMapRenderer: MapRenderer {
    @Composable
    override fun DrawMap(pathPoints: List<LocationPoint>) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current

        var naverMap by remember { mutableStateOf<NaverMap?>(null) }
        val arrowOverlay = remember { ArrowheadPathOverlay() }

        val mapView = remember {
            MapView(context).apply {
                getMapAsync {
                    naverMap = it
                }
            }
        }

        // Draw Line
        LaunchedEffect(pathPoints, naverMap) {
            val map = naverMap ?: return@LaunchedEffect

            if (pathPoints.size >= 2) {
                arrowOverlay.coords = pathPoints.map {
                    LatLng(it.latitude, it.longitude)
                }
                arrowOverlay.color = Color.BLUE
                arrowOverlay.outlineWidth = 2
                arrowOverlay.map = map
            } else {
                arrowOverlay.map = null
            }
        }

        // Camera Update
        LaunchedEffect(pathPoints, naverMap) {
            val lastPoint = pathPoints.lastOrNull() ?: return@LaunchedEffect
            val map = naverMap ?: return@LaunchedEffect

            val cameraUpdate = CameraUpdate.scrollTo(
                LatLng(
                    lastPoint.latitude,
                    lastPoint.longitude,
                )
            ).animate(CameraAnimation.Easing)

            map.moveCamera(cameraUpdate)
        }

        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxSize()
        )

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_CREATE -> mapView.onCreate(null)
                    Lifecycle.Event.ON_START -> mapView.onStart()
                    Lifecycle.Event.ON_RESUME -> mapView.onResume()
                    Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                    Lifecycle.Event.ON_STOP -> mapView.onStop()
                    Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                    else -> {}
                }

            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }
    }
}
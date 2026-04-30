package com.jhkim.runningtracker.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.jhkim.runningtracker.domain.location.LocationPoint
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap

class NaverMapRenderer: MapRenderer {
    @Composable
    override fun DrawMap(pathPoints: List<LocationPoint>) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current

        var naverMap by remember { mutableStateOf<NaverMap?>(null) }
        val mapView = remember {
            MapView(context).apply {
                getMapAsync {
                    naverMap = it
                }
            }
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
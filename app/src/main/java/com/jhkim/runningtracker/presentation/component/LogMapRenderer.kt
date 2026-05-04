package com.jhkim.runningtracker.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jhkim.runningtracker.domain.model.LocationPoint

class LogMapRenderer: MapRenderer {
    @Composable
    override fun DrawMap(pathPoints: List<LocationPoint>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = "--- 지도 로그 (Mock) ---",
                    color = Color.White,
                )
            }
            items(pathPoints.takeLast(5)) { point ->
                Text(
                    text = "위도: ${point.latitude}, 경도: ${point.longitude}",
                    color = Color.White
                )
            }
        }
    }
}
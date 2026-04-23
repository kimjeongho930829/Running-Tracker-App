package com.jhkim.runningtracker.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jhkim.runningtracker.core.util.RunFormatter
import com.jhkim.runningtracker.presentation.service.TrackingState
import com.jhkim.runningtracker.ui.theme.AppTheme

@Composable
fun TrackingOverlay(
    modifier: Modifier = Modifier,
    trackingState: TrackingState,
    onFinish: () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth().padding(AppTheme.spacing.normal),
        shape = RoundedCornerShape(AppTheme.spacing.large),
        color = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        tonalElevation = AppTheme.spacing.small,
    ) {
        Row(
            modifier = Modifier.padding(AppTheme.spacing.normal).fillMaxWidth(),
            verticalAlignment =  Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                TrackingInfoItem(
                    label = "Distance",
                    value = RunFormatter.formatDistance(trackingState.distanceInMeters)
                )
                VerticalDivider(
                    modifier = Modifier.height(32.dp),
                    color = AppTheme.colors.onPrimary.copy(alpha = 0.2f)
                )
                TrackingInfoItem(
                    label = "Time",
                    value = RunFormatter.formatTime(trackingState.timeInMillis)
                )
            }
            IconButton(
                onClick = onFinish,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color = AppTheme.colors.onPrimary.copy(alpha = 0.2f))
            ) {
                Icon(
                    imageVector = Icons.Default.Stop,
                    contentDescription = "Finish",
                    tint = AppTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun TrackingInfoItem(
    label: String,
    value: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = AppTheme.typography.caption,
            color = AppTheme.colors.onPrimary.copy(alpha = 0.8f),
        )
        Text(
            text = value,
            style = AppTheme.typography.h3,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TrackingOverlayPreview() {
    TrackingOverlay(
        trackingState = TrackingState(
            isTracking = true,
            distanceInMeters = 1250.0,
            timeInMillis = 480000,
            avgSpeedInKMH = 9.4f,
        ),
        onFinish = {}
    )
}
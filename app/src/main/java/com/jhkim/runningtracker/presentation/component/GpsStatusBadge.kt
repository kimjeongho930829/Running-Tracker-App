package com.jhkim.runningtracker.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jhkim.runningtracker.domain.model.GpsStatus
import com.jhkim.runningtracker.ui.theme.AppTheme

@Composable
fun GpsStatusBadge(
    modifier: Modifier = Modifier,
    status: GpsStatus,
    onClick: (() -> Unit)? = null,
) {
    val color: Color
    val text: String
    val icon: ImageVector

    when (status) {
        GpsStatus.Acquired -> {
            color = AppTheme.colors.warning
            text = "Searching GPS..."
            icon = Icons.Default.Refresh
        }
        GpsStatus.Disabled -> {
            color = AppTheme.colors.secondaryText
            text = "GPS Disabled"
            icon = Icons.Default.Block
        }
        GpsStatus.Enabled -> {
            color = AppTheme.colors.success
            text = "GPS Connected"
            icon = Icons.Default.Map
        }
        GpsStatus.Lost -> {
            color = AppTheme.colors.error
            text = "GPS Lost"
            icon = Icons.Default.Warning
        }
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.9f),
        contentColor = Color.White,
        modifier = modifier.then(
            other = if (onClick != null) {
                Modifier.clickable { onClick() }
            } else {
                Modifier
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = text,
                style = AppTheme.typography.caption,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GpsStatusBadgePreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GpsStatusBadge(status = GpsStatus.Lost)
        GpsStatusBadge(status = GpsStatus.Enabled)
        GpsStatusBadge(status = GpsStatus.Disabled)
        GpsStatusBadge(status = GpsStatus.Acquired)
    }
}
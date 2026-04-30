package com.jhkim.runningtracker.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service.NOTIFICATION_SERVICE
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.jhkim.runningtracker.MainActivity
import com.jhkim.runningtracker.R

class TrackingNotificationHelper(
    private val context: Context,
) {
    private val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 일반 트래킹용 채널 (조용히 유지)
            val trackingChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(trackingChannel)

            // 경고, 알림용 채널 (소리 및 팝업 활성화)
            val alertChannel = NotificationChannel(
                ALERT_CHANNEL_ID,
                ALERT_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(alertChannel)
        }
    }

    fun buildTrackingNotification(): Notification {
        val pendingIntent = createPendingIntent(0)
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Running Tracker")
            .setContentText("운동 중...")
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createPendingIntent(requestCode: Int): PendingIntent {
        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return PendingIntent.getActivity(
            context,
            requestCode,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "tracking_channel_v1"
        const val NOTIFICATION_CHANNEL_NAME = "Tracking"
        const val ALERT_CHANNEL_ID = "alert_channel_v1"
        const val ALERT_CHANNEL_NAME = "Alerts"
        const val TRACKING_NOTIFICATION_ID = 1
    }
}
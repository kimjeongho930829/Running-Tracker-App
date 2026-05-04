package com.jhkim.runningtracker.presentation.service

import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import org.koin.android.ext.android.inject

class TrackingService : LifecycleService() {
    private val trackingManager: TrackingManager by inject()
    private val notificationHelper: TrackingNotificationHelper by inject()

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when (intent?.action) {
            ACTION_START -> startForegroundService()
            ACTION_STOP -> stopForegroundService()
        }
        return START_STICKY
    }

    private fun startForegroundService() {
        notificationHelper.createNotificationChannels()
        val notification = notificationHelper.buildTrackingNotification()
        startForeground(TrackingNotificationHelper.TRACKING_NOTIFICATION_ID, notification)
        trackingManager.startTracking(lifecycleScope)
    }

    private fun stopForegroundService() {
        trackingManager.stopTracking()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}
package com.jhkim.runningtracker.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jhkim.runningtracker.domain.model.LocationPoint
import com.jhkim.runningtracker.domain.model.Run

@Entity(tableName = "runs")
data class RunEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val distanceInMeters: Double,
    val timeInMillis: Long,
    val timestamp: Long,
    val avgSpeedInKMH: Float,
    val pathPoints: List<LocationPoint>,
) {
    fun toRun(): Run = Run(
        id = id,
        distanceInMeters = distanceInMeters,
        timeInMillis = timeInMillis,
        timestamp = timestamp,
        avgSpeedInKMH = avgSpeedInKMH,
        pathPoints = pathPoints,
    )

    companion object {
        fun fromRun(run: Run): RunEntity = RunEntity(
            id = run.id,
            distanceInMeters = run.distanceInMeters,
            timeInMillis = run.timeInMillis,
            timestamp = run.timestamp,
            avgSpeedInKMH = run.avgSpeedInKMH,
            pathPoints = run.pathPoints,
        )
    }
}
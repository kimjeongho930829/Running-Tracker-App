package com.jhkim.runningtracker.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RunDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: RunEntity)

    @Delete
    suspend fun deleteRun(run: RunEntity)

    @Query("SELECT * FROM runs")
    fun getAllRuns(): Flow<List<RunEntity>>

    @Query("SELECT * FROM runs ORDER BY timestamp")
    fun getAllRunsSortedByDate(): Flow<List<RunEntity>>

    @Query("SELECT * FROM runs ORDER BY timeInMillis")
    fun getAllRunsSortedByTimeInMillis(): Flow<List<RunEntity>>

    @Query("SELECT * FROM runs ORDER BY avgSpeedInKMH")
    fun getAllRunsSortedByAvgSpeed(): Flow<List<RunEntity>>

    @Query("SELECT * FROM runs ORDER BY distanceInMeters")
    fun getAllRunsSortedByDistance(): Flow<List<RunEntity>>
}
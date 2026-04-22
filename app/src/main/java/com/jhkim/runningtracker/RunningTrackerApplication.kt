package com.jhkim.runningtracker

import android.app.Application
import com.jhkim.runningtracker.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RunningTrackerApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}
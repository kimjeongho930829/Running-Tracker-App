package com.jhkim.runningtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jhkim.runningtracker.presentation.MainRoot
import com.jhkim.runningtracker.ui.theme.RunningTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RunningTrackerTheme {
                MainRoot()
            }
        }
    }
}

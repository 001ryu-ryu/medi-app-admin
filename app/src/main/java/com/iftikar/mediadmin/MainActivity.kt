package com.iftikar.mediadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.iftikar.mediadmin.navigation.NavApp
import com.iftikar.mediadmin.ui.theme.MediAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediAdminTheme(dynamicColor = false) {
                NavApp()
            }
        }
    }
}
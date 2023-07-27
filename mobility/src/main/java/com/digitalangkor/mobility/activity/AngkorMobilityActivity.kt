package com.digitalangkor.mobility.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.digitalangkor.mobility.screen.AngkorMobilityMainScreen
import com.digitalangkor.mobility.screen.SplashScreen
import kotlinx.coroutines.delay

class AngkorMobilityActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var isSplash by rememberSaveable {
                mutableStateOf(true)
            }

            AnimatedVisibility(visible = isSplash, enter = fadeIn(), exit = fadeOut()) {
                SplashScreen {
                    isSplash = false
                }
            }

            AnimatedVisibility(visible = !isSplash, enter = fadeIn(), exit = fadeOut()) {
                AngkorMobilityMainScreen()
            }
        }
    }
}
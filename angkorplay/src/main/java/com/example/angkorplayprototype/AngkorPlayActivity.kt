package com.example.angkorplayprototype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.angkorplayprototype.ui.theme.AngkorPlayPrototypeTheme
import kotlinx.coroutines.delay

class AngkorPlayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AngkorPlayPrototypeTheme {
                var isSplash by rememberSaveable {
                    mutableStateOf(true)
                }
                LaunchedEffect(true) {
                    delay(1000)
                    isSplash = false
                }

                AnimatedVisibility(visible = isSplash, enter = fadeIn(), exit = fadeOut()) {
                    SplashScreen()
                }

                AnimatedVisibility(visible = !isSplash, enter = fadeIn(), exit = fadeOut()) {
                    AngkorPlayMainScreen(
                        mainPosters = mainPosters,
                        watchingContents = watchingContents,
                        popularPosters = popularPosters,
                        kContents = kContents,
                        newContents = newContents
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AngkorPlayPrototypeTheme {
        Greeting("Android")
    }
}
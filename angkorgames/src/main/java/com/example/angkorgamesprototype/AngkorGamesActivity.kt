package com.example.angkorgamesprototype

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
import com.example.angkorgamesprototype.main.Game
import com.example.angkorgamesprototype.main.AngkorGamesMainScreen
import com.example.angkorgamesprototype.splash.AngkorGamesSplashScreen
import com.example.angkorgamesprototype.ui.theme.AngkorGamesPrototypeTheme
import kotlinx.coroutines.delay

class AngkorGamesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isSplash by rememberSaveable {
                mutableStateOf(true)
            }
            LaunchedEffect(true) {
                delay(1000)
                isSplash = false
            }

            AnimatedVisibility(visible = isSplash, enter = fadeIn(), exit = fadeOut()) {
                AngkorGamesSplashScreen()
            }

            AnimatedVisibility(visible = !isSplash, enter = fadeIn(), exit = fadeOut()) {
                AngkorGamesMainScreen(
                    popularGames = listOf(
                        R.drawable.angkor_games_img_main_banner_1,
                        R.drawable.angkor_games_img_main_banner_2,
                        R.drawable.angkor_games_img_main_banner_3
                    ),
                    preOrderGames = listOf(
                        Game(
                            imageResourceId = R.drawable.img_pre_order_battlegrounds_h_96,
                            name = "Battle grounds",
                            description = "Let’s enjoy playing fun games together!"
                        ),
                        Game(
                            imageResourceId = R.drawable.img_pre_order_clashroyale_h_96,
                            name = "Clash Royale",
                            description = "Let’s enjoy playing fun games together!"
                        ),
                        Game(
                            imageResourceId = R.drawable.img_pre_order_lineage_2_h_96,
                            name = "Lineage2",
                            description = "Let’s enjoy playing fun games together!"
                        )
                    ),
                    recommendGames = listOf(
                        Game(
                            imageResourceId = R.drawable.img_recommended_risekingdoms_h_96,
                            name = "Rise Kingdoms",
                            description = "Let’s enjoy playing fun games together!"
                        ),
                        Game(
                            imageResourceId = R.drawable.img_recommended_amongus_h_96,
                            name = "Among Us",
                            description = "Let’s enjoy playing fun games together!"
                        ),
                        Game(
                            imageResourceId = R.drawable.img_recommended_mafiacity_h_96,
                            name = "Mafia City",
                            description = "Let’s enjoy playing fun games together!"
                        )
                    )
                )
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
    AngkorGamesPrototypeTheme {
        Greeting("Android")
    }
}
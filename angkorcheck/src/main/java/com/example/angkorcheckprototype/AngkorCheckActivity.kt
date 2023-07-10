package com.example.angkorcheckprototype

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
import com.example.angkorcheckprototype.card.HotelCard
import com.example.angkorcheckprototype.ui.theme.AngkorCheckPrototypeTheme
import kotlinx.coroutines.delay

class AngkorCheckActivity : ComponentActivity() {
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
                AngkorCheckSplashScreen()
            }

            AnimatedVisibility(visible = !isSplash, enter = fadeIn(), exit = fadeOut()) {
                val hotelCards = listOf(
                    HotelCard(
                        name = "Angkor Hotel",
                        images = List(5) { R.drawable.img_list_1 },
                        pricePerDay = 200,
                        reviewsCount = 123,
                        star = 4.9f
                    ),
                    HotelCard(
                        name = "Angkor Hotel",
                        images = List(5) { R.drawable.img_list_2 },
                        pricePerDay = 200,
                        reviewsCount = 123,
                        star = 4.9f
                    ),
                    HotelCard(
                        name = "Angkor Hotel",
                        images = List(5) { R.drawable.img_list_3 },
                        pricePerDay = 200,
                        reviewsCount = 123,
                        star = 4.9f
                    )
                )

                val popularHotelCards = listOf(
                    HotelCard(
                        name = "Angkor Hotel",
                        images = List(5) { R.drawable.img_popular_1 },
                        pricePerDay = 200,
                        reviewsCount = 123,
                        star = 4.9f
                    ),
                    HotelCard(
                        name = "Angkor Hotel",
                        images = List(5) { R.drawable.img_popular_2 },
                        pricePerDay = 200,
                        reviewsCount = 123,
                        star = 4.9f
                    ),
                    HotelCard(
                        name = "Angkor Hotel",
                        images = List(5) { R.drawable.img_list_2 },
                        pricePerDay = 200,
                        reviewsCount = 123,
                        star = 4.9f
                    )
                )

                AngkorCheckMainScreen(popularHotels = popularHotelCards, hotels = hotelCards)
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
    AngkorCheckPrototypeTheme {
        Greeting("Android")
    }
}
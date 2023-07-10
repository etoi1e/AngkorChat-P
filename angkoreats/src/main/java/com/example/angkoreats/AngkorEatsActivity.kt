package com.example.angkoreats

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
import com.example.angkoreats.home.AngkorEatsHomeScreen
import com.example.angkoreats.home.state.CategoryUiState
import com.example.angkoreats.home.state.RestaurantUiState
import com.example.angkoreats.splash.AngkorEatsSplashScreen
import com.example.angkoreats.ui.compose.theme.AngkorEatsTheme
import kotlinx.coroutines.delay

class AngkorEatsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isSplash by rememberSaveable {
                mutableStateOf(true)
            }
            LaunchedEffect( true){
                delay(1000)
                isSplash = false
            }

            AnimatedVisibility(visible = isSplash, enter = fadeIn(), exit = fadeOut()) {
                AngkorEatsSplashScreen()
            }

            AnimatedVisibility(visible = !isSplash, enter = fadeIn(), exit = fadeOut()){
                val categoryList = listOf(
                    CategoryUiState(R.drawable.img_category_bbq_72,"BBQ"),
                    CategoryUiState(R.drawable.img_category_pizza_72,"Pizza"),
                    CategoryUiState(R.drawable.img_category_hamburger_72,"Hamburger"),
                    CategoryUiState(R.drawable.img_category_drinks_72,"Drinks"),
                    CategoryUiState(R.drawable.img_category_pizza_72,"Pizza"),
                    CategoryUiState(R.drawable.img_category_popular_72,"Popular"),
                )

                val restaurantList = listOf(
                    RestaurantUiState(R.drawable.img_all_restaurants_1_144,"BBQ Park Phnom Penh","20~25 min"),
                    RestaurantUiState(R.drawable.img_all_restaurants_2_144,"The Lobster Place","25~30 min"),
                    RestaurantUiState(R.drawable.img_category_pizza_72,"The Pizza","35~40 min"),
                )
            AngkorEatsHomeScreen(categoryList = categoryList ,restaurantList = restaurantList)
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
    AngkorEatsTheme {
        Greeting("Android")
    }
}
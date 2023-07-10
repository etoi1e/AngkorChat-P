package com.example.angkorwebtoonprototype

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
import com.example.angkorwebtoonprototype.main.AngkorWebtoonMainScreen
import com.example.angkorwebtoonprototype.splash.AngkorWebtoonSplashScreen
import com.example.angkorwebtoonprototype.ui.theme.AngkorWebtoonPrototypeTheme
import com.example.angkorwebtoonprototype.webtoon.Webtoon
import kotlinx.coroutines.delay

internal val webtoons = listOf(
    Webtoon(
        imageResId = R.drawable.img_category_1_h_148,
        title = "Webtoon title",
        writer = "Writer"
    ),
    Webtoon(
        imageResId = R.drawable.img_category_2_h_148,
        title = "Webtoon title",
        writer = "Writer"
    ),
    Webtoon(
        imageResId = R.drawable.img_category_3_h_148,
        title = "Webtoon title",
        writer = "Writer"
    ),
    Webtoon(
        imageResId = R.drawable.img_category_4_h_148,
        title = "Webtoon title",
        writer = "Writer"
    ),
    Webtoon(
        imageResId = R.drawable.img_category_5_h_148,
        title = "Webtoon title",
        writer = "Writer"
    ),
    Webtoon(
        imageResId = R.drawable.img_category_6_h_148,
        title = "Webtoon title",
        writer = "Writer"
    ),
    Webtoon(
        imageResId = R.drawable.img_category_7_h_148,
        title = "Webtoon title",
        writer = "Writer"
    ),
    Webtoon(
        imageResId = R.drawable.img_category_8_h_148,
        title = "Webtoon title",
        writer = "Writer"
    ),
    Webtoon(
        imageResId = R.drawable.img_category_9_h_148,
        title = "Webtoon title",
        writer = "Writer"
    ),
    Webtoon(
        imageResId = R.drawable.img_category_10_h_148,
        title = "Webtoon title",
        writer = "Writer"
    ),
    Webtoon(
        imageResId = R.drawable.img_category_11_h_148,
        title = "Webtoon title",
        writer = "Writer"
    ),
    Webtoon(
        imageResId = R.drawable.img_category_12_h_148,
        title = "Webtoon title",
        writer = "Writer"
    )
)

class AngkorWebtoonActivity : ComponentActivity() {
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
                AngkorWebtoonSplashScreen()
            }

            AnimatedVisibility(visible = !isSplash, enter = fadeIn(), exit = fadeOut()) {
                AngkorWebtoonMainScreen(
                    bannerImageResources = listOf(
                        R.drawable.img_main_banner_h_232,
                        R.drawable.img_banner_2
                    ).shuffled().take(5),
                    webtoons = webtoons
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
    AngkorWebtoonPrototypeTheme {
        Greeting("Android")
    }
}
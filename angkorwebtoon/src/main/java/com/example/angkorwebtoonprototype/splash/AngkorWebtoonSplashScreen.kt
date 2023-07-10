package com.example.angkorwebtoonprototype.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.angkorwebtoonprototype.R
import com.example.angkorwebtoonprototype.ui.theme.AngkorWebtoonPrototypeTheme

@Composable
@Preview
fun AngkorWebtoonSplashScreen() {
    AngkorWebtoonPrototypeTheme(
        lightStatusBar = true
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.angkor_webtoon_img_splash),
                contentDescription = "AngkorWebtoon"
            )
        }
    }
}
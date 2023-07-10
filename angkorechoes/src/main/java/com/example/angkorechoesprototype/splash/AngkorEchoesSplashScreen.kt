package com.example.angkorechoesprototype.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.angkorechoesprototype.R
import com.example.ui.theme.AngkorEchoesTheme

@Composable
@Preview
fun AngkorEchoesSplashScreen() {
    AngkorEchoesTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AngkorEchoesTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.angkor_echoes_img_splash),
                contentDescription = "Splash"
            )
        }
    }
}
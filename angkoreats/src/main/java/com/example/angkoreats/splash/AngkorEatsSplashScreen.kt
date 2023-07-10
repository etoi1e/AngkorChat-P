package com.example.angkoreats.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkoreats.R
import com.example.angkoreats.ui.compose.theme.AngkorEatsTheme

@Composable
@Preview
fun AngkorEatsSplashScreen() {
    AngkorEatsTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AngkorEatsTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                modifier = Modifier
                    .padding(bottom = 108.dp)
                    .size(240.dp),
                painter = painterResource(id = R.drawable.img_splash_logo),
                contentDescription = "Splash"
            )
            Image(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .size(300.dp),
                painter = painterResource(id = R.drawable.img_splash_bbq),
                contentDescription = "BBQ"
            )

        }
    }
}
package com.digitalangkor.mobility.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitalangkor.mobility.R
import com.digitalangkor.mobility.ui.theme.AngKorMobilityTheme
import com.digitalangkor.mobility.util.iconShadow

@Composable
internal fun AngkorMobilityTopBar() {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .height(70.dp)
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterStart)
                .iconShadow(
                    color = Color(0x40000000),
                    offsetY = 4.dp,
                    blurRadius = 4.dp
                )
                .clip(CircleShape)
                .clickable {

                },
            painter = painterResource(id = R.drawable.burger_menu), contentDescription = ""
        )
        Image(
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterEnd)
                .iconShadow(
                    color = Color(0x40000000),
                    offsetY = 4.dp,
                    blurRadius = 4.dp
                )
                .clip(CircleShape)
                .clickable {

                },
            painter = painterResource(id = R.drawable.profile), contentDescription = ""
        )
    }
}

@Preview
@Composable
internal fun AngkorMobilityTopBarPreview() {
    AngKorMobilityTheme {
        Surface {
            AngkorMobilityTopBar()
        }
    }
}
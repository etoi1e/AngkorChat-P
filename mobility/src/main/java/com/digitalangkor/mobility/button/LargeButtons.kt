package com.digitalangkor.mobility.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

@Composable
fun LargeButtonBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(100))
            .background(Color(0xfffafafa))
            .border(1.dp, Color(0xffededed), RoundedCornerShape(100))
            .clickable { },
        content = content
    )
}

@Composable
fun PickUpPointButton() {
    LargeButtonBackground(modifier = Modifier.padding(horizontal = 36.dp)) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.img_circle), contentDescription = "")
            Text(
                text = "Choose pick up point",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xff656565)
            )
        }
    }
}

@Composable
fun DestinationButton() {
    LargeButtonBackground(modifier = Modifier.padding(horizontal = 36.dp)) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.img_location), contentDescription = "")
            Text(
                text = "Choose your destination",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xff656565)
            )
        }
    }
}

@Preview
@Composable
fun LargeButtons() {
    AngKorMobilityTheme {
        Surface {
            Column(
                modifier = Modifier.padding(top = 100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PickUpPointButton()
                DestinationButton()
            }
        }
    }
}
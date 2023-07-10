package com.example.angkoreats.ui.compose.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkoreats.R
import com.example.angkoreats.ui.compose.theme.AngkorEatsTheme


@Composable
fun RestaurantsBar(
    img: Painter,
    name: String,
    time: String
) {

    Row(
        modifier = Modifier.background(AngkorEatsTheme.colors.background)
    ) {

        Column() {
            Image(
                modifier = Modifier
                    .width(224.dp)
                    .height(144.dp)
                    .clip(RoundedCornerShape(15.dp)),
                painter = img, contentDescription = name,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 4.dp)
                    .width(56.dp)
                    .height(20.dp)
                    .border(
                        BorderStroke(1.dp, AngkorEatsTheme.colors.accent),
                        shape = RoundedCornerShape(corner = CornerSize(4.dp))
                    ),
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = "coupon",
                    color = AngkorEatsTheme.colors.accent,
                    style = AngkorEatsTheme.typography.caption
                )
            }

            Text(text = name, style = AngkorEatsTheme.typography.body)
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = time,
                style = AngkorEatsTheme.typography.body4,
                color = AngkorEatsTheme.colors.textSecond
            )

        }


    }

}

@Preview
@Composable
fun RestaurantsBarPreview() {
    AngkorEatsTheme {
        RestaurantsBar(
            img = painterResource(id = R.drawable.img_all_restaurants_1_144),
            name = "Eleven Madison Park",
            time = "20 ~ 25 min"
        )
    }
}



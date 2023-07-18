package com.example.angkorshopping.compose.item

import android.graphics.drawable.shapes.OvalShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorshopping.R
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme

@Composable

fun Reviews(
    itemImg: Int,
    userImg: Int,
    userName: String
) {
    Box() {
        Box(
            modifier = Modifier
                .width(104.dp)
                .height(104.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Image(
                contentScale = ContentScale.Crop,
                painter = painterResource(itemImg),
                contentDescription = ""
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = userImg),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = userName,
                style = AngkorShoppingTheme.typography.sansR11
            )
        }


    }

}


@Preview
@Composable
fun ReviewsPreview() {
    AngkorShoppingTheme {
        Reviews(R.drawable.img_3, R.drawable.img_2, "UserName")
    }
}
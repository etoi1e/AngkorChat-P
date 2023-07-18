package com.example.angkorshopping.compose.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorshopping.R
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme


@Composable
fun RecentItem(
    item: String
) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp)
            .width(73.dp)
            .height(28.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(AngkorShoppingTheme.colors.LightYellow),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item,
            style = AngkorShoppingTheme.typography.sansR13,
            color = AngkorShoppingTheme.colors.MainYellow
        )
        Icon(
            modifier = Modifier
                .padding(start = 4.dp)
                .size(10.dp),
            painter = painterResource(id = R.drawable.ic_close_line_yellow_12),
            contentDescription = "",
            tint = AngkorShoppingTheme.colors.MainYellow,
        )
    }

}


@Preview
@Composable
fun TopBannerPreview() {
    AngkorShoppingTheme {
        RecentItem("item")
    }
}
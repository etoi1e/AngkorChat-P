package com.example.angkorshopping.compose.item


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorshopping.R
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme

@Composable
fun Merchandise(
    Item: Int
) {
    val image = painterResource(id = Item)

    Column(
        modifier = Modifier
            .clickable {  }
    ) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp)),
            painter = image,
            contentDescription = "Merchandise"

        )

    }
}

@Preview
@Composable
fun MerchandisePreview() {
    AngkorShoppingTheme {
            Merchandise(Item = R.drawable.img_1)
    }
}